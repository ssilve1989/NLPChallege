package com.digitalreasoning.structure.test;

import com.digitalreasoning.structure.Punctuation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by steve on 6/28/15.
 */
public class PunctuationTest {

    @Test
    public void punctuationTest(){
        //some common punctuations
        assertEquals(".", new Punctuation(".").getPunctuation());
        assertEquals("?", new Punctuation("?").getPunctuation());
        assertEquals("!", new Punctuation("!").getPunctuation());
        //some stranger ones
        assertEquals(">", new Punctuation(">").getPunctuation());
        //unicode version of whitespace for XML compatability.
        assertEquals("\u00A0", new Punctuation(" ").getPunctuation());
    }
}
