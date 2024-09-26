package com.tecsup.lab06.aop;

import com.tecsup.lab06.domain.entities.Auditoria;
import com.tecsup.lab06.domain.entities.Alumno;
import com.tecsup.lab06.domain.entities.Apoderados;
import com.tecsup.lab06.domain.persistence.ApoderadosDao;
import com.tecsup.lab06.domain.persistence.AuditoriaDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Calendar;

@Component
@Aspect
public class LOG {

    private Long tx;

    @Autowired
    private ApoderadosDao apoderadosDao;

    @Autowired
    private AuditoriaDao auditoriaDao; // Asegúrate de que este DAO esté inyectado

    @Around("execution(* com.tecsup.lab06.services.*ServiceImpl.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Long currTime = System.currentTimeMillis();
        tx = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = "tx[" + tx + "] - " + joinPoint.getSignature().getName();

        if (joinPoint.getArgs().length > 0) {
            logger.info(metodo + "() INPUT: " + Arrays.toString(joinPoint.getArgs()));
        }
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage());
            throw e; // Re-lanzar la excepción
        }
        logger.info(metodo + "(): tiempo transcurrido " + (System.currentTimeMillis() - currTime) + " ms.");
        return result;
    }

    @After("execution(* com.tecsup.lab06.controllers.*Controller.guardar*(..)) ||" +
            "execution(* com.tecsup.lab06.controllers.*Controller.editar*(..)) ||" +
            "execution(* com.tecsup.lab06.controllers.*Controller.eliminar*(..))")
    public void auditoria(JoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = joinPoint.getSignature().getName();
        Integer id = null; // Cambiar a Integer para manejar el ID como int

        Object[] parametros = joinPoint.getArgs(); // Mover la declaración aquí

        if (metodo.startsWith("guardar")) {
            if (parametros[0] instanceof Alumno) {
                Alumno alumno = (Alumno) parametros[0];
                id = alumno.getId(); // Asegúrate de que Alumno tenga getId()
                logger.info("Registrando auditoría para Alumno: " + id);
            } else if (parametros[0] instanceof Apoderados) {
                Apoderados apoderado = (Apoderados) parametros[0];
                id = apoderado.getId(); // Asegúrate de que Apoderado tenga getId()
                logger.info("Registrando auditoría para Apoderado: " + id);
            }
        } else if (metodo.startsWith("editar") || metodo.startsWith("eliminar")) {
            id = (Integer) parametros[0]; // Asegúrate de que el ID sea Integer
            logger.info("Registrando auditoría para ID: " + id);
        }

        if (id != null) {
            String traza = "tx[" + tx + "] - " + metodo;
            logger.info(traza + "(): registrando auditoria...");
            auditoriaDao.save(new Auditoria(
                    parametros[0] instanceof Alumno ? "alumnos" : "apoderados",
                    id,
                    Calendar.getInstance().getTime(),
                    "usuario", // Cambiar a un valor real de usuario si es necesario
                    metodo
            ));
        }
    }
}