package com.digitalreasoning.structure;

import com.digitalreasoning.xml.XMLTag;

/**
 * Created by steve on 6/26/15.
 */
public class Word extends SentencePiece
    implements XMLTag {

    private static final String XML_TAG = "Word";
    private boolean namedEntity;

    public Word(String word) {
        this.piece = word;
        namedEntity = false;
    }

    public boolean isNamedEntity() {
        return namedEntity;
    }

    public void setIsNamedEntity(boolean value){
        this.namedEntity = value;
    }

    public String getWord(){
        return this.piece;
    }

    @Override
    public String getXMLTag() {
        return XML_TAG;
    }
}
