package com.digitalreasoning.structure;

import com.digitalreasoning.xml.XMLTag;

import java.util.ArrayList;
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
    private List<SentencePiece> pieces;
    private static final Pattern nonWordRegex = Pattern.compile("\\W");

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
     * Here we are going to parse the Sentence into Words and Punctuation.
     * We will iterate over the sentence tokenized. Rather than splitting
     * on a word and non-word regex, we willy only iterate once which should
     * ensure better performance. It doesn't make sense to perform a split twice
     * on the same string.
     */
    public void parseSentence(){
        //Get the sentence pieces, spaces count as punctuation
        String s = this.sentence; //local just for clarity
        Matcher matcher = nonWordRegex.matcher(s);
        int start = 0;
        while(matcher.find()){
            if(start != matcher.start()){
                //this is a word
                pieces.add(new Word(s.substring(start, matcher.start())));
                start = matcher.end();
            }else{
                start++;
            }
            //the matched group is the punctuation
            pieces.add(new Punctuation(matcher.group()));
        }
    }

    @Override
    public String getXMLTag() {
        return XML_TAG;
    }
}
