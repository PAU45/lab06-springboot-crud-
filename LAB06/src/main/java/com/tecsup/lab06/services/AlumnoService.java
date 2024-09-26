package com.tecsup.lab06.services;

import com.tecsup.lab06.domain.entities.Alumno;
import java.util.List;

public interface AlumnoService {
    void grabar(Alumno alumno);
    void eliminar(int id); // Cambiar a int
    Alumno buscar(int id); // Cambiar a int
    List<Alumno> listar();
}