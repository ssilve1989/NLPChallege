package com.digitalreasoning.structure;

import com.digitalreasoning.xml.XMLTag;

/**
 * Created by steve on 6/26/15.
 * Data structure to contain any type of punctuation
 */
public class Punctuation extends SentencePiece
        implements XMLTag {

    private static final String XML_TAG = "Punctuation";

    /**
     * Create the punctuation instance from the string passed in.
     * Note: Removed the unicode conversion from previous versions as I discovered it was due to the IntelliJ IDE
     * "reformat code" function that removed the whitespace from those nodes causing it to be empty.
     * @param punctuation
     */
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
