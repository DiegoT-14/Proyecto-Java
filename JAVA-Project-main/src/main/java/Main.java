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

        System.out.println("\n---BIENVENIDO---");

        // === BUCLE EXTERNO: MENÚ PRINCIPAL ===
        while (!exitProgram) {
            System.out.println("\nSeleccione el modulo a utilizar:\n");
            System.out.println("1. Modulo administrador");
            System.out.println("0. Salir");
            System.out.print("\nOpcion: ");

            int mainOption = -1;

            if (scanner.hasNextInt()) {
                mainOption = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Por favor, ingrese un numero valido.");
                scanner.nextLine();
                continue;
            }

            if (mainOption == 0) {
                exitProgram = true;
                System.out.println("\n---Cerrando aplicacion. ¡Hasta pronto!---");
            } else if (mainOption == 1) {

                // === BUCLE INTERNO: MENÚ ADMINISTRADOR ===
                boolean exitAdmin = false;
                while (!exitAdmin) {
                    System.out.println("\n--- MODULO ADMINISTRADOR ---\n");
                    System.out.println("1. Crear artista y agregarlo a la base de datos.");
                    System.out.println("2. Eliminar artista de la base de datos.");
                    System.out.println("3. Crear Cliente y agregarlo a la base de datos.");
                    System.out.println("4. Eliminar Cliente de la base de datos.");
                    System.out.println("5. Agregar Playlist a la base de datos.");
                    System.out.println("6. Eliminar Playlist de la base de datos.");
                    System.out.println("7. Crear cancion y agregarla a la base de datos.");
                    System.out.println("8. Eliminar cancion de la base de datos y las playlists en las que exista.");
                    System.out.println("9. Ver la lista de clientes.");
                    System.out.println("10. Ver la lista de canciones.");
                    System.out.println("11. Ver la lista de artistas.");
                    System.out.println("12. Ver la lista de playlists.");
                    System.out.println("0. Volver al menu principal.");
                    System.out.print("\nSeleccione una opcion: ");

                    int adminOption = -1;

                    if (scanner.hasNextInt()) {
                        adminOption = scanner.nextInt();
                        scanner.nextLine();
                    } else {
                        System.out.println("Por favor, ingrese un número valido.");
                        scanner.nextLine();
                        continue;
                    }

                    switch (adminOption) {
                        case 1:
                            System.out.print("\nIngrese el nombre del artista: ");
                            String artistName = scanner.nextLine();
                            adminController.addArtist(new Artist(artistName));
                            System.out.println("¡Artista agregado con exito!");
                            break;

                        case 2:
                            System.out.println("\n--- Lista de Artistas ---");
                            adminController.showArtists();
                            System.out.print("Ingrese el ID del artista a eliminar: ");
                            try {
                                UUID idArtist = UUID.fromString(scanner.nextLine());
                                adminController.removeArtist(idArtist);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Formato de ID invalido.");
                            }
                            break;

                        case 3:
                            System.out.print("\nIngrese username: ");
                            String username = scanner.nextLine();
                            System.out.print("Ingrese contraseña: ");
                            String password = scanner.nextLine();
                            System.out.print("Ingrese nombre: ");
                            String name = scanner.nextLine();
                            System.out.print("Ingrese apellido: ");
                            String lastName = scanner.nextLine();

                            System.out.print("Ingrese edad: ");
                            int age = 0;
                            if (scanner.hasNextInt()) {
                                age = scanner.nextInt();
                            }
                            scanner.nextLine();

                            adminController.addCustomer(new Customer(username, password, name, lastName, age));
                            System.out.println("¡Cliente agregado con exito!");
                            break;

                        case 4:
                            System.out.println("\n--- Lista de Clientes ---");
                            adminController.showCustomers();
                            System.out.print("Ingrese el ID del cliente a eliminar: ");
                            try {
                                UUID idCustomer = UUID.fromString(scanner.nextLine());
                                adminController.removeCustomer(idCustomer);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Formato de ID invalido.");
                            }
                            break;

                        case 5:
                            System.out.print("\nIngrese el nombre de la Playlist: ");
                            String playlistName = scanner.nextLine();
                            adminController.addPlaylist(new Playlist(playlistName));
                            System.out.println("¡Playlist agregada con exito!");
                            break;

                        case 6:
                            System.out.println("\n--- Lista de Playlists ---");
                            adminController.showPlaylists();
                            System.out.print("Ingrese el ID de la Playlist a eliminar: ");
                            try {
                                UUID idPlaylist = UUID.fromString(scanner.nextLine());
                                adminController.removePlaylist(idPlaylist);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Formato de ID invalido.");
                            }
                            break;

                        case 7:
                            System.out.println("\n--- Creando Canción ---");
                            System.out.println("Primero, seleccione el artista que la interpreta.");
                            adminController.showArtists();
                            System.out.print("Ingrese el ID del artista: ");

                            try {
                                UUID idArtistForSong = UUID.fromString(scanner.nextLine());
                                Artist initialArtist = adminController.getArtistById(idArtistForSong);

                                if (initialArtist == null) {
                                    System.out.println("Error: No se encontro ningun artista con ese ID.");
                                } else {
                                    System.out.print("Ingrese el nombre de la cancion: ");
                                    String songName = scanner.nextLine();
                                    System.out.print("Ingrese el genero: ");
                                    String genre = scanner.nextLine();

                                    System.out.print("Ingrese la duracion en segundos: ");
                                    int duration = 0;
                                    if (scanner.hasNextInt()) {
                                        duration = scanner.nextInt();
                                    }
                                    scanner.nextLine();

                                    System.out.print("Ingrese el nombre del album: ");
                                    String album = scanner.nextLine();

                                    adminController.addSong(new Song(songName, genre, duration, album, initialArtist));
                                    System.out.println("¡Cancion agregada con exito!");
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Formato de ID invalido.");
                            }
                            break;

                        case 8:
                            System.out.println("\n--- Lista de Canciones ---");
                            adminController.showSongs();
                            System.out.print("Ingrese el ID de la cancion a eliminar: ");
                            try {
                                UUID idSong = UUID.fromString(scanner.nextLine());
                                adminController.removeSong(idSong);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Formato de ID invalido.");
                            }
                            break;

                        case 9:
                            System.out.println("\n--- Lista de Clientes ---");
                            adminController.showCustomers();
                            break;

                        case 10:
                            System.out.println("\n--- Lista de Canciones ---");
                            adminController.showSongs();
                            break;

                        case 11:
                            System.out.println("\n--- Lista de Artistas ---");
                            adminController.showArtists();
                            break;

                        case 12:
                            System.out.println("\n--- Lista de Playlists ---");
                            adminController.showPlaylists();
                            break;

                        case 0:
                            exitAdmin = true;
                            System.out.println("\nVolviendo al menú principal...");
                            break;

                        default:
                            System.out.println("\nOpción no válida. Intente nuevamente.");
                            break;
                    }
                }
            } else {
                System.out.println("Opcion no valida. Por favor seleccione 1 o 0.");
            }
        }

        scanner.close();
    }
}
