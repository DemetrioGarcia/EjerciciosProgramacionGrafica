package org.example;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {

        File archivo = new File("src/main/resources/ejemplo1.txt");
        File archivo2 = new File("src/main/resources/ejemplo1.txtmod");

        combinar(archivo,archivo2);
    }

    private static void crearFicheros(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("En que carpeta quieres crear los archivos?");
        String carpeta = entrada.next();
        System.out.println("Introduce el nombre de los archivos: ");
        String nombre = entrada.next();
        System.out.println("Cuantos archivos quieres crear?");
        int cantidad = entrada.nextInt();

        for (int i = 1; i < cantidad+1; i++) {
            File archivo = new File(carpeta+"/"+nombre+i+".txt");

            try{
                if (archivo.createNewFile()){
                    BufferedWriter escritor = new BufferedWriter(new FileWriter(carpeta+"/"+nombre+i+".txt"));
                    escritor.write("Este es el fichero "+nombre+i+".txt");
                    escritor.close();

                    System.out.println("El archivo "+archivo.getName() + " no existe");
                    System.out.println("Nombre: "+archivo.getName());
                    System.out.println("Longitud: "+archivo.length());
                    System.out.println("Ruta absoluta: "+archivo.getAbsolutePath());
                } else {
                    System.out.println("El archivo "+archivo.getName() + " ya existe");
                    System.out.println("Nombre: "+archivo.getName());
                    System.out.println("Longitud: "+archivo.length());
                    System.out.println("Ruta absoluta: "+archivo.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("Ha habido un problema");
                e.printStackTrace();
            }
        }
    }

    private static void listarArchivos(String rutaCarpeta){

        File carpeta = new File(rutaCarpeta);
        if (carpeta.isDirectory()){
            File[] archivos = carpeta.listFiles();
            if (archivos != null && archivos.length > 0) {
                for (File f : archivos){
                    if (f.isFile()) {
                        System.out.println(f.getName() + " - "+f.length()+"bytes");
                    }
                }
            } else {
                System.out.println("No se ha encontrado ningún archivo");
            }
        } else {
            System.out.println("La ruta proporcionada no es una carpeta");
        }
    }

    private static void listarArchivos(String rutaCarpeta, String extension){

        File carpeta = new File(rutaCarpeta);
        if (carpeta.isDirectory()){
            File[] archivos = carpeta.listFiles();
            if (archivos != null && archivos.length > 0) {
                for (File f : archivos){
                    if (f.isFile() && f.getName().endsWith(extension)) {
                        System.out.println(f.getName() + " - "+f.length()+"bytes");
                    }
                }
            } else {
                System.out.println("No se ha encontrado ningún archivo");
            }
        } else {
            System.out.println("La ruta proporcionada no es una carpeta");
        }
    }

    private static void leer(String palabra) {

        try {

            BufferedReader lector = new BufferedReader(new FileReader("src/main/resources/ejemplo1.txt"));
            String linea;
            int contador = 0;

            while ((linea = lector.readLine()) != null) {
                int index = 0;
                while ((index = linea.indexOf(palabra, index)) != -1){
                    contador++;
                    index += palabra.length();
                }
                System.out.println(linea);
            }

            lector.close();

            System.out.println("La palabra "+palabra+" aparece "+contador+" veces.");
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    private static void LeerEscaner(String palabra){
        try {
            File archivo = new File("src/main/resources/ejemplo1.txt");
            Scanner lector = new Scanner(archivo);

            String[] partes;
            int contador = 0;

            while (lector.hasNextLine()){
                String linea = lector.nextLine();
                partes = linea.split(" ");
                for (String parte : partes) {
                    if (Objects.equals(parte, palabra)) {
                        contador++;
                    }
                }
                System.out.println("Línea: "+linea);
            }

            lector.close();
            System.out.println("La palabra "+palabra+" aparece "+contador+" veces.");

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
            e.printStackTrace();
        }
    }

    private static void modificar(File archivo) {
        try {
            File archivoNuevo = new File(String.valueOf(archivo+"mod"));

            BufferedWriter writer = new BufferedWriter(new FileWriter(archivoNuevo));
            String[] partes;
            Scanner lector = new Scanner(archivo);

            while (lector.hasNextLine()){
                String linea = lector.nextLine();
                partes = linea.split(" ");
                for (String parte : partes) {

                    String partemod = parte.substring(0,1).toUpperCase()+parte.substring(1);
                    writer.write(partemod+" ");
                }
                System.out.println("Línea: "+linea);
            }
            writer.close();

            if (archivo.delete()) {
                archivoNuevo.renameTo(archivo);
                System.out.println("Archivo modificado correctamente");
            } else {
                System.out.println("No se ha podido reemplazar el archivo original.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void combinar(File archivo, File archivo2) throws IOException {

        File archivoNuevo = new File("src/main/resources/archivoCombinado.txt");
        Scanner lector = new Scanner(archivo);
        Scanner lector2 = new Scanner(archivo2);
        BufferedWriter writer = new BufferedWriter(new FileWriter(archivoNuevo));

        String[] partes;
        while (lector.hasNext() || lector2.hasNext()){
            if (lector.hasNext()){
                writer.write(lector.next()+" ");
            }
            if (lector2.hasNext()){
                writer.write(lector2.next()+" ");
            }
        }

        writer.close();
    }
}
