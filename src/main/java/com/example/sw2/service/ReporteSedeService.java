package com.example.sw2.service;

import com.example.sw2.dtoReportes.ReportesClienteDto;
import com.example.sw2.dtoReportes.ReportesTotalDto;
import com.example.sw2.dtoReportes.ReportesComunidadDto;
import com.example.sw2.entity.Reportes;
import com.example.sw2.repository.VentasRepository;
import com.example.sw2.utils.ReportesUtils;
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
public class ReporteSedeService extends ReportesUtils implements IReporteSedeService {


    @Autowired
    VentasRepository ventasRepository;

    @Override
    public ByteArrayInputStream generarReporte(Reportes reportes, int idusuario) throws Exception{

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        switch (reportes.getOrderBy()){


            case 1:
                //nos referimos al total

                llenarReporteTotal(workbook, reportes, idusuario);
                break;
            case 2:
                //nos referimos al articulo(producto)

                llenarReporteProducto(workbook, reportes, idusuario);
                break;
            case 3:
                //nos referimos a la comunidad

                llenarReporteComunidad( workbook, reportes,idusuario);
                break;
            case 4:
                //nos referimos al cliente

                llenarReporteCliente(workbook, reportes,idusuario);

                break;

        }

        workbook.write(stream);
        workbook.close();

        return new ByteArrayInputStream(stream.toByteArray());
    }

    private void llenarReporteTotal(Workbook workbook, Reportes reportes, int idusuario){

        String[] columns = {"Documento","Numero","Cliente","RUC_DNI","Vendedor","DNI vendedor"};

        Sheet sheet= workbook.createSheet("reporte total " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());

        List<ReportesTotalDto> reportesTotales;
        switch (reportes.getType()){
            case 1:
                reportesTotales = ventasRepository.obtenerReporteAnualTotal(reportes.getYear(), idusuario);
                break;
            case 2:
                reportesTotales = ventasRepository.obtenerReporteTrimestralTotal(reportes.getSelected(),reportes.getYear(), idusuario);
                break;
            case 3:
                reportesTotales = ventasRepository.obtenerReporteMensualTotal(reportes.getSelected(),reportes.getYear(), idusuario);
                break;
            default:
                reportesTotales = new ArrayList<>();
        }

        if(reportesTotales.isEmpty()){
            sheet.createRow(1).createCell(0).setCellValue("Sin ventas :(");

        }else{
            Row row = sheet.createRow(1);
            for(int i=1; i<columns.length + 1; i++){
                row.createCell(i).setCellValue(columns[i]);
            }
            int fila = 1;
            for(ReportesTotalDto reportesTotalDto : reportesTotales){
                row = sheet.createRow(++fila);
                row.createCell(1).setCellValue(reportesTotalDto.getTipodocumento());
                row.createCell(2).setCellValue(reportesTotalDto.getNumerodocumento());
                row.createCell(3).setCellValue(reportesTotalDto.getNombrecliente());
                row.createCell(4).setCellValue(reportesTotalDto.getRuc_dni());
                row.createCell(5).setCellValue(reportesTotalDto.getVendedor());
                row.createCell(6).setCellValue(reportesTotalDto.getDnivendedor());
            }
        }

    }

    private void llenarReporteProducto(Workbook workbook, Reportes reportes, int idusuario){}

    private void llenarReporteComunidad(Workbook workbook, Reportes reportes, Integer idusuario){
        String[] columns = {"Nombre","Código","Cantidad Artesanos","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("reporte comunidad " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());
        List<ReportesComunidadDto> reportesComunidad;
        switch (reportes.getType()){
            case 1:
                reportesComunidad = ventasRepository.obtenerReporteSedeAnualComunidad(reportes.getYear(),idusuario);
                break;
            case 2:
                reportesComunidad = ventasRepository.obtenerReporteSedeTrimestralComunidad(reportes.getSelected(),reportes.getYear(),idusuario);
                break;
            case 3:
                reportesComunidad = ventasRepository.obtenerReporteSedeMensualComunidad(reportes.getSelected(),reportes.getYear(),idusuario);
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

    private void llenarReporteCliente(Workbook workbook, Reportes reportes,Integer idusuario){
        String[] columns = {"Nombre","DNI o RUC","Producto más comprado","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("reporte de clientes " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());
        List<ReportesClienteDto> reportesClientes;
        switch (reportes.getType()){
            case 1:
                reportesClientes = ventasRepository.obtenerReporteSedeAnualCliente(reportes.getYear(),idusuario);
                break;
            case 2:
                reportesClientes = ventasRepository.obtenerReporteSedeTrimestralCliente(reportes.getSelected(),reportes.getYear(),idusuario);
                break;
            case 3:
                reportesClientes = ventasRepository.obtenerReporteSedeMensualCliente(reportes.getSelected(),reportes.getYear(),idusuario);
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
                //Total
                break;
            case 2:
                //Articulo
                break;
            case 3:
                //Comunidad
                sheet.setColumnWidth(1, 6000);
                sheet.setColumnWidth(2, 6000);
                sheet.setColumnWidth(3, 6000);
                sheet.setColumnWidth(4, 6000);
                sheet.setColumnWidth(5, 6000);
                break;
            case 4:
                //Cliente
                sheet.setColumnWidth(1, 5500);
                sheet.setColumnWidth(2, 5500);
                sheet.setColumnWidth(3, 5500);
                sheet.setColumnWidth(4, 5500);
                sheet.setColumnWidth(5, 5500);
                break;

        }

    }



}
