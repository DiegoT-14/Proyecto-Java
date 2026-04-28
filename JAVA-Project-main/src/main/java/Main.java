package com.javeriana;

import java.util.Scanner;
import java.util.UUID;
import com.javeriana.model.Artist;
import com.javeriana.model.Customer;
import com.javeriana.model.Playlist;
import com.javeriana.model.Song;
import com.javeriana.controller.AdminController;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdminController adminController = new AdminController();

        boolean exitProgram = false;

        System.out.println("\n--- BIENVENIDO ---");

        while (!exitProgram) {
            System.out.println("\nSeleccione el modulo a utilizar:");
            System.out.println("\n1. Modulo Administrador");
            System.out.println("2. Modulo Cliente (Login)");
            System.out.println("0. Salir");
            System.out.print("\nOpcion: ");

            int mainOption = leerEntero(scanner);

            switch (mainOption) {
                case 1:
                    menuAdministrador(adminController, scanner);
                    break;
                case 2:
                    loginCliente(adminController, scanner);
                    break;
                case 0:
                    exitProgram = true;
                    System.out.println("\nCerrando aplicacion...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
        scanner.close();
    }

    // ==========================================
    //           SISTEMA DE LOGIN (Punto 9)
    // ==========================================
    private static void loginCliente(AdminController adminController, Scanner scanner) {
        System.out.println("\n--- INICIO DE SESION ---");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        Customer loggedCustomer = adminController.login(user, pass);

        if (loggedCustomer != null) {
            System.out.println("\n¡Login exitoso! Bienvenido, " + loggedCustomer.getName());
            menuCliente(loggedCustomer, adminController, scanner);
        } else {
            System.out.println("\nError: Credenciales incorrectas. No se puede mostrar el menú.");
        }
    }

    // ==========================================
    //           MODULO CLIENTE (Punto 4 Calificación)
    // ==========================================
    private static void menuCliente(Customer loggedCustomer, AdminController adminController, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- MENU CLIENTE (" + loggedCustomer.getUsername() + ") ---");
            System.out.println("\n1. Seguir a un artista");
            System.out.println("2. Dejar de seguir a un artista");
            System.out.println("3. Crear lista de reproduccion");
            System.out.println("4. Eliminar lista de reproduccion");
            System.out.println("5. Agregar cancion a una lista");
            System.out.println("6. Eliminar cancion de una lista");
            System.out.println("7. Ver mis listas de reproduccion");
            System.out.println("8. Ver canciones de una lista");
            System.out.println("0. Salir (Cerrar Sesion)");
            System.out.print("\nSeleccione una opcion: ");

            int opt = leerEntero(scanner);
            try {
                switch (opt) {
                    case 1:
                        adminController.showArtists();
                        System.out.print("\nID del artista a seguir: ");
                        adminController.followArtist(loggedCustomer, leerUUID(scanner));
                        break;
                    case 2:
                        adminController.showFollowedArtists(loggedCustomer);
                        System.out.print("\nID del artista para dejar de seguir: ");
                        adminController.unfollowArtist(loggedCustomer, leerUUID(scanner));
                        break;
                    case 3:
                        System.out.print("\nNombre de la nueva lista: ");
                        String pName = scanner.nextLine();
                        adminController.addPlaylistToCustomer(loggedCustomer, new Playlist(pName));
                        break;
                    case 4:
                        adminController.showCustomerPlaylists(loggedCustomer);
                        System.out.print("\nID de la lista a eliminar: ");
                        adminController.removePlaylistFromCustomer(loggedCustomer, leerUUID(scanner));
                        break;
                    case 5:
                        adminController.showCustomerPlaylists(loggedCustomer);
                        System.out.print("\nID de la lista destino: ");
                        UUID pIdAdd = leerUUID(scanner);
                        adminController.showSongs();
                        System.out.print("\nID de la cancion a agregar: ");
                        adminController.addSongToPlaylist(loggedCustomer, pIdAdd, leerUUID(scanner));
                        break;
                    case 6:
                        adminController.showCustomerPlaylists(loggedCustomer);
                        System.out.print("\nID de la lista: ");
                        UUID pIdRem = leerUUID(scanner);
                        adminController.showSongsFromCustomerPlaylist(loggedCustomer, pIdRem);
                        System.out.print("ID de la cancion a remover: ");
                        adminController.removeSongFromPlaylist(loggedCustomer, pIdRem, leerUUID(scanner));
                        break;
                    case 7:
                        adminController.showCustomerPlaylists(loggedCustomer);
                        break;
                    case 8:
                        adminController.showCustomerPlaylists(loggedCustomer);
                        System.out.print("\nID de la lista a consultar: ");
                        adminController.showSongsFromCustomerPlaylist(loggedCustomer, leerUUID(scanner));
                        break;
                    case 0:
                        exit = true;
                        System.out.println("\nSesion cerrada.");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    // ==========================================
    //           MODULO ADMINISTRADOR
    // ==========================================
    private static void menuAdministrador(AdminController adminController, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- MODULO ADMINISTRADOR ---");
            System.out.println("1. Agregar Artista\n2. Eliminar Artista\n3. Agregar Cliente\n4. Eliminar Cliente\n5. Agregar Cancion\n6. Eliminar Cancion\n7. Ver Todo\n0. Volver");
            System.out.print("\nSeleccione: ");

            int opt = leerEntero(scanner);
            try {
                switch (opt) {
                    case 1:
                        System.out.print("\nNombre Artista: ");
                        adminController.addArtist(new Artist(scanner.nextLine()));
                        System.out.println("\nArtista creado con exito!.");
                        break;
                    case 2:
                        adminController.showArtists();
                        System.out.print("\nID a eliminar: ");
                        adminController.removeArtist(leerUUID(scanner));
                        break;
                    case 3:
                        System.out.print("\nUsername: "); String u = scanner.nextLine();
                        System.out.print("Password: "); String p = scanner.nextLine();
                        System.out.print("Nombre: "); String n = scanner.nextLine();
                        System.out.print("Apellido: "); String a = scanner.nextLine();
                        System.out.print("Edad: "); int age = leerEntero(scanner);
                        adminController.addCustomer(new Customer(u, p, n, a, age));
                        System.out.println("\n Usuario creado con exito!.");
                        break;
                    case 4:
                        adminController.showCustomers();
                        System.out.print("\nID Cliente: ");
                        adminController.removeCustomer(leerUUID(scanner));
                        break;
                    case 5:
                        adminController.showArtists();
                        System.out.print("\nID Artista: ");
                        Artist art = adminController.getArtistById(leerUUID(scanner));
                        if(art != null) {
                            System.out.print("Nombre: "); String sN = scanner.nextLine();
                            System.out.print("Genero: "); String sG = scanner.nextLine();
                            System.out.print("Duracion (seg): "); int sD = leerEntero(scanner);
                            System.out.print("Album: "); String sA = scanner.nextLine();
                            adminController.addSong(new Song(sN, sG, sD, sA, art));
                        } else { System.out.println("\nArtista no encontrado."); }
                        break;
                    case 6:
                        adminController.showSongs();
                        System.out.print("\nID Cancion: ");
                        adminController.removeSong(leerUUID(scanner));
                        break;
                    case 7:
                        System.out.println("\n--- ESTADO DEL SISTEMA ---");
                        adminController.showArtists();
                        adminController.showSongs();
                        adminController.showCustomers();
                        break;
                    case 0: exit = true; break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS AUXILIARES DE LECTURA ---
    private static int leerEntero(Scanner scanner) {
        try {
            int val = Integer.parseInt(scanner.nextLine());
            return val;
        } catch (Exception e) {
            return -1;
        }
    }

    private static UUID leerUUID(Scanner scanner) {
        try {
            return UUID.fromString(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Formato de ID invalido.");
            return null;
        }
    }
}
