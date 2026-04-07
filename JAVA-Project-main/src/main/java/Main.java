package com.javeriana;

import java.util.Scanner;
import com.javeriana.model.Artist;
import com.javeriana.model.Customer;
import com.javeriana.model.Song;
import com.javeriana.model.Playlist;

public class Main {

    public static void main(String[] args) {
        // Inicializamos el Scanner
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        System.out.println("¡Bienvenido a la aplicación de música!");

        while (option != 5) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Crear Artista");
            System.out.println("2. Crear Cliente");
            System.out.println("3. Crear Playlist");
            System.out.println("4. Crear Canción");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            // Verificamos que el usuario ingrese un número para evitar errores
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); // Limpiar el "Enter" que deja nextInt() en el buffer
            } else {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpiar la entrada incorrecta
                continue; // Volver a mostrar el menú
            }

            switch (option) {
                case 1:
                    System.out.println("\n-- Creando Artista --");
                    System.out.print("Por favor ingrese el nombre del artista: ");
                    String artistName = scanner.nextLine();

                    Artist newArtist = new Artist(artistName);
                    System.out.println("¡Artista creado con éxito!");
                    System.out.println(newArtist.toString());
                    break;

                case 2:
                    System.out.println("\n-- Creando Cliente --");
                    System.out.print("Por favor ingrese su username: ");
                    String customerUserName = scanner.nextLine();

                    System.out.print("Por favor ingrese su contraseña: ");
                    String customerPassword = scanner.nextLine();

                    System.out.print("Por favor ingrese su nombre: ");
                    String customerName = scanner.nextLine();

                    System.out.print("Por favor ingrese su apellido: ");
                    String customerLastName = scanner.nextLine();

                    System.out.print("Por favor ingrese su edad: ");
                    int customerAge = scanner.nextInt();
                    scanner.nextLine();

                    Customer newCustomer = new Customer(customerUserName, customerPassword, customerName, customerLastName, customerAge);
                    System.out.println("¡Cliente creado con éxito!");
                    System.out.println(newCustomer.toString());
                    break;

                case 3:
                    System.out.println("\n-- Creando Playlist --");
                    System.out.print("Por favor ingrese el nombre de la Playlist: ");
                    String playlistName = scanner.nextLine();

                    Playlist newPlaylist = new Playlist(playlistName);
                    System.out.println("¡Playlist creada con éxito!");
                    System.out.println(newPlaylist.toString());
                    break;

                case 4:
                    System.out.println("\n-- Creando Canción --");
                    System.out.print("Por favor ingrese el nombre de la canción: ");
                    String songName = scanner.nextLine();

                    System.out.print("Por favor ingrese el género: ");
                    String songGenre = scanner.nextLine();

                    System.out.print("Por favor ingrese la duración en segundos: ");
                    int songDurationInSeconds = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Por favor ingrese el nombre del álbum: ");
                    String songAlbum = scanner.nextLine();

                    Song newSong = new Song(songName, songGenre, songDurationInSeconds, songAlbum);
                    System.out.println("¡Canción creada con éxito!");
                    System.out.println(newSong.toString());
                    break;

                case 5:
                    System.out.println("\nCerrando aplicación. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("\nOpción no válida. Por favor, seleccione un número del 1 al 5.");
                    break;
            }
        }

        scanner.close();
    }
}
