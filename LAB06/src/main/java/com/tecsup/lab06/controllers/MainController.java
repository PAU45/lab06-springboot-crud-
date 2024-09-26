package com.tecsup.lab06.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String inicio() {
        return "inicio"; // Vista de inicio
    }



    @GetMapping("/docente")
    public String docente() {
        return "docente"; // Vista para docentes
    }

    @GetMapping("/alumnos") // Nueva ruta para listar alumnos
    public String listarAlumnos() {
        return "listarAlumnos"; // Vista para listar alumnos
    }
}