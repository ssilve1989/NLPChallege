package com.digitalreasoning.tasks;

import java.io.IOException;

/**
 * Created by steve on 6/26/15.
 * All Challenge tasks deal with I/O so they should define a beforeTask
 * to appropriately setup the actual task.
 */
public abstract class NLPTask {

    protected abstract void beforeTask() throws IOException;
    public abstract void runTask();
}
