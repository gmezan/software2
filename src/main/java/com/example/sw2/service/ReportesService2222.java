package com.example.sw2.service;

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

        Sheet sheet= workbook.createSheet("reporte de sede " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());


    }
    private void llenarReporteProducto(Workbook workbook, Reportes reportes){

        Sheet sheet= workbook.createSheet("reporte producto " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());

    }
    private void llenarReporteComunidad(Workbook workbook, Reportes reportes){

        Sheet sheet= workbook.createSheet("reporte comunidad " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());


    }
    private void llenarReporteCliente(Workbook workbook, Reportes reportes){

        Sheet sheet= workbook.createSheet("reporte cliente " + LocalDate.now().toString());
        setcolumnwidths(sheet,reportes.getOrderBy());

    }

    private void setcolumnwidths(Sheet sheet, Integer orderBy){


        switch (orderBy){
            case 1:
                break;
            case 2:
                sheet.setColumnWidth(0, 10000);
                sheet.setColumnWidth(1, 5500);
                sheet.setColumnWidth(2, 5500);
                sheet.setColumnWidth(3, 5500);
                sheet.setColumnWidth(4, 5500);
                sheet.setColumnWidth(5, 8000);
                sheet.setColumnWidth(6, 5500);
                sheet.setColumnWidth(7, 5500);
                sheet.setColumnWidth(8, 5500);
                sheet.setColumnWidth(9, 5500);
                sheet.setColumnWidth(10, 8000);
                sheet.setColumnWidth(11, 8000);

                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;

        }

    }


    @Override
    public ByteArrayInputStream SedeOrClienteXxAnual_TrimesterOrMonth(int mes, int trimestre, int anho, int orderBy, int type) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("empleados");

        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 5500);
        sheet.setColumnWidth(2, 5500);
        sheet.setColumnWidth(3, 5500);
        sheet.setColumnWidth(4, 5500);
        sheet.setColumnWidth(5, 8000);
        sheet.setColumnWidth(6, 5500);
        sheet.setColumnWidth(7, 5500);
        sheet.setColumnWidth(8, 5500);
        sheet.setColumnWidth(9, 5500);
        sheet.setColumnWidth(10, 8000);
        sheet.setColumnWidth(11, 8000);

        int fila = 0;
        List<ReportesSedesDto> reportesSedesDtos;

        String Intro;
        String Intro1 = "Ventas de la sede ";
        String Intro2 = "Ventas al cliente ";
        if(orderBy == 2){
            reportesSedesDtos = ventasRepositoryXxSedesNClientes.obtenerSedes();
            Intro = Intro1;
        }else /*if(orderBy == 5)*/{
            reportesSedesDtos = ventasRepositoryXxSedesNClientes.obtenerClientes();
            Intro = Intro2;
        }



        //System.out.println("Esta vacio o no: " + sedesDtos.isEmpty());
        //System.out.println(sedesDtos.toArray());
        //System.out.println("Guack");

        for(ReportesSedesDto sedes : reportesSedesDtos){

            int sede;
            if (orderBy == 2){
                //sede = sedes.getVendedor();
                System.out.println("Guack2");
            }else /*if(orderBy == 5)*/{
                //sede = sedes.getRuc_dni();
                System.out.println("Guack3");
            }


            Row row = sheet.createRow(fila);

            sede = 0;

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue(Intro + sede + " en el anho " + anho);
            fila++;
            fila++;

            List<ReportesTotalDto> ventas;
            if (orderBy == 2 && type == 1 && trimestre == 0 && mes == 0){
                ventas = ventasRepositoryXxSedesNClientes.obtenerReporteAnualSede(anho, sede);
            }else if(orderBy == 5 && type == 1 && trimestre == 0 && mes == 0){
                ventas = ventasRepositoryXxSedesNClientes.obtenerReporteAnualCliente(anho, sede);
            }else if (orderBy == 2 && type == 2 && mes == 0){
                ventas = ventasRepositoryXxSedesNClientes.obtenerReporteTrimestralSede(trimestre, anho, sede);
            }else if(orderBy == 5 && type == 2 && mes == 0) {
                ventas = ventasRepositoryXxSedesNClientes.obtenerReporteTrimestralCliente(trimestre, anho, sede);
            }else if (orderBy == 2 && type == 3 && trimestre == 0){
                ventas = ventasRepositoryXxSedesNClientes.obtenerReporteMensualSede(mes, anho, sede);
            }else /*if(orderBy == 5 && type == 3 && trimestre == 0)*/{
                ventas = ventasRepositoryXxSedesNClientes.obtenerReporteMensualCliente(mes, anho, sede);
            }


            if(ventas.isEmpty()){

                row = sheet.createRow(fila);

                row.createCell(0).setCellValue("Sin ventas :(");
                fila=fila+4;

            }else{
                row = sheet.createRow(fila);

                for(int i=0; i<columns.length; i++){

                    Cell cell = row.createCell(i);
                    cell.setCellValue(columns[i]);
                }

                fila++;

                for(ReportesTotalDto ven : ventas){

                    row = sheet.createRow(fila);

                    row.createCell(0).setCellValue(ven.getRuc_dni());
                    row.createCell(1).setCellValue(ven.getNombrecliente());
                    row.createCell(2).setCellValue(ven.getTipodocumento());
                    row.createCell(3).setCellValue(ven.getNumerodocumento());
                    row.createCell(4).setCellValue(ven.getLugarventa());
                    row.createCell(5).setCellValue(ven.getProductoinventario());
                    row.createCell(6).setCellValue(ven.getFecha());
                    row.createCell(7).setCellValue(ven.getVendedor());
                    row.createCell(8).setCellValue(ven.getCantidad());
                    row.createCell(9).setCellValue(ven.getPrecio_venta());
                    row.createCell(10).setCellValue(ven.getFecha_modificacion());
                    row.createCell(11).setCellValue(ven.getFecha_creacion());

                    fila++;

                }

                fila=fila+4;
            }

        }

        workbook.write(stream);
        workbook.close();

        return new ByteArrayInputStream(stream.toByteArray());
    }

}
