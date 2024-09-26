package com.tecsup.lab06.views;

import java.util.List;
import java.util.Map;

import com.tecsup.lab06.domain.entities.Alumno;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("alumno/ver.xlsx")
public class AlumnoXlsView extends AbstractXlsxView {

    @Override
    public void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception { // Cambiado a public
        response.setHeader("Content-Disposition", "attachment; filename=\"alumno_view.xlsx\"");

        List<Alumno> alumnos = (List<Alumno>) model.get("alumnos");
        Sheet sheet = workbook.createSheet("Lista de Alumnos");

        // Estilo para encabezados
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Estilo para cuerpo
        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderTop(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);

        // Crear encabezados de la tabla
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Apellido");
        header.createCell(3).setCellValue("Edad");
        header.createCell(4).setCellValue("Promedio");

        // Aplicar estilo a los encabezados
        for (int i = 0; i < 5; i++) {
            header.getCell(i).setCellStyle(headerStyle);
        }

        // Llenar la tabla con datos de alumnos
        int rownum = 1; // Comenzar desde la segunda fila
        for (Alumno alumno : alumnos) {
            Row fila = sheet.createRow(rownum++);
            Cell cell = fila.createCell(0);
            cell.setCellValue(alumno.getId());
            cell.setCellStyle(bodyStyle);

            cell = fila.createCell(1);
            cell.setCellValue(alumno.getNombre());
            cell.setCellStyle(bodyStyle);

            cell = fila.createCell(2);
            cell.setCellValue(alumno.getApellido());
            cell.setCellStyle(bodyStyle);

            cell = fila.createCell(3);
            cell.setCellValue(alumno.getEdad());
            cell.setCellStyle(bodyStyle);

            cell = fila.createCell(4);
            cell.setCellValue(alumno.getPromedio());
            cell.setCellStyle(bodyStyle);
        }
    }
}