package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static Scanner entrada = new Scanner(System.in);
    static ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {


        boolean aux = true;
        while (aux) {
            System.out.println();
            System.out.println("Elige una opción: (X para salir)");
            System.out.println("1.- Crear videojuegos.");
            System.out.println("2.- Guardar JSON.");
            System.out.println("3.- Mostrar JSON");
            System.out.println("4.- Mostrar los videojuegos para consola <30€");
            System.out.println();
            char opcion = entrada.next().charAt(0);
            entrada.nextLine();

            switch (opcion) {
                case '1':
                    crearVideojuego();
                    break;
                case '2':
                    guardarJSON();
                    break;
                case '3':
                    mostrarJSON();
                    break;
                case '4':
                    mostrarMenores30();
                    break;
                default:
                    aux=false;
                    break;
            }
        }
    }

    private static void crearVideojuego(){

        System.out.print("Introduce el nombre: ");
        String nombre = entrada.nextLine();

        System.out.print("Introduce la plataforma: ");
        String plataforma = entrada.next();

        System.out.print("Introduce el precio: ");
        int precio = entrada.nextInt();

        System.out.println("Esta Disponible? (S/N)");
        char disponibilidad = entrada.next().toUpperCase().charAt(0);
        entrada.nextLine();
        boolean disponible = (disponibilidad == 'S');

        System.out.println("Generos: (separados por espacios)");
        String genero = entrada.nextLine();

        String[] arrayGeneros = genero.split(" ");
        ArrayList<String> listaGeneros = new ArrayList<>(Arrays.asList(arrayGeneros));

        listaVideojuegos.add(new Videojuego(nombre,plataforma,precio,disponible, listaGeneros));
    }

    private static void guardarJSON(){

        String json = gson.toJson(listaVideojuegos);

        try (FileWriter writer = new FileWriter("src/main/resources/videojuegos.json")) {
            gson.toJson(listaVideojuegos, writer);
            System.out.println("Guardado en videojuegos.json");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void mostrarJSON(){

        try {
            FileReader reader = new FileReader("src/main/resources/videojuegos.json");

            Type tipoLista = new TypeToken<ArrayList<Videojuego>>(){}.getType();
            listaVideojuegos = gson.fromJson(reader,tipoLista);

            for (Videojuego videojuego2 : listaVideojuegos) {
                videojuego2 = gson.fromJson(reader, Videojuego.class);
                System.out.println("Nombre: " + videojuego2.getNombre());
                System.out.println("Plataforma: " + videojuego2.getPlataforma());
                System.out.println("Precio: " + videojuego2.getPrecio());
                System.out.println("Disponible: " + videojuego2.isDisponible());
                System.out.println("Generos: " + videojuego2.getListaGeneros());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mostrarMenores30(){
        try {
            FileReader reader = new FileReader("src/main/resources/videojuegos.json");
            for (Videojuego videojuego : listaVideojuegos) {
                if (videojuego.getPrecio() < 30 && Objects.equals(videojuego.getPlataforma(), "consola")) {
                    Videojuego videojuego2 = gson.fromJson(reader, Videojuego.class);
                    System.out.println("Nombre: " + videojuego2.getNombre());
                    System.out.println("Plataforma: " + videojuego2.getPlataforma());
                    System.out.println("Precio: " + videojuego2.getPrecio());
                    System.out.println("Disponible: " + videojuego2.isDisponible());
                    System.out.println("Generos: " + videojuego2.getListaGeneros());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}