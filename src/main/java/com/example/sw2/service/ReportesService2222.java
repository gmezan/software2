package com.example.sw2.service;

import com.example.sw2.dtoReportes.ReportesArticuloDto;
import com.example.sw2.dtoReportes.ReportesClienteDto;
import com.example.sw2.dtoReportes.ReportesTotalDto;
import com.example.sw2.dtoReportes.ReportesSedesDto;
import com.example.sw2.entity.Reportes;
import com.example.sw2.repository.VentasRepository;
import com.example.sw2.repository.VentasRepositoryXxSedesNClientes;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
public class ReportesService2222 implements ServiceReportes2222 {

    @Autowired
    VentasRepository ventasRepository;

    @Autowired
    VentasRepositoryXxSedesNClientes ventasRepositoryXxSedesNClientes;

    @Override
    public ByteArrayInputStream generarReporte(Reportes reportes) throws Exception{

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        switch (reportes.getOrderBy()){


            case 1:
                //nos referimos al total
                llenarReporteTotal(workbook, reportes);


                break;
            case 2:
                //nos referimos a la sede

                llenarReporteSede(workbook, reportes);

                break;
            case 3:
                //nos referimos al articulo(producto)

                llenarReporteProducto(workbook, reportes);



                break;
            case 4:
                //nos referimos a la comunidad

                llenarReporteComunidad( workbook, reportes);
                break;
            case 5:
                //nos referimos al cliente

                llenarReporteCliente(workbook, reportes);

                break;

        }

        workbook.write(stream);
        workbook.close();

        return new ByteArrayInputStream(stream.toByteArray());

    }

    private void llenarReporteTotal(Workbook workbook, Reportes reportes){

        Sheet sheet= workbook.createSheet("reporte total " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());

    }

    private void llenarReporteSede(Workbook workbook, Reportes reportes){
        String[] columns = {"Nombre","DNI","Correo","Telefono","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("reporte de sede " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());
        List<ReportesSedesDto> reportesSedes;
        switch (reportes.getType()){
            case 1:
                reportesSedes = ventasRepository.obtenerReporteAnualSede(reportes.getYear());
                break;
            case 2:
                reportesSedes = ventasRepository.obtenerReporteTrimestralSede(reportes.getSelected(),reportes.getYear());
                break;
            case 3:
                reportesSedes = ventasRepository.obtenerReporteMensualSede(reportes.getSelected(),reportes.getYear());
                break;
            default:
                reportesSedes = new ArrayList<>();
        }

        if(reportesSedes.isEmpty()){
            sheet.createRow(1).createCell(0).setCellValue("Sin ventas :(");

        }else{
            Row row = sheet.createRow(1);
            for(int i=1; i<columns.length + 1; i++){
                row.createCell(i).setCellValue(columns[i]);
            }
            int fila = 1;
            for(ReportesSedesDto reportesSedesDto : reportesSedes){
                row = sheet.createRow(++fila);
                row.createCell(1).setCellValue(reportesSedesDto.getNombre());
                row.createCell(2).setCellValue(reportesSedesDto.getDni());
                row.createCell(3).setCellValue(reportesSedesDto.getCorreo());
                row.createCell(4).setCellValue(reportesSedesDto.getTelefono());
                row.createCell(5).setCellValue(reportesSedesDto.getSumaventas());
                row.createCell(6).setCellValue(reportesSedesDto.getCantidadvendidos());
            }
        }
    }
    private void llenarReporteProducto(Workbook workbook, Reportes reportes){
        String[] columns = {"Nombre","Linea","Codigo","Suma Ventas","Cantidad Vendidos"};
        Sheet sheet= workbook.createSheet("reporte producto " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());
        List<ReportesArticuloDto> reportesArticulos;
        switch (reportes.getType()){
            case 1:
                reportesArticulos = ventasRepository.obtenerReporteAnualArticuloProducto(reportes.getYear());
                break;
            case 2:
                reportesArticulos = ventasRepository.obtenerReporteTrimestralArticuloProducto(reportes.getSelected(),reportes.getYear());
                break;
            case 3:
                reportesArticulos = ventasRepository.obtenerReporteMensualArticuloProducto(reportes.getSelected(),reportes.getYear());
                break;
            default:
                reportesArticulos = new ArrayList<>();
        }
        if(reportesArticulos.isEmpty()){
            sheet.createRow(1).createCell(0).setCellValue("Sin ventas :(");
        }else{
            Row row = sheet.createRow(1);
            for(int i=1; i<columns.length + 1; i++){
                row.createCell(i).setCellValue(columns[i]);
            }
            int fila = 1;
            for(ReportesArticuloDto reportesArticuloDto : reportesArticulos){
                row = sheet.createRow(++fila);
                row.createCell(1).setCellValue(reportesArticuloDto.getNombre());
                row.createCell(2).setCellValue(reportesArticuloDto.getLinea());
                row.createCell(3).setCellValue(reportesArticuloDto.getCodigonom());
                row.createCell(4).setCellValue(reportesArticuloDto.getSumaventas());
                row.createCell(5).setCellValue(reportesArticuloDto.getCantidadvendidos());
            }
        }
    }
    private void llenarReporteComunidad(Workbook workbook, Reportes reportes){

        Sheet sheet= workbook.createSheet("reporte comunidad " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());


    }
    private void llenarReporteCliente(Workbook workbook, Reportes reportes){
        String[] columns = {"Nombre","DNI o RUC","Producto más comprado","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("reporte de clientes " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());
        List<ReportesClienteDto> reportesClientes;
        switch (reportes.getType()){
            case 1:
                reportesClientes = ventasRepository.obtenerReporteAnualCliente(reportes.getYear());
                break;
            case 2:
                reportesClientes = ventasRepository.obtenerReporteTrimestralCliente(reportes.getSelected(),reportes.getYear());
                break;
            case 3:
                reportesClientes = ventasRepository.obtenerReporteMensualCliente(reportes.getSelected(),reportes.getYear());
                break;
            default:
                reportesClientes = new ArrayList<>();
        }

        if(reportesClientes.isEmpty()){
            sheet.createRow(1).createCell(0).setCellValue("Sin ventas :(");

        }else{
            Row row = sheet.createRow(1);
            for(int i=1; i<columns.length + 1; i++){
                row.createCell(i).setCellValue(columns[i]);
            }
            int fila = 1;
            for(ReportesClienteDto reportesClienteDto : reportesClientes){
                row = sheet.createRow(++fila);
                row.createCell(1).setCellValue(reportesClienteDto.getNombre());
                row.createCell(2).setCellValue(reportesClienteDto.getRuc_dni());
                row.createCell(3).setCellValue(reportesClienteDto.getProducto());
                row.createCell(4).setCellValue(reportesClienteDto.getSumaventas());
                row.createCell(5).setCellValue(reportesClienteDto.getCantidadvendidos());
            }
        }


    }

    private void setcolumnwidths(Sheet sheet, Integer orderBy){
        switch (orderBy){
            case 1:
                break;
            case 2:
                sheet.setColumnWidth(1, 5500);
                sheet.setColumnWidth(2, 5500);
                sheet.setColumnWidth(3, 5500);
                sheet.setColumnWidth(4, 5500);
                sheet.setColumnWidth(5, 5500);
                sheet.setColumnWidth(6, 5500);
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
            case 5:
                sheet.setColumnWidth(1, 5500);
                sheet.setColumnWidth(2, 5500);
                sheet.setColumnWidth(3, 5500);
                sheet.setColumnWidth(4, 5500);
                sheet.setColumnWidth(5, 5500);
                break;

        }

    }


}
