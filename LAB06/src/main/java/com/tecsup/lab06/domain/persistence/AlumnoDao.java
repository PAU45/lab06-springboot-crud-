package com.tecsup.lab06.domain.persistence;

import com.tecsup.lab06.domain.entities.Alumno;
import org.springframework.data.repository.CrudRepository;

public interface AlumnoDao extends CrudRepository<Alumno, Integer> { // Cambia Integer por Long
}
