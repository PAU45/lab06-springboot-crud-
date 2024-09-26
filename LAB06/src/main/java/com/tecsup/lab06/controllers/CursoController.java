package com.tecsup.lab06.controllers;

import com.tecsup.lab06.domain.entities.Curso;
import com.tecsup.lab06.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import java.util.Map;

@Controller
@SessionAttributes("curso")
public class CursoController {

    @Autowired
    CursoService cursoService;

    // Método para listar cursos
    @GetMapping("/listar")
    public String listarCurso(Model model) {
        model.addAttribute("cursos", cursoService.listar());
        return "listar"; // Asegúrate que tengas una vista llamada listar.html
    }

    // Método para mostrar el formulario de creación
    @GetMapping("/form")
    public String crear(Map<String, Object> model) {
        Curso curso = new Curso();
        model.put("curso", curso);
        return "form"; // Asegúrate que tengas una vista llamada form.html
    }

    // Método para guardar un curso
    @PostMapping("/form")
    public String guardar(@Valid @ModelAttribute("curso") Curso curso, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            return "form"; // Si hay errores, regresa al formulario
        }

        cursoService.grabar(curso); // Asegúrate que este método guarda correctamente
        status.setComplete(); // Completa la sesión del curso
        return "redirect:/listar"; // Redirige a la lista de cursos
    }

    // Método para editar un curso
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value="id") Integer id, Map<String, Object> model) {
        Curso curso = null;

        if (id > 0) {
            curso = cursoService.buscar(id); // Busca el curso por ID
        } else {
            return "redirect:/listar"; // Redirige si no es un ID válido
        }

        model.put("curso", curso);
        return "form"; // Muestra el formulario con el curso a editar
    }

    // Método para eliminar un curso
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value="id") Integer id) {
        if (id > 0) {
            cursoService.eliminar(id); // Elimina el curso por ID
        }
        return "redirect:/listar"; // Redirige a la lista de cursos
    }

    // Método para ver detalles de los cursos
    @GetMapping("/ver")
    public String ver(Model model) {
        model.addAttribute("cursos", cursoService.listar());
        model.addAttribute("titulo", "Lista de cursos");
        return "curso/ver"; // Asegúrate de tener la vista correspondiente
    }
}








