package com.tecsup.lab06.domain.persistence;



import com.tecsup.lab06.domain.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoDao extends CrudRepository<Curso,Integer> {
}



