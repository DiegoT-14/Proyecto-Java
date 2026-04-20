package com.javeriana.controller;

import com.javeriana.model.Artist;
import com.javeriana.model.Customer;
import com.javeriana.model.Playlist;
import com.javeriana.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminController {

    private List<Artist> artists;
    private List<Customer> customers;
    private List<Song> songs;
    private List<Playlist> playlists;

    public AdminController() {
        this.artists = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.songs = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    public void showArtists() {
        if (artists.isEmpty()) {
            System.out.println("No hay artistas registrados.");
            return;
        }
        for (Artist artist : artists) {
            System.out.println(artist.toString());
        }
    }

    public void showCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }

    public void showSongs() {
        if (songs.isEmpty()) {
            System.out.println("No hay canciones registradas.");
            return;
        }
        for (Song song : songs) {
            System.out.println(song.toString());
        }
    }

    public void showPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("No hay listas de reproduccion registradas.");
            return;
        }
        for (Playlist playlist : playlists) {
            System.out.println(playlist.toString());
        }
    }

    public void removeCustomer(UUID customerId) {
        customers.removeIf(customer -> customer.getId().equals(customerId));
        System.out.println("Cliente eliminado del sistema.");
    }

    public void removePlaylist(UUID playlistId) {

        for (Customer customer : customers) {
            customer.getplaylists().removeIf(playlist -> playlist.getId().equals(playlistId));
        }
        playlists.removeIf(playlist -> playlist.getId().equals(playlistId));
        System.out.println("Playlist eliminada del sistema y de las cuentas de los clientes.");
    }

    public void removeSong(UUID songId) {

        for (Playlist playlist : playlists) {
            playlist.getSongs().removeIf(song -> song.getId().equals(songId));
        }
        songs.removeIf(song -> song.getId().equals(songId));
        System.out.println("Cancion y sus referencias eliminadas correctamente del sistema.");
    }

    public void removeArtist(UUID artistId) {

        List<UUID> songsToDelete = new ArrayList<>();

        for (Song song : songs) {
            for (Artist a : song.getArtists()) {
                if (a.getId().equals(artistId)) {
                    songsToDelete.add(song.getId());
                    break;
                }
            }
        }

        for (UUID songId : songsToDelete) {
            removeSong(songId);
        }

        artists.removeIf(artist -> artist.getId().equals(artistId));
        System.out.println("Artista y toda su discografia eliminados del sistema.");
    }

    public Artist getArtistById(UUID artistId) {
        for (Artist artist : artists) {
            if (artist.getId().equals(artistId)) {
                return artist;
            }
        }
        return null;
    }
}
