package com.javeriana.service;

import com.javeriana.model.Artist;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArtistService {
    private List<Artist> artists;

    public ArtistService() {
        this.artists = new ArrayList<>();
    }

    public void add(Artist artist) {
        // Regla: Nombre no nulo ni vacío
        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del artista no puede estar vacio o ser null");
        }
        // Regla: No duplicados
        for (Artist a : artists) {
            if (a.getName().equalsIgnoreCase(artist.getName())) {
                System.out.println("El artista con nombre " + artist.getName() + " ya existe");
                return;
            }
        }
        this.artists.add(artist);
    }

    public void delete(UUID id) {
        // Regla: ID no nulo
        if (id == null) {
            throw new IllegalArgumentException("El id del artista no puede estar vacio o ser null");
        }

        boolean removed = artists.removeIf(a -> a.getId().equals(id));
        if (!removed) {
            System.out.println("El artista con id " + id + " no existe");
        }
    }

    public Artist findById(UUID id) {
        for (Artist a : artists) {
            if (a.getId().equals(id)) return a;
        }
        return null;
    }

    public void showAll() {
        if (artists.isEmpty()) {
            System.out.println("No hay artistas registrados.");
            return;
        }
        for (Artist a : artists) System.out.println(a);
    }
}
