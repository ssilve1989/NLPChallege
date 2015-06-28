package com.digitalreasoning.structure.test;

import com.digitalreasoning.structure.Paragraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by steve on 6/28/15.
 */
public class ParagraphTest {

    @Test
    public void paragraphTest() {
        String sentences = "This paragraph has three sentences. This one. And this one.";
        Paragraph paragraph = new Paragraph(sentences);
        assertEquals(3, paragraph.getSentences().size());
        String sentencesWithSpecialChars = "This sentence has some special \"chars\" in it. As well as some other symbols <>! It's length is 3.";
        Paragraph p2 = new Paragraph(sentencesWithSpecialChars);
        assertEquals(3, p2.getSentences().size());
    }
}
