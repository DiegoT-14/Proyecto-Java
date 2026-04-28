package com.javeriana.controller;

import com.javeriana.model.Artist;
import com.javeriana.model.Customer;
import com.javeriana.model.Playlist;
import com.javeriana.model.Song;
import com.javeriana.service.ArtistService;
import com.javeriana.service.CustomerService;
import com.javeriana.service.PlaylistService;
import com.javeriana.service.SongService;

import java.util.List;
import java.util.UUID;

public class AdminController {

    private ArtistService artistService;
    private CustomerService customerService;
    private SongService songService;
    private PlaylistService playlistService;

    public AdminController() {
        this.artistService = new ArtistService();
        this.customerService = new CustomerService();
        this.songService = new SongService();
        this.playlistService = new PlaylistService();
    }

    // ==========================================
    //          MÓDULO INICIO / LOGIN
    // ==========================================

    public Customer login(String username, String password) {
        return customerService.login(username, password);
    }

    // ==========================================
    //          MÓDULO ADMINISTRADOR
    // ==========================================

    public void addArtist(Artist artist) {
        artistService.add(artist);
    }

    public void addCustomer(Customer customer) {
        customerService.add(customer);
    }

    public void addSong(Song song) {
        songService.add(song);
    }

    public void addPlaylist(Playlist playlist) {
        playlistService.add(playlist);
    }

    public void showArtists() {
        artistService.showAll();
    }

    public void showCustomers() {
        customerService.showAll();
    }

    public void showSongs() {
        songService.showAll();
    }

    public void showPlaylists() {
        // En el admin, mostramos la lista "global" si existiera,
        // pero por ahora delegamos al servicio.
        playlistService.showAll();
    }

    public void removeCustomer(UUID customerId) {
        customerService.delete(customerId);
    }

    public void removePlaylist(UUID playlistId) {
        customerService.removePlaylistFromAllCustomers(playlistId);
        playlistService.delete(playlistId);
    }

    public void removeSong(UUID songId) {
        // Regla: Al eliminar canción, quitar de todas las playlists
        playlistService.removeSongFromAllPlaylists(songId);
        songService.delete(songId);
    }

    public void removeArtist(UUID artistId) {
        // Regla: Al eliminar artista, eliminar sus canciones y de las playlists
        List<UUID> deletedSongIds = songService.removeSongsByArtistId(artistId);

        for (UUID songId : deletedSongIds) {
            playlistService.removeSongFromAllPlaylists(songId);
        }

        artistService.delete(artistId);
    }

    public Artist getArtistById(UUID artistId) {
        return artistService.findById(artistId);
    }

    // ==========================================
    //             MÓDULO CLIENTE
    // ==========================================

    // 1. Seguir a un artista
    public void followArtist(Customer loggedCustomer, UUID artistId) {
        Artist artist = artistService.findById(artistId);
        customerService.followArtist(loggedCustomer, artist, artistId);
    }

    // 2. Dejar de seguir a un artista
    public void unfollowArtist(Customer loggedCustomer, UUID artistId) {
        customerService.unfollowArtist(loggedCustomer, artistId);
    }

    // 3. Agregar una lista de reproducción al cliente
    public void addPlaylistToCustomer(Customer loggedCustomer, Playlist playlist) {
        // El servicio valida el nombre (IllegalArgumentException)
        playlistService.add(playlist);
        loggedCustomer.getPlaylists().add(playlist);
        System.out.println("\nPlaylist '" + playlist.getName() + "' creada y agregada a tu cuenta.");
    }

    // 4. Eliminar una lista de reproducción del cliente
    public void removePlaylistFromCustomer(Customer loggedCustomer, UUID playlistId) {
        playlistService.deleteFromList(loggedCustomer.getPlaylists(), playlistId);
    }

    // 5. Agregar una canción a una lista de reproducción del cliente
    public void addSongToPlaylist(Customer loggedCustomer, UUID playlistId, UUID songId) {
        Playlist playlist = playlistService.findByIdInList(loggedCustomer.getPlaylists(), playlistId);

        if (playlist == null) {
            System.out.println("\nLa playlist con id " + playlistId + " no existe en el usuario");
            return;
        }

        Song song = songService.findById(songId);
        playlistService.addSongToPlaylist(playlist, song, songId);
    }

    // 6. Eliminar una canción de una lista de reproducción del cliente
    public void removeSongFromPlaylist(Customer loggedCustomer, UUID playlistId, UUID songId) {
        Playlist playlist = playlistService.findByIdInList(loggedCustomer.getPlaylists(), playlistId);

        if (playlist == null) {
            System.out.println("\nLa playlist con id " + playlistId + " no existe en el usuario");
            return;
        }

        playlistService.removeSongFromPlaylist(playlist, songId);
    }

    // 7. Obtener información de todas las playlists del cliente
    public void showCustomerPlaylists(Customer loggedCustomer) {
        if (loggedCustomer.getPlaylists().isEmpty()) {
            System.out.println("\nNo tienes playlists creadas.");
            return;
        }
        for (Playlist p : loggedCustomer.getPlaylists()) {
            System.out.println(p.toString());
        }
    }

    // 8. Obtener información de todas las canciones de una playlist del cliente
    public void showSongsFromCustomerPlaylist(Customer loggedCustomer, UUID playlistId) {
        Playlist playlist = playlistService.findByIdInList(loggedCustomer.getPlaylists(), playlistId);

        if (playlist == null) {
            System.out.println("\nLa playlist con id " + playlistId + " no existe en el usuario");
            return;
        }

        if (playlist.getSongs().isEmpty()) {
            System.out.println("\nLa playlist '" + playlist.getName() + "' está vacía.");
        } else {
            System.out.println("\n--- Canciones en " + playlist.getName() + " ---");
            for (Song s : playlist.getSongs()) {
                System.out.println(s.toString());
            }
        }
    }

    // Auxiliar para mostrar artistas que sigue el cliente
    public void showFollowedArtists(Customer loggedCustomer) {
        if (loggedCustomer.getFollowedArtists().isEmpty()) {
            System.out.println("\nNo sigues a ningún artista.");
            return;
        }
        for (Artist a : loggedCustomer.getFollowedArtists()) {
            System.out.println(a.toString());
        }
    }
}
