package com.javeriana.model;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Playlist {
    private UUID id;
    private String name;
    private List<Song> songs;

    public Playlist(String name){
        this.id = UUID.randomUUID();
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public List<Song> getSongs() { return songs; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Id: " + id + " - Name: " + name + " (Total songs: " + songs.size() + ")";
    }
}
