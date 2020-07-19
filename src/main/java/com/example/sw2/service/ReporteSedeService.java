package com.example.sw2.service;

import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.dtoReportes.ReportesArticuloDto;
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

        String[] columns = {"Documento","Doc. Número","Medio de Pago","Producto","Cliente","RUC","DNI","Vendedor","DNI vendedor","Cantidad","Precio Unit.","Precio Total","Fecha de Venta"};

        Sheet sheet= workbook.createSheet("Sede Reporte total " + LocalDate.now().toString());
        setColumnWidths(sheet,reportes.getOrderBy());
        String titulo = "";
        List<ReportesTotalDto> reportesTotales;
        switch (reportes.getType()){
            case 1:
                reportesTotales = ventasRepository.obtenerReporteSedeAnualTotal(reportes.getYear(), idusuario);
                titulo = "Reporte total del año " + reportes.getYear();
                break;
            case 2:
                reportesTotales = ventasRepository.obtenerReporteSedeTrimestralTotal(reportes.getSelected(),reportes.getYear(), idusuario);
                titulo = "Reporte total del año " + reportes.getYear()+" trimestre " + CustomConstants.getTrimestre().get(reportes.getSelected());
                break;
            case 3:
                reportesTotales = ventasRepository.obtenerReporteSedeMensualTotal(reportes.getSelected(),reportes.getYear(), idusuario);
                titulo = "Reporte total del año " + reportes.getYear()+" mes " + CustomConstants.getMeses().get(reportes.getSelected());
                break;
            default:
                reportesTotales = new ArrayList<>();
        }

        fillCellsInSheet(sheet,columns,reportesTotales,workbook,titulo);

    }

    private void llenarReporteProducto(Workbook workbook, Reportes reportes, int idusuario){
        String[] columns = {"Nombre","Linea","Codigo","Suma Ventas","Cantidad Vendidos"};
        Sheet sheet= workbook.createSheet("Reporte producto " + LocalDate.now().toString());
        setColumnWidths(sheet,3);
        String titulo = "";
        List<ReportesArticuloDto> reportesArticulos = new ArrayList<>();
        switch (reportes.getType()){
            case 1:
                reportesArticulos = ventasRepository.obtenerReporteSedeAnualArticuloProducto(reportes.getYear(),idusuario);
                titulo = "Ventas por Artículos del año " + reportes.getYear();
                break;
            case 2:
                reportesArticulos = ventasRepository.obtenerReporteSedeTrimestralArticuloProducto(reportes.getSelected(),reportes.getYear(),idusuario);
                titulo = "Ventas por Artículos del año " + reportes.getYear()+" trimestre " + CustomConstants.getTrimestre().get(reportes.getSelected());
                break;
            case 3:
                reportesArticulos = ventasRepository.obtenerReporteSedeMensualArticuloProducto(reportes.getSelected(),reportes.getYear(),idusuario);
                titulo = "Ventas por Artículos del año " + reportes.getYear()+" mes " + CustomConstants.getMeses().get(reportes.getSelected());
                break;
            default:
                reportesArticulos = new ArrayList<>();
        }
        fillCellsInSheet(sheet,columns,reportesArticulos,workbook,titulo);
    }

    private void llenarReporteComunidad(Workbook workbook, Reportes reportes, Integer idusuario){
        String[] columns = {"Nombre","Código","Cantidad Artesanos","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("reporte comunidad " + LocalDate.now().toString());
        setColumnWidths(sheet,4);
        String titulo = "";
        List<ReportesComunidadDto> reportesComunidad = new ArrayList<>();
        switch (reportes.getType()){
            case 1:
                reportesComunidad = ventasRepository.obtenerReporteSedeAnualComunidad(reportes.getYear(),idusuario);
                titulo = "Ventas por Comunidad del año " + reportes.getYear();
                break;
            case 2:
                reportesComunidad = ventasRepository.obtenerReporteSedeTrimestralComunidad(reportes.getSelected(),reportes.getYear(),idusuario);
                titulo = "Ventas por Comunidad del año " + reportes.getYear()+" trimestre " + CustomConstants.getTrimestre().get(reportes.getSelected());
                break;
            case 3:
                reportesComunidad = ventasRepository.obtenerReporteSedeMensualComunidad(reportes.getSelected(),reportes.getYear(),idusuario);
                titulo = "Ventas por Comunidad del año " + reportes.getYear()+" mes " + CustomConstants.getMeses().get(reportes.getSelected());
                break;
            default:
                reportesComunidad = new ArrayList<>();
        }
        fillCellsInSheet(sheet,columns,reportesComunidad,workbook,titulo);
    }

    private void llenarReporteCliente(Workbook workbook, Reportes reportes,Integer idusuario){
        String[] columns = {"Nombre","DNI o RUC","Producto más comprado","Suma Ventas","Cantidad Productos Vendidos"};
        Sheet sheet= workbook.createSheet("Reporte de clientes " + LocalDate.now().toString());
        setColumnWidths(sheet,reportes.getOrderBy());
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
}
