package com.digitalreasoning.structure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by steve on 6/27/15.
 */
public class NamedEntityList {
    private List<String> entities = Collections.emptyList();
    private static NamedEntityList instance;

    private NamedEntityList(){
        try {
            entities = Files.lines(Paths.get("NER.txt"))
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NamedEntityList getInstance(){
        if(instance == null){
            instance = new NamedEntityList();
        }
        return instance;
    }

    public List<String> getEntityList(){
        return this.entities;
    }

    //Unit test
    public static void main(String[] args){
        List<String> entities = NamedEntityList.getInstance().getEntityList();
        entities.forEach(System.out::println);
    }
}
