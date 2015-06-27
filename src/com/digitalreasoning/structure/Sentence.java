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
    private List<SentencePiece> pieces;
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
     * Here we are going to parse the Sentence into Words and Punctuation.
     * We will iterate over the sentence tokenized. Rather than splitting
     * on a word and non-word regex, we willy only iterate once which should
     * ensure better performance. It doesn't make sense to perform a split twice
     * on the same string.
     */
    public void parseSentence(){
        String s = this.sentence;
        //check if there are any named entities in this
        List<String> entities = NamedEntityList.getInstance().getEntityList();
        List<Word> entityWords = new LinkedList<>();
        for(String entity : entities){
            if(s.contains(entity)){
                int startIndex = s.indexOf(entity);
                Word word = new Word(s.substring(startIndex, startIndex + entity.length()));
                word.setIsNamedEntity(true);
                entityWords.add(word);
                s = s.replaceFirst(entity, ENTITY_REPLACE);
            }
        }
        addSentencePiece(s, entityWords);
    }

    private boolean containsEntity(String s){
        for(String entity : NamedEntityList.getInstance().getEntityList()){
            if(s.contains(entity))
                return true;
        }
        return false;
    }

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

    @Override
    public String getXMLTag() {
        return XML_TAG;
    }
}
