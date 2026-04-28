package com.javeriana.service;

import com.javeriana.model.Artist;
import com.javeriana.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService() {
        this.customers = new ArrayList<>();
    }

    // --- MÉTODOS DEL ADMINISTRADOR ---

    public void add(Customer c) {
        // Regla de Negocio 3: Validar campos no nulos ni vacíos
        if (isInvalid(c.getUsername()) || isInvalid(c.getPassword()) ||
                isInvalid(c.getName()) || isInvalid(c.getLastName())) {
            throw new IllegalArgumentException("Los campos no pueden estar vacios o ser null");
        }

        // Regla: Validar si el username ya existe
        for (Customer existing : customers) {
            if (existing.getUsername().equalsIgnoreCase(c.getUsername())) {
                System.out.println("El cliente con nombre de usuario " + c.getUsername() + " ya existe");
                return;
            }
        }

        // Regla: Formato de nombre de usuario (8 a 10 caracteres, letras, números, _ o -)
        if (!c.getUsername().matches("^[a-zA-Z0-9_-]{8,10}$")) {
            throw new IllegalArgumentException("El nombre de usuario debe tener entre 8 y 10 caracteres y debe contener solo letras (Mayusculas o minusculas), numeros o los caracteres '_' y '-'");
        }

        // Regla: Validación de contraseña (al menos 8 chars, 1 Mayús, 1 Minús, 1 Número, 1 Especial)
        String passRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@$%^&*-]).{8,}$";
        if (!c.getPassword().matches(passRegex)) {
            throw new IllegalArgumentException("La contrasenia debe tener al menos 8 caracteres, una letra mayuscula, una letra minuscula, un numero y un caracter especial");
        }

        // Regla: Edad mayor a 14 años
        if (c.getAge() <= 14) {
            throw new IllegalArgumentException("La edad debe ser mayor a 14 años");
        }

        this.customers.add(c);
    }

    public void delete(UUID id) {
        // Regla de Negocio 4: Validar ID
        if (id == null) {
            throw new IllegalArgumentException("El id del cliente no puede estar vacio o ser null");
        }

        boolean removed = customers.removeIf(c -> c.getId().equals(id));
        if (!removed) {
            System.out.println("El cliente con id " + id + " no existe");
        } else {
            System.out.println("Cliente eliminado del sistema.");
        }
    }

    // Corrigiendo error: Cannot resolve method 'removePlaylistFromAllCustomers'
    // Esta es la lógica de "cascada" para cuando el admin borra una playlist global
    public void removePlaylistFromAllCustomers(UUID playlistId) {
        for (Customer customer : customers) {
            customer.getPlaylists().removeIf(p -> p.getId().equals(playlistId));
        }
    }

    // --- MÉTODOS DEL MÓDULO CLIENTE ---

    public Customer login(String username, String password) {
        // Regla de Negocio 9: Loggearse
        for (Customer c : customers) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    public void followArtist(Customer c, Artist a, UUID artistId) {
        // Regla de Cliente 1: Seguir artista
        if (a == null) {
            System.out.println("El artista con id " + artistId + " no existe");
            return;
        }
        c.getFollowedArtists().add(a);
        System.out.println("Ahora sigues a: " + a.getName());
    }

    public void unfollowArtist(Customer c, UUID artistId) {
        // Regla de Cliente 2: Dejar de seguir artista
        boolean removed = c.getFollowedArtists().removeIf(a -> a.getId().equals(artistId));
        if (!removed) {
            System.out.println("El artista con id " + artistId + " no existe en la lista de artistas del usuario");
        } else {
            System.out.println("Has dejado de seguir al artista.");
        }
    }

    // --- AUXILIARES ---

    private boolean isInvalid(String s) {
        return s == null || s.trim().isEmpty();
    }

    public void showAll() {
        if (customers.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Customer c : customers) {
            System.out.println(c.toString());
        }
    }
}
