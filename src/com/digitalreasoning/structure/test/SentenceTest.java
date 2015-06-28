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
}
