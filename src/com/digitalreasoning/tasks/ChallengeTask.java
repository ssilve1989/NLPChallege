package com.digitalreasoning.tasks;

import com.digitalreasoning.structure.Paragraph;
import com.digitalreasoning.xml.ParagraphsToXML;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by steve on 6/26/15.
 * 1. Write a program that identifies sentence boundaries and tokenizes the text in the file
 * "nlp_data.txt" into words. It should correctly process all symbols, including punctuation
 * and whitespace. Every word must fall into a sentence. Create data structures that
 * efficiently express the data you have processed. When your program runs it should
 * output an XML representation of your Java object model.
 * <p>
 * #2 Modify your program from #1 to add rudimentary recognition of proper nouns (“named
 * entities”) in the input, and print a list of recognized named entities when it runs.
 * The list of named entities is in the file “NER.txt”. Enhance your data structures and
 * output schema to store information about which portions of the text represent named entities.
 */
public class ChallengeTask extends NLPTask {

    private final String input;
    private final String entityFile;
    private List<String> lines;
    private List<String> namedEntities;

    public ChallengeTask(String input, String entityFile) {
        this.input = input;
        this.entityFile = entityFile;
    }

    /**
     * Sets up the lines that have been read in from the input file via Stream and translated
     * into a List<String>. Stream might process faster than normal iteration? Regardless
     * the code is cleaner doing it this way and doesn't introduce "side-effects" the way
     * normal file reading does.
     *
     * @throws IOException
     */
    @Override
    protected void beforeTask() {
        try {
            lines = Files.lines(Paths.get(this.input))
                    .filter(p -> !p.trim().isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void runTask() {
        try {
            beforeTask();
            List<Paragraph> paragraphs = lines.stream().map(line -> new Paragraph((line))).collect(Collectors.toList());
            ParagraphsToXML xmlBuilder = new ParagraphsToXML("output.xml", paragraphs);
            xmlBuilder.writeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
