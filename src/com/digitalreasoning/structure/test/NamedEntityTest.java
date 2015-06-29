package com.digitalreasoning.structure.test;

import com.digitalreasoning.structure.NamedEntityList;
import com.digitalreasoning.structure.Sentence;
import com.digitalreasoning.structure.SentencePiece;
import com.digitalreasoning.structure.Word;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by steve on 6/28/15.
 */
public class NamedEntityTest {

    /**
     * This test is to ensure that only a single instance of the list ever exists
     * in memory.
     */
    @Test
    public void instanceCheck(){
        NamedEntityList list1 = NamedEntityList.getInstance();
        NamedEntityList list2 = NamedEntityList.getInstance();
        assertEquals(list1, list2);
        assertEquals(list1.getEntityList(), list2.getEntityList());
    }

    /**
     * Verify the named entity count
     */
    @Test
    public void entityTest(){
        String s = "Europe some words Newton other words Serbia.";
        int count = 0;
        Sentence sentence = new Sentence(s);
        for(SentencePiece sentencePiece  : sentence.getSentencePieces()){
            if(sentencePiece instanceof Word){
                Word word = (Word) sentencePiece;
                if(word.isNamedEntity()) count++;
            }
        }
        assertEquals(3, count);
    }
}
