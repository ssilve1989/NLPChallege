package com.digitalreasoning.structure;

import com.digitalreasoning.xml.XMLTag;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by steve on 6/26/15.
 */
public class Paragraph implements XMLTag {
    private String paragraph;
    private List<Sentence> sentences;
    private static final String XML_TAG = "Paragraph";

    public Paragraph(){
        paragraph = "";
        sentences = Collections.emptyList();
    }

    public Paragraph(String paragraph){
        this.paragraph = paragraph;
        sentences = parseParagraph(this.paragraph);
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentence(String paragraph) {
        this.paragraph = paragraph;
        this.sentences = parseParagraph(this.paragraph);
    }

    /**
     * Exposed as static member incase some user wants to construct a list of sentences
     * from a string. This algorithm will use BreakIterator to determine sentence
     * boundaries and insert all sentences into the member variable <code>sentences</code>
     * It attempts to optimize memory by using the constant reference provided by the Collections
     * class where it can.
     * @param paragraph
     * @return
     */
    public static List<Sentence> parseParagraph(String paragraph){
        if(paragraph == null || paragraph.isEmpty()) return Collections.emptyList();
        List<Sentence> sentences = new ArrayList<Sentence>();
        BreakIterator iter = BreakIterator.getSentenceInstance(Locale.getDefault());
        iter.setText(paragraph);
        int endBoundary = iter.first();
        while(endBoundary != BreakIterator.DONE){
            int startBoundary = endBoundary;
            endBoundary = iter.next();
            if(endBoundary != BreakIterator.DONE){
                String sentence = paragraph.substring(startBoundary, endBoundary);
                sentences.add(new Sentence(sentence));
            }
        }
        //This should allow the reference we created to be GC'd if it turns out
        //there were no sentences
        return sentences.isEmpty() ? Collections.emptyList() : sentences;
    }

    @Override
    public String getXMLTag() {
        return XML_TAG;
    }
}
