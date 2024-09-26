package com.tecsup.lab06.aop;

import com.tecsup.lab06.domain.entities.Auditoria;
import com.tecsup.lab06.domain.entities.Curso;
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
public class LogginAspecto {

    private Long tx;

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Around("execution(* com.tecsup.lab06.services.*ServiceImpl.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result =  null;
        Long currTime = System.currentTimeMillis();
        tx = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = "tx[" + tx + "] - " + joinPoint.getSignature().getName();
        //logger.info(metodo + "()");
        if(joinPoint.getArgs().length > 0)
            logger.info(metodo + "() INPUT:" + Arrays.toString(joinPoint.getArgs()));
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage());
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
        Integer id = null;
        if(metodo.startsWith("guardar")){
            Object[] parametros = joinPoint.getArgs();
            Curso curso = (Curso)parametros[0];
            id = curso.getId();
        }
        else if(metodo.startsWith("editar")){
            Object[] parametros = joinPoint.getArgs();
            id = (Integer)parametros[0];
        }
        else if(metodo.startsWith("eliminar")){
            Object[] parametros = joinPoint.getArgs();
            id = (Integer)parametros[0];
        }
        String traza = "tx[" + tx + "] - " + metodo;
        logger.info(traza + "(): registrando auditoria...");
        auditoriaDao.save(new Auditoria("cursos", id, Calendar.getInstance().getTime(),
                "usuario", metodo));
    }
}
