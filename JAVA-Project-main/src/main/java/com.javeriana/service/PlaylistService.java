package com.javeriana.service;

import com.javeriana.model.Playlist;
import com.javeriana.model.Song;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlaylistService {
    private List<Playlist> playlists; // Lista global de playlists del sistema

    public PlaylistService() {
        this.playlists = new ArrayList<>();
    }

    // --- MÉTODOS PARA EL ADMINISTRADOR (LISTA GLOBAL) ---

    public void add(Playlist p) {
        // Regla de negocio 3: Validar nombre
        if (p.getName() == null || p.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la lista de reproduccion no puede estar vacio o ser null");
        }
        this.playlists.add(p);
    }

    // Corrigiendo error: Cannot resolve method 'showAll'
    public void showAll() {
        if (playlists.isEmpty()) {
            System.out.println("No hay listas de reproduccion registradas.");
            return;
        }
        for (Playlist p : playlists) {
            System.out.println(p.toString());
        }
    }

    // Corrigiendo error: Cannot resolve method 'delete'
    public void delete(UUID id) {
        boolean removed = playlists.removeIf(p -> p.getId().equals(id));
        if (removed) {
            System.out.println("Playlist eliminada del sistema.");
        }
    }

    // Corrigiendo error: Cannot resolve method 'removeSongFromAllPlaylists'
    // Esta es la lógica para el borrado en cascada
    public void removeSongFromAllPlaylists(UUID songId) {
        for (Playlist playlist : playlists) {
            // Removemos la canción de la lista interna de cada playlist
            playlist.getSongs().removeIf(s -> s.getId().equals(songId));
        }
        System.out.println("Cancion eliminada del sistema.");
    }

    // --- MÉTODOS PARA EL MÓDULO CLIENTE (LISTAS PRIVADAS) ---

    public void addSongToPlaylist(Playlist p, Song s, UUID songId) {
        // Mensaje de la Regla 5 del Cliente
        if (s == null) {
            System.out.println("La cancion con id " + songId + " no existe");
            return;
        }
        p.getSongs().add(s);
        System.out.println("Cancion agregada a la playlist.");
    }

    public void removeSongFromPlaylist(Playlist p, UUID songId) {
        // Mensaje de la Regla 6 del Cliente
        boolean removed = p.getSongs().removeIf(s -> s.getId().equals(songId));
        if (!removed) {
            System.out.println("La cancion con id " + songId + " no existe en la lista de reproducción");
        } else {
            System.out.println("Cancion eliminada de la lista de reproducción.");
        }
    }

    // Busca una playlist específica dentro de una lista (usado para las listas del usuario)
    public Playlist findByIdInList(List<Playlist> list, UUID id) {
        for (Playlist p : list) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    // Elimina de una lista específica (usado para las listas del usuario)
    public void deleteFromList(List<Playlist> list, UUID id) {
        boolean removed = list.removeIf(p -> p.getId().equals(id));
        // Mensaje de la Regla 4 del Cliente
        if (!removed) {
            System.out.println("La playlist con id " + id + " no existe en la lista del usuario");
        } else {
            System.out.println("Playlist eliminada de tu cuenta.");
        }
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }
}
