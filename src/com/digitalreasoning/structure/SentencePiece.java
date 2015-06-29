package com.digitalreasoning.structure;

import com.digitalreasoning.xml.XMLTag;

/**
 * Created by steve on 6/27/15.
 * Sort of a "God Parent" class for Word and Punctuation so they can be added
 * to the same data structure to preserve order when parsing a sentence.
 */
public abstract class SentencePiece
    implements XMLTag{
    protected String piece;

    @Override
    public String toString() {
        return this.piece;
    }

    public abstract String getXMLTag();

}
