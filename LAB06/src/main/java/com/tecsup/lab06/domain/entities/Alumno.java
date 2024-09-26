package com.tecsup.lab06.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // ID del alumno

    @NotEmpty
    @Column(name = "nombre")
    private String nombre; // Nombre del alumno

    @NotEmpty
    @Column(name = "apellido")
    private String apellido; // Apellido del alumno

    @Column(name = "edad")
    private int edad; // Edad del alumno

    @Column(name = "grado")
    private int grado; // Grado del alumno

    @Column(name = "seccion")
    private String seccion; // Sección del alumno

    @Column(name = "promedio")
    private double promedio; // Promedio del alumno

    // Constructor vacío
    public Alumno() {
    }

    // Constructor con parámetros
    public Alumno(int id, String nombre, String apellido, int edad, int grado, String seccion, double promedio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.grado = grado;
        this.seccion = seccion;
        this.promedio = promedio;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", grado=" + grado +
                ", seccion='" + seccion + '\'' +
                ", promedio=" + promedio +
                '}';
    }
}