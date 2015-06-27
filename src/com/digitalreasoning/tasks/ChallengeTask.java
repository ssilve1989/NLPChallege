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
 */
public class ChallengeTask extends NLPTask {

    private final String input;
    private List<String> lines;

    public ChallengeTask(String input) {
        this.input = input;
    }

    /**
     * Sets up the lines that have been read in from the input file via Stream and translated
     * into a List<String>. Stream might process faster than normal iteration? Regardless
     * the code is cleaner doing it this way and doesn't introduce "side-effects" the way
     * normal file reading does.
     * @throws IOException
     */
    @Override
    protected void beforeTask() throws IOException {
        try{
            lines = Files.lines(Paths.get(this.input))
                    .filter(p -> !p.trim().isEmpty())
                    .collect(Collectors.toList());
        }catch(IOException e){
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
            List<Paragraph> paragraphs = new ArrayList<Paragraph>();
            for(String line : lines){
                paragraphs.add(new Paragraph((line)));
            }
            ParagraphsToXML xmlBuilder = new ParagraphsToXML("output.xml", paragraphs);
            xmlBuilder.writeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
