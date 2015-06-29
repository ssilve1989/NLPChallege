**Digital Reasoning NLP Challenge**
===================================

**Task 1**
----------

 1. Write    a    program    that    identifies    sentence    boundaries    and    tokenizes    the    text    in    the    file   
“   nlp_data.txt   ”    into    words.    It    should    correctly    process    all    symbols,    including    punctuation   
and    whitespace.    Every    word    must    fall    into    a    sentence.    Create    data    structures    that   
efficiently    express    the    data    you    have    processed.    When    your    program    runs    it    should   
output  an  XML  representation  of  your  Java  object  model.  

Commit: [Task 1](https://github.com/ssilve1989/NLPChallenge/tree/78f3f6acefbfab7553b47b833f2e2be5e8744061)

**Implementation - Task 1**

`Main`, `ChallengeTask`, `NLPTask` Interface
The program can be run by running the main method in: 
`com.digitalreasoning.challenge.Main`    
This class is responsible for creating a new `ChallengeTask` that implements `NLPTask`.
`ChallengeTask` defines a `beforeTask()` method to preprocess the task dependencies.
In this task it stores the lines of text into a Collection.

```java
@Override
protected void beforeTask() throws IOException {
        lines = Files.lines(Paths.get(this.input))
                .filter(p -> !p.trim().isEmpty())
                .collect(Collectors.toList());
}
```

**Data Structures** `com.digitalreasoning.structure.*`
This package consists of the data structures used to represent the semantics of the text file read in. `Paragraph` uses `BreakIterator` to locate sentence boundaries. At first I wanted to make use of some RegEx to detect the boundaries (ex: `"\\p{Punct}\\s+[A-Z|\'|\"]"`) but it was not good enough. `Paragraph` then stores these Sentences in a `List<Sentence>` collection. As a new `Sentence` object is created it breaks down the sentence into `Word` and `Punctuation` objects both of which extend the abstract class `SentencePiece`. 

`XMLTag` is an interface which the structures implement to assign an XML Tag name to represent the object. After the semantics are constructed `ParagraphsToXML` constructs an XML document representing the structure of the input. This class chains through each objects nodes using clear and concise methods that take advantage of the Java 8 Lambda expressions.
```java
private final void paragraphToXml(Element root, Paragraph p){
        Element paragraphElement = doc.createElement(p.getXMLTag());
        root.appendChild(paragraphElement);
        p.getSentences().forEach(sentence -> sentenceToXMl(paragraphElement, sentence));
    }
    private final void sentenceToXMl(Element parent, Sentence s){
        Element sentenceElement = doc.createElement(s.getXMLTag());
        parent.appendChild(sentenceElement);
        s.getSentencePieces().forEach(piece -> sentencePieceToXML(sentenceElement, piece));
    }

    private final void sentencePieceToXML(Element parent, SentencePiece piece){
        Element sentencePieceElement = doc.createElement(piece.getXMLTag());
        if(piece instanceof Word){
            Word word = (Word) piece;
            sentencePieceElement.appendChild(doc.createTextNode(word.getWord()));
        }else if(piece instanceof Punctuation){
            Punctuation punctuation = (Punctuation) piece;
            sentencePieceElement.appendChild(doc.createTextNode(punctuation.getPunctuation()));
        }
        parent.appendChild(sentencePieceElement);
    }
```

**Task 2**
-------
1. Modify your program from #1 to add rudimentary recognition of proper nouns ("named entities") in the input, and print a list of recognized named entities when it runs. The list of named entities is in the file "NER.txt". Enhance your data structures and output schema to store information about which portions of the text represent named entities.

Commit: [Task 2](https://github.com/ssilve1989/NLPChallenge/tree/85f440829c58aa5af053495b200b24b93b778d83)

This task introduces a named entity list in which we need to verify against as we parse the input. The class `com.digitalreasoning.structure.NamedEntityList` was  introduced as a `Singleton` to contain this data. The main change here was when the Sentences are breaking up into words/punctuation we check if any of the words are named entities. I abstracted the logic to break apart words/pieces into `addSentencePiece()`and introduced new logic to determine if there were any named entities.

```java
 public void parseSentence(){
        String s = this.sentence;
        //check if there are any named entities in this
        List<String> entities = NamedEntityList.getInstance().getEntityList();
        List<Word> entityWords = new LinkedList<>();
        for(String entity : entities){
            if(containsExact(s, entity)){
                int startIndex = s.indexOf(entity);
                Word word = new Word(s.substring(startIndex, startIndex + entity.length()));
                word.setIsNamedEntity(true);
                entityWords.add(word);
                s = s.replaceFirst(entity, ENTITY_REPLACE);
            }
        }
        addSentencePiece(s, entityWords);
    }
```
 Now when the XML document is created it treats a named entity as a single word and applies the attribute namedEntity="true" to the `<Word>` node.

**Task 3**
----------

Commit: Master branch contains the most up to date code.

1. Modify your program from #2 to use "nlp_data.zip" as its input. Use a thread pool to parallelize the processing of the text files contained in the zip. Aggregate the results and modify the output schema accordingly.

This task introduces a series of files to be read in parallel. The biggest change here is to `ChallengeTask.runTask()`. This method now creates a thread pool whose size is equal to the amount of files to be processed. I use the Java 8 `Future`class to process all the data in parallel and when the computation is complete we render the data into XML. It also utilizes a threadsafe collection `LinkedBlockingDeque'

```java
    @Override
    public void runTask() {
        try {
            beforeTask();
            LinkedBlockingDeque<Paragraph> paragraphList = new LinkedBlockingDeque<>(); //thread safe collection
            ExecutorService executor = Executors.newFixedThreadPool(files.size());
            for(File file: files) {
                Future<List<Paragraph>> paragraphs = executor.submit(() -> Files.lines(Paths.get(file.getAbsolutePath()))
                        .parallel()
                        .map(Paragraph::new)
                        .collect(Collectors.toList()));
                List<Paragraph> futureParagraphs = paragraphs.get();
                paragraphList.addAll(futureParagraphs);
            }
            executor.shutdown();
            ParagraphsToXML xmlBuilder = new ParagraphsToXML("output.xml", paragraphList);
            xmlBuilder.writeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
```

**Improvements from Task 2**
-------
I reverse sorted the named entity list so that running into instances of entitiy pairs that look like "Sun" and "Sun Microsystems" would process correctly. Without a reverse sort you can run into the chance that "Sun" gets parsed out first leaving only "Microsystems" in the sentence.

**Limitations**
-------

This program sort of meshes functional programming and object oriented programming giving it a bit of a limitation in terms of code comprehension. Given more time to think it out I would research the appropriate Design Pattern to use and reduce Lambda usage to where it would be more appropriate. 

Memory Limitations: Input is read and stored as Strings in memory. If the input are very large, memory could be come an issue. As Entity List grows the operational time of computing named entities grows. Currently everything is put in memory and then dumped into XML. Ideally it would stream data and write as its streaming. 

**Test Cases**
-------

`com.digitalreasoning.structure.test.*` contains unit testing for each Structure using JUnit. 
