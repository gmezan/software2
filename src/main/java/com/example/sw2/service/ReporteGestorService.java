package com.example.sw2.service;

import com.example.sw2.dtoReportes.ReportesArticuloDto;
import com.example.sw2.dtoReportes.ReportesClienteDto;
import com.example.sw2.dtoReportes.ReportesComunidadDto;
import com.example.sw2.dtoReportes.ReportesTotalDto;
import com.example.sw2.dtoReportes.ReportesSedesDto;
import com.example.sw2.entity.Reportes;
import com.example.sw2.repository.VentasRepository;
import com.example.sw2.utils.ReportesUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReporteGestorService extends ReportesUtils implements IReporteGestorService {

    @Autowired
    VentasRepository ventasRepository;

    @Override
    public ByteArrayInputStream generarReporte(Reportes reportes) throws Exception{

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        switch (reportes.getOrderBy()){
            case 1: //nos referimos al total
                llenarReporteTotal(workbook, reportes);

                break;
            case 2: //nos referimos a la sede
                llenarReporteSede(workbook, reportes);

                break;
            case 3: //nos referimos al articulo(producto)
                llenarReporteProducto(workbook, reportes);

                break;
            case 4: //nos referimos a la comunidad
                llenarReporteComunidad( workbook, reportes);
                break;
            case 5: //nos referimos al cliente
                llenarReporteCliente(workbook, reportes);

                break;

        }

        workbook.write(stream);
        workbook.close();

        return new ByteArrayInputStream(stream.toByteArray());

    }

    private void llenarReporteTotal(Workbook workbook, Reportes reportes){

        String[] columns = {"Documento","Numero","Producto","Cliente","RUC","DNI","Vendedor","DNI vendedor","Cantidad","Precio Unit.","Precio Total","Fecha de Venta"};

        Sheet sheet= workbook.createSheet("Reporte total " + LocalDate.now().toString());
        setColumnWidths(sheet,reportes.getOrderBy());

        List<ReportesTotalDto> reportesTotales = new ArrayList<>();
        switch (reportes.getType()){
            case 1:
                reportesTotales = ventasRepository.obtenerReporteAnualTotal(reportes.getYear());
                break;
            case 2:
                reportesTotales = ventasRepository.obtenerReporteTrimestralTotal(reportes.getSelected(),reportes.getYear());
                break;
            case 3:
                reportesTotales = ventasRepository.obtenerReporteMensualTotal(reportes.getSelected(),reportes.getYear());
                break;
        }
        fillCellsInSheet(sheet,columns,reportesTotales,workbook);
    }

    private void llenarReporteSede(Workbook workbook, Reportes reportes){
        String[] columns = {"Nombre","DNI","Correo","Telefono","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("Reporte de sede " + LocalDate.now().toString());
        setColumnWidths(sheet,reportes.getOrderBy());
        List<ReportesSedesDto> reportesSedes = new ArrayList<>();
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
        }
        fillCellsInSheet(sheet,columns,reportesSedes,workbook);
    }
    private void llenarReporteProducto(Workbook workbook, Reportes reportes){
        String[] columns = {"Nombre","Linea","Codigo","Suma Ventas","Cantidad Vendidos"};
        Sheet sheet= workbook.createSheet("Reporte producto " + LocalDate.now().toString());
        setColumnWidths(sheet,reportes.getOrderBy());
        List<ReportesArticuloDto> reportesArticulos = new ArrayList<>();
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
        }
        fillCellsInSheet(sheet,columns,reportesArticulos,workbook);
    }
    private void llenarReporteComunidad(Workbook workbook, Reportes reportes){
        String[] columns = {"Nombre","Código","Cantidad Artesanos","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("Reporte comunidad " + LocalDate.now().toString());
        setColumnWidths(sheet,reportes.getOrderBy());
        List<ReportesComunidadDto> reportesComunidad = new ArrayList<>();
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
        }
        fillCellsInSheet(sheet,columns,reportesComunidad,workbook);
    }

    private void llenarReporteCliente(Workbook workbook, Reportes reportes){
        String[] columns = {"Nombre","DNI o RUC","Producto más comprado","Suma Ventas","Cantidad Vendida"};
        Sheet sheet= workbook.createSheet("Reporte de clientes " + LocalDate.now().toString());
        setColumnWidths(sheet,reportes.getOrderBy());
        List<ReportesClienteDto> reportesClientes = new ArrayList<>();
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
        }

        fillCellsInSheet(sheet,columns,reportesClientes,workbook);
    }

}
