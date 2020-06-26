package com.example.sw2.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class VentaAnualXxProducto {


    @Override
    public ByteArrayInputStream exportAllData() throws Exception {

        String[] columns = {"RUC_DNI","nombreCliente","lugarVenta","ProductoInventario"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("empleados");

        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4500);
        sheet.setColumnWidth(2, 5500);
        sheet.setColumnWidth(3, 7000);

        Row row = sheet.createRow(0);

        for(int i=0; i<columns.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        List<Anual2020Dto> personas = ventasRepository.anual2020();

        int initRow = 1;
        for(Anual2020Dto emp : personas){

            row = sheet.createRow(initRow);

            row.createCell(0).setCellValue(emp.getRuc_dni());
            row.createCell(1).setCellValue(emp.getNombrecliente());
            row.createCell(2).setCellValue(emp.getLugarventa());
            row.createCell(3).setCellValue(emp.getProductoinventario());

            initRow++;
        }

        workbook.write(stream );
        workbook.close();

        return new ByteArrayInputStream(stream.toByteArray());

    }

}
