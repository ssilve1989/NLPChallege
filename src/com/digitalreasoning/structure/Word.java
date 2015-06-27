package com.digitalreasoning.structure;

import com.digitalreasoning.xml.XMLTag;

/**
 * Created by steve on 6/26/15.
 */
public class Word extends SentencePiece
    implements XMLTag {

    private static final String XML_TAG = "Word";

    public Word(String word) {
        this.piece = word;
    }

    public String getWord(){
        return this.piece;
    }

    @Override
    public String getXMLTag() {
        return XML_TAG;
    }
}
