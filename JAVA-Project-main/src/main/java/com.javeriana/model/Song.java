package com.javeriana.model;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class Song {
    private UUID id;
    private String name;
    private String genre;
    private int durationInSeconds;
    private String album;
    private List<Artist> artists;

    public Song(String name, String genre, int durationInSeconds, String album, Artist initialArtist){
        this.id = UUID.randomUUID();
        this.name = name;
        this.genre = genre;
        this.durationInSeconds = durationInSeconds;
        this.album = album;
        this.artists = new ArrayList<>();
        this.artists.add(initialArtist);
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public int getDurationInSeconds() { return durationInSeconds; }
    public String getAlbum() { return album; }
    public List<Artist> getArtists() { return artists; }

    public void setName(String name) { this.name = name; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDurationInSeconds(int durationInSeconds) { this.durationInSeconds = durationInSeconds; }
    public void setAlbum(String album) { this.album = album; }

    @Override
    public String toString() {
        return "Id: " + id + " - Name: " + name + " - Album: " + album + " (" + durationInSeconds + "s)";
    }
}
