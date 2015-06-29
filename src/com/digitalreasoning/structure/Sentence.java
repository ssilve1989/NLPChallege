package com.digitalreasoning.structure;

import com.digitalreasoning.xml.XMLTag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by steve on 6/26/15.
 * Data Structure to represent a sentence. What is a sentence?
 * A sentence is a collection of words and punctuation.
 */
public class Sentence implements XMLTag {
    private static final String XML_TAG = "Sentence";
    private String sentence;
    private final List<SentencePiece> pieces;
    private static final Pattern nonWordRegex = Pattern.compile("\\W");
    private static final String ENTITY_REPLACE = String.valueOf("ENTITY_REPLACE".hashCode());

    public Sentence(String sentence) {
        this.sentence = sentence;
        pieces = new ArrayList<>();
        parseSentence();
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public List<SentencePiece> getSentencePieces() {
        return this.pieces;
    }

    /**
     * Take the sentence instance variable and process it for words/punctuation
     * and named entities. You are probably thinking, what the hell is this weird
     * ENTITY_REPLACE hashcoded string. Well this allows for a "simpler" processing of named
     * entities. If we can create the word instance on entities first, it saves computation time
     * from other approaches. We do this replacement string so that we can maintain the order of sentencePiece
     * occurence. We also use a linkedlist to just "pop" off the Entity when we encounter
     * its replacement later in addSentencePiece()
     */
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

    /**
     * Breaks the given String into its word/punctuation pieces while also
     * checking for named entity replacements.
     * @param s The String representing the Sentence
     * @param entities A list of entities found in this String sentence
     */
    public void addSentencePiece(String s, List<Word> entities){
        Matcher matcher = nonWordRegex.matcher(s);
        int start = 0;
        while(matcher.find()){
            if(start != matcher.start()){
                //this is a word
                String word = s.substring(start,matcher.start());
                if(word.equals(ENTITY_REPLACE)){
                    pieces.add(entities.remove(0));
                }else{
                    pieces.add(new Word(word));
                }
                start = matcher.end();
            }else{
                start++;
            }
            //the matched group is the punctuation
            pieces.add(new Punctuation(matcher.group()));
        }
    }

    /**
     * Returns true if the term is found in source. It uses word boundaries so that&nbsp;
     * partial matches don't return a false positive. Ex. Europe vs European
     * @param source The String in which the search term is being checked
     * @param term The String that is being searched for in source
     * @return
     */
    private boolean containsExact(String source, String term){
        final Pattern p = Pattern.compile("\\b"+term+"\\b");
        final Matcher m = p.matcher(source);
        return m.find();
    }

    /**
     * Override toString to return all the words/punctuation that form
     * this sentence into a coherent String representation.
     * @return
     */
    @Override
    public String toString(){
        StringBuilder sentence = new StringBuilder();
        pieces.forEach(sentence::append);
        return sentence.toString();
    }

    @Override
    public String getXMLTag() {
        return XML_TAG;
    }
}
