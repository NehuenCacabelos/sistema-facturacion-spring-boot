package com.facturacion.api.view.xlsx;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.facturacion.api.entities.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("listar.xlsx")
public class ClienteXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook wb,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"cliente.xlsx\"");

        @SuppressWarnings("unchecked")
        List<Cliente> clientes = (List<Cliente>) model.get("clientes");

        Sheet sheet = wb.createSheet("Clientes");

        // Estilos
        Font titleFont = wb.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setFont(titleFont);

        Font headFont = wb.createFont();
        headFont.setBold(true);
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setFont(headFont);

        CreationHelper ch = wb.getCreationHelper();
        CellStyle dateStyle = wb.createCellStyle();
        dateStyle.setDataFormat(ch.createDataFormat().getFormat("yyyy-mm-dd"));

        int rowNum = 0;
        Row r0 = sheet.createRow(rowNum++);
        r0.createCell(0).setCellValue("Listado de Clientes");
        r0.getCell(0).setCellStyle(titleStyle);

        Row header = sheet.createRow(rowNum++);
        String[] headers = { "Nombre", "Apellido", "Email", "Fecha de creación" };
        for (int i = 0; i < headers.length; i++) {
            Cell h = header.createCell(i);
            h.setCellValue(headers[i]);
            h.setCellStyle(headStyle);
        }

        if (clientes != null) {
            for (Cliente c : clientes) {
                Row r = sheet.createRow(rowNum++);
                r.createCell(0).setCellValue(c.getNombre());
                r.createCell(1).setCellValue(c.getApellido());
                r.createCell(2).setCellValue(c.getEmail());

                LocalDate fecha = c.getCreateAt();
                Cell df = r.createCell(3);
                if (fecha != null) {
                    df.setCellValue(fecha.toString());
                    df.setCellStyle(dateStyle);
                }
            }
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        sheet.createFreezePane(0, 2);
    }
}
