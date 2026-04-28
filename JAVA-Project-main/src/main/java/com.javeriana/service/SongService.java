package com.javeriana.service;

import com.javeriana.model.Artist; // Importante añadir este
import com.javeriana.model.Song;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SongService {
    private List<Song> songs;

    public SongService() {
        this.songs = new ArrayList<>();
    }

    public void add(Song s) {
        if (isInvalid(s.getName()) || isInvalid(s.getGenre()) || isInvalid(s.getAlbum())) {
            throw new IllegalArgumentException("Los campos no pueden estar vacios o ser null");
        }
        if (s.getDurationInSeconds() < 0) {
            throw new IllegalArgumentException("La duración de la cancion no puede ser menor a 0");
        }
        this.songs.add(s);
    }

    public void delete(UUID id) {
        if (id == null || id.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("El id de la cancion no puede estar vacio o ser null");
        }

        boolean removed = songs.removeIf(s -> s.getId().equals(id));
        if (!removed) {
            System.out.println("La cancion con id " + id + " no existe");
        }
    }

    // --- MÉTODO CORREGIDO PARA EL BORRADO EN CASCADA ---
    public List<UUID> removeSongsByArtistId(UUID artistId) {
        List<UUID> removedSongIds = new ArrayList<>();

        // Usamos removeIf para filtrar y eliminar las canciones del artista
        songs.removeIf(song -> {
            for (Artist a : song.getArtists()) {
                if (a.getId().equals(artistId)) {
                    removedSongIds.add(song.getId());
                    return true; // Indica que esta canción debe ser eliminada
                }
            }
            return false;
        });

        return removedSongIds;
    }

    public Song findById(UUID id) {
        for (Song s : songs) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }

    private boolean isInvalid(String s) {
        return s == null || s.trim().isEmpty();
    }

    public void showAll() {
        if (songs.isEmpty()) {
            System.out.println("No hay canciones registradas.");
            return;
        }
        for (Song s : songs) {
            System.out.println(s.toString());
        }
    }

    public List<Song> getSongs() {
        return songs;
    }
}
