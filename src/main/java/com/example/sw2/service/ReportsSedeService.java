package com.example.sw2.service;

import com.example.sw2.dtoReportes.ReportesComunidadDto;
import com.example.sw2.entity.Reportes;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.VentasRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportsSedeService implements ServiceReportsSede{

    @Autowired
    VentasRepository ventasRepository;

    @Override
    public ByteArrayInputStream generarReporte(Reportes reportes, int idusuario) throws Exception{

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        switch (reportes.getOrderBy()){


            case 1:
                //nos referimos al total
                llenarReporteTotal(workbook, reportes);

                break;
            case 2:
                //nos referimos al articulo(producto)

                llenarReporteProducto(workbook, reportes);
                break;
            case 3:
                //nos referimos a la comunidad

                llenarReporteComunidad( workbook, reportes);
                break;
            case 4:
                //nos referimos al cliente

                llenarReporteCliente(workbook, reportes);

                break;

        }

        workbook.write(stream);
        workbook.close();

        return new ByteArrayInputStream(stream.toByteArray());
    }

    private void llenarReporteTotal(Workbook workbook, Reportes reportes){}

    private void llenarReporteProducto(Workbook workbook, Reportes reportes){}

    private void llenarReporteComunidad(Workbook workbook, Reportes reportes){
        String[] columns = {"Nombre","Código","Cantidad Artesanos","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("reporte comunidad " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());
        List<ReportesComunidadDto> reportesComunidad;
        switch (reportes.getType()){
            case 1:
                reportesComunidad = ventasRepository.obtenerReporteAnualComunidad(reportes.getYear());
                break;
            case 2:
                reportesComunidad = ventasRepository.obtenerReporteTrimestralComunidad(reportes.getSelected(),reportes.getYear());
                break;
            case 3:
                reportesComunidad = ventasRepository.obtenerReporteMensualComunidad(reportes.getSelected(),reportes.getYear());
                break;
            default:
                reportesComunidad = new ArrayList<>();
        }
        if(reportesComunidad.isEmpty()){
            sheet.createRow(1).createCell(0).setCellValue("Sin ventas :(");

        }else{
            Row row = sheet.createRow(1);
            for(int i=1; i<columns.length + 1; i++){
                row.createCell(i).setCellValue(columns[i]);
            }
            int fila = 1;
            for(ReportesComunidadDto reportesComunidadDto : reportesComunidad){
                row = sheet.createRow(++fila);
                row.createCell(1).setCellValue(reportesComunidadDto.getNombre());
                row.createCell(2).setCellValue(reportesComunidadDto.getCodigo());
                row.createCell(3).setCellValue(reportesComunidadDto.getCantidadartesanos());
                row.createCell(4).setCellValue(reportesComunidadDto.getSumaventas());
                row.createCell(5).setCellValue(reportesComunidadDto.getCantidadvendidos());
            }
        }
    }

    private void llenarReporteCliente(Workbook workbook, Reportes reportes){}


    private void setcolumnwidths(Sheet sheet, Integer orderBy){
        switch (orderBy){
            case 1:
                break;
            case 2:
                break;
            case 3:
                sheet.setColumnWidth(1, 5500);
                sheet.setColumnWidth(2, 5500);
                sheet.setColumnWidth(3, 5500);
                sheet.setColumnWidth(4, 5500);
                sheet.setColumnWidth(5, 5500);
                break;
            case 4:
                break;

        }

    }



}
