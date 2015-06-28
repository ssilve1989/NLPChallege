package com.digitalreasoning.structure.test;

import com.digitalreasoning.structure.util.SentencePieceUtil;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by steve on 6/28/15.
 */
public class SentencePieceTest {

    /**
     * Verify that the Utility class is performing its
     * boolean checks correctly on SentencePiece types.
     */
    @Test
    public void pieceVerifierTest(){
        assertTrue(SentencePieceUtil.isPunctuation("."));
        assertTrue(SentencePieceUtil.isWord("Word"));
        assertFalse(SentencePieceUtil.isWord("!"));
        assertFalse(SentencePieceUtil.isPunctuation("Nope"));
    }
}
