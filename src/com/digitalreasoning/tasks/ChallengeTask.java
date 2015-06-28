package com.digitalreasoning.tasks;

import com.digitalreasoning.structure.Paragraph;
import com.digitalreasoning.util.DirToListUtil;
import com.digitalreasoning.xml.ParagraphsToXML;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    private List<File> files;

    public ChallengeTask(String input) {
        this.input = input;
    }

    @Override
    protected void beforeTask() {
        try {
            files = DirToListUtil.getDirectoryFiles("nlp_data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Use a ThreadPool with threads equal to the amount of files there are
     *  to be read in.&nbsp;Using the Future class we can store all the responses
     *  in a single list and when all threads are done executing the data parsing
     *  we can dump the data into XML format.
     */
    @Override
    public void runTask() {
        try {
            beforeTask();
            List<Paragraph> paragraphList = new ArrayList<>();
            ExecutorService executor = Executors.newFixedThreadPool(files.size());
            for(File file: files) {
                Future<List<Paragraph>> paragraphs = executor.submit(() -> Files.lines(Paths.get(file.getAbsolutePath()))
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
    }
}
