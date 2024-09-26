package com.tecsup.lab06.domain.persistence;


import com.tecsup.lab06.domain.entities.Auditoria;
import org.springframework.data.repository.CrudRepository;


public interface AuditoriaDao extends CrudRepository<Auditoria, Integer> {

}
