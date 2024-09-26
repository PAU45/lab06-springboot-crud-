package com.tecsup.lab06.services;

import com.tecsup.lab06.domain.entities.Alumno;
import com.tecsup.lab06.repository.AlumnoRepository; // Asegúrate de que esta importación sea correcta
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository; // Cambia a AlumnoRepository

    @Override
    @Transactional
    public void grabar(Alumno alumno) {
        alumnoRepository.save(alumno); // Guardar el alumno (nuevo o actualizado)
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        alumnoRepository.deleteById(id); // Eliminar el alumno por ID
    }

    @Override
    @Transactional(readOnly = true)
    public Alumno buscar(int id) {
        return alumnoRepository.findById(id).orElse(null); // Buscar el alumno por ID
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listar() {
        return alumnoRepository.findAll(); // Listar todos los alumnos
    }
}