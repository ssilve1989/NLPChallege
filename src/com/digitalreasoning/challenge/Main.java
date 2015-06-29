package com.digitalreasoning.challenge;

import com.digitalreasoning.tasks.NLPTask;
import com.digitalreasoning.tasks.ChallengeTask;

/**
 * Created by steve on 6/26/15.
 *
 */
public class Main {
    public static void main(String[] args){
        NLPTask task = new ChallengeTask("nlp_data");
        task.runTask();
    }
}
