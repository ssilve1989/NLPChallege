package com.digitalreasoning.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by steve on 6/28/15.
 * Utility class to help turn a directory's contents
 * into a list
 */
public class DirToListUtil {

    public static List<File> getDirectoryFiles(String directoryName){
        File directory = new File(directoryName);
        if(!directory.isDirectory()){
            System.err.println(directoryName + " is not a directory");
            return Collections.emptyList();
        }
        File[] files = directory.listFiles();
        if(files == null || files.length == 0) return Collections.emptyList();
        List<File> fileList = new ArrayList<>();
        for(File file : files){
            fileList.add(file);
        }
        return fileList;
    }
}
