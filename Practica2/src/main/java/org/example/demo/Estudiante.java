package org.example.demo;

import javafx.scene.control.DatePicker;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;


@ToString
@Getter
public class Estudiante {

    @Override
    public String toString() {
        return "(" +
                nia +
                ",'" + nombre + '\'' +
                ",'" + fechaNacimiento.toLocalDate() + '\'' +
                ')';
    }

    private Integer nia;
    private String nombre;
    private java.sql.Date fechaNacimiento;

    public Estudiante(Integer nia, String nombre, Date fechaNacimiento){
        this.nia=nia;
        this.nombre=nombre;
        this.fechaNacimiento = (java.sql.Date) fechaNacimiento;
    }



}
