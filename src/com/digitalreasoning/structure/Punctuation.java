package com.digitalreasoning.structure;

import com.digitalreasoning.xml.XMLTag;

/**
 * Created by steve on 6/26/15.
 * Data structure to contain any type of punctuation
 */
public class Punctuation extends SentencePiece
        implements XMLTag {

    private static final String XML_TAG = "Punctuation";

    public Punctuation(String punctuation) {
        //this.piece = punctuation.equals(" ") ? "\u00A0" : punctuation;
        this.piece = punctuation;
    }

    public String getPunctuation() {
        return this.piece;
    }

    @Override
    public String getXMLTag() {
        return XML_TAG;
    }
}
