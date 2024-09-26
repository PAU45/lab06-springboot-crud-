package com.tecsup.lab06.repository;

import com.tecsup.lab06.domain.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
    // Puedes agregar métodos personalizados aquí si es necesario
}