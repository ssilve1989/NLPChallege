package com.digitalreasoning.structure.test;

import com.digitalreasoning.structure.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by steve on 6/27/15.
 */
public class SentenceTest {

    /**
     * Tests that not only does Sentence construction work, but that
     * the pieces are constructed correctly.
     */
    @Test
    public void testSentence(){
        String s = "This is a sentence with 10 words and 10 punctuations.";
        Sentence sentence = new Sentence(s);
        int wordCount = 0, puncCount = 0;
        for(SentencePiece piece : sentence.getSentencePieces()){
            if(piece instanceof Word) wordCount++;
            else if(piece instanceof Punctuation) puncCount++;
        }
        assertEquals(10, wordCount);
        assertEquals(10, puncCount);
    }

    @Test
    public void toStringTest(){
        String s = "I am the entire sentence with punctuations and \"quotes\"!";
        Sentence sentence = new Sentence(s);
        //convert spaces to unicode since we do that in our structures for XML compatability
        s = s.replaceAll(" ", "\u00A0");
        assertEquals(s, sentence.toString());
    }
}
