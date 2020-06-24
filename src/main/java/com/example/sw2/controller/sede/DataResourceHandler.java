package com.example.sw2.controller.sede;

import com.example.sw2.dto.DatosProductoVentaDto;
import com.example.sw2.repository.VentasRepository;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@Component
public class DataResourceHandler {

    @Autowired
    VentasRepository ventasRepository;


    public void ReportLastMonth(int a) {

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet firstSheet = workbook.createSheet("FIRST SHEET");

        int n = 0;
        List<DatosProductoVentaDto> listProduct
        if (a == 1){
            listProduct = ventasRepository.obtenerDatosPorProductoUltimoMes();
        }else if (a == 2){
            listProduct = ventasRepository.obtenerDatosPorProductoUltimoTrimestre();
        }else if (a == 3){

        }

        for (DatosProductoVentaDto product : listProduct){
            HSSFRow rowA = firstSheet.createRow(n++);
            int i = 0;
            rowA.createCell(i++).setCellValue(product.getCodigoproducto());
            rowA.createCell(i++).setCellValue(product.getNombreproducto());
            rowA.createCell(i++).setCellValue(product.getComunidadproducto());
            rowA.createCell(i++).setCellValue(product.getColorproducto());
            rowA.createCell(i++).setCellValue(product.getTamanhoproducto());
            rowA.createCell(i++).setCellValue(product.getFechaventa());
            rowA.createCell(i++).setCellValue(product.getCantidadventa());
        }

        try (FileOutputStream fos = new FileOutputStream(new File("CreateExcelDemo.xls"))) {
            workbook.write(fos);
            System.out.println("Excel creado exitosamente");
        } catch (IOException e) {
            System.out.println("Excepcion generada");
            e.printStackTrace();
        }
    }
}
