package com.digitalreasoning.structure.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by steve on 6/28/15.
 *
 * This class isn't actually used but could be to verify that the correct
 * SentencePiece is instantiated.
 */
public class SentencePieceUtil {
    private static final Pattern punctuationPattern = Pattern.compile("\\p{Punct}");
    private static final Pattern wordPattern = Pattern.compile("\\w+");

    public static boolean isPunctuation(String punctuation){
        final Matcher matcher = punctuationPattern.matcher(punctuation);
        return verify(matcher);
    }

    public static boolean isWord(String word){
        final Matcher matcher = wordPattern.matcher(word);
        return verify(matcher);
    }

    private static boolean verify(Matcher matcher){
        return matcher.find();
    }
}
