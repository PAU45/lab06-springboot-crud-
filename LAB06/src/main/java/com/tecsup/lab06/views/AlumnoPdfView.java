package com.tecsup.lab06.views;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.tecsup.lab06.domain.entities.Alumno;

@Component("alumnoPdfView") // Nombre del componente para la vista PDF
public class AlumnoPdfView extends AbstractPdfView {


    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Alumno> alumnos = (List<Alumno>) model.get("alumnos");
        String titulo = (String) model.get("a"); // Obtener el título del modelo

        // Establecer márgenes
        document.setMargins(30, 30, 30, 30); // Izquierda, derecha, arriba, abajo
        document.open(); // Abrir el documento

        // Agregar título al PDF
        Paragraph paragraph = new Paragraph(titulo);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph.setSpacingAfter(20);
        document.add(paragraph);

        // Crear una tabla con estilo
        PdfPTable tabla = new PdfPTable(5); // Número de columnas
        tabla.setWidthPercentage(100); // Ancho de la tabla
        tabla.setSpacingBefore(20); // Espacio antes de la tabla

        // Estilos de encabezado
        PdfPCell cell = new PdfPCell(new Phrase("Lista de Alumnos"));
        cell.setColspan(5); // Hacer que el encabezado ocupe todas las columnas
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(10);
        tabla.addCell(cell);

        // Encabezados de la tabla
        String[] headers = {"ID", "Nombre", "Apellido", "Edad", "Promedio"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header));
            headerCell.setBackgroundColor(new Color(200, 200, 200));
            headerCell.setPadding(8);
            tabla.addCell(headerCell);
        }

        // Rellenar la tabla con datos de alumnos
        for (Alumno alumno : alumnos) {
            tabla.addCell(String.valueOf(alumno.getId()));
            tabla.addCell(alumno.getNombre());
            tabla.addCell(alumno.getApellido());
            tabla.addCell(String.valueOf(alumno.getEdad()));
            tabla.addCell(String.valueOf(alumno.getPromedio()));
        }

        document.add(tabla); // Agregar la tabla al documento
        document.close(); // Cerrar el documento
    }
}