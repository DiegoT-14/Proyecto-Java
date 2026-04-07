package com.javeriana.model;

import java.util.UUID;

public class Song {
    private UUID id;
    private String name;
    private String genre;
    private int durationInSeconds;
    private String album;


    public Song (String name,String genre, int durationInSeconds, String album){
        this.id= UUID.randomUUID();
        this.name=name;
        this.genre=genre;
        this.durationInSeconds=durationInSeconds;
        this.album=album;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public String getAlbum() {
        return album;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Id: " + id + " - Name: " + name + " - Genre: " + genre + " - Duration in seconds: " + durationInSeconds + " - Album: " + album;
    }
}
