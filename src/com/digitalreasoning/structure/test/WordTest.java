package com.digitalreasoning.structure.test;

import com.digitalreasoning.structure.Word;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by steve on 6/28/15.
 */
public class WordTest {

    @Test
    public void wordTest(){
        assertEquals("Word", new Word("Word").getWord());
        Word word = new Word("Named Entity");
        word.setIsNamedEntity(true);
        assertTrue(word.isNamedEntity());
        assertNotEquals("Nope", new Word("Yep").getWord());
    }
}
