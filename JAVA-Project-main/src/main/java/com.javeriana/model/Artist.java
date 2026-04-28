package com.javeriana.model;
import java.util.UUID;

public class Artist {
    private UUID id;
    private String name;

    public Artist(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Artist(String name){
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName(){ return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Id: " + id + " - Name: " + name;
    }
}
