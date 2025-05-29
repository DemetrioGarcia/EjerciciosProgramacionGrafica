package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class Videojuego {

    private String nombre;
    private String plataforma;
    private int precio;
    private boolean disponible;
    private ArrayList<String> listaGeneros;


}
