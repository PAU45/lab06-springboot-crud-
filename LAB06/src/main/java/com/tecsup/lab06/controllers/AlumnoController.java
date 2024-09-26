package com.tecsup.lab06.controllers;

import jakarta.servlet.http.HttpServletResponse;
import com.tecsup.lab06.domain.entities.Alumno;
import com.tecsup.lab06.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Para .xlsx
import com.tecsup.lab06.views.AlumnoXlsView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    // Listar todos los alumnos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Alumno> alumnos = alumnoService.listar();
        model.addAttribute("alumnos", alumnos);
        return "listarAlumnos"; // Nombre de la vista Thymeleaf
    }

    // Mostrar el formulario de edición
    @GetMapping("/editar/{id}")
    public String editarAlumno(@PathVariable int id, Model model) {
        Alumno alumno = alumnoService.buscar(id);
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            return "editarAlumno"; // Vista para editar alumno
        }
        return "redirect:/alumnos/listar"; // Redirigir si no se encuentra el alumno
    }

    // Procesar la actualización del alumno
    @PostMapping("/actualizar")
    public String actualizarAlumno(@ModelAttribute Alumno alumno) {
        alumnoService.grabar(alumno); // Guardar el alumno actualizado
        return "redirect:/alumnos/listar"; // Redirigir a la lista de alumnos
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") int id) {
        if (id > 0) {
            alumnoService.eliminar(id);
        }
        return "redirect:/alumnos/listar";
    }
    // Mostrar el formulario para crear un nuevo alumno
    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("alumno", new Alumno()); // Crear un nuevo objeto Alumno
        return "crearAlumno"; // Nombre de la vista para crear alumno
    }

    // Procesar la creación del alumno
    @PostMapping("/crear")
    public String crearAlumno(@ModelAttribute Alumno alumno) {
        alumnoService.grabar(alumno); // Guardar el nuevo alumno
        return "redirect:/alumnos/listar"; // Redirigir a la lista de alumnos
    }

    // Buscar un alumno por ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Alumno> buscar(@PathVariable int id) {
        Alumno alumno = alumnoService.buscar(id);
        return alumno != null ? new ResponseEntity<>(alumno, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Método para ver alumnos y generar PDF si se solicita
    @GetMapping("/ver")
    public String verAlumnos(Model model, @RequestParam(required = false) String format) {
        List<Alumno> alumnos = alumnoService.listar();
        model.addAttribute("alumnos", alumnos);
        model.addAttribute("titulo", "Lista de alumnos");
        if ("pdf".equals(format)) {
            return "alumnoPdfView"; // Nombre de la vista PDF
        }
        return "listarAlumnos"; // Vista normal si no se pide PDF
    }

    // Método para exportar alumnos a Excel
    @GetMapping("/excel")
    public void exportarAlumnosExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"alumno_view.xlsx\"");

        Workbook workbook = new XSSFWorkbook();
        List<Alumno> alumnos = alumnoService.listar(); // Obtener la lista de alumnos
        AlumnoXlsView alumnoXlsView = new AlumnoXlsView();
        alumnoXlsView.buildExcelDocument(Map.of("alumnos", alumnos), workbook, null, response);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}