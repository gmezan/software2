package com.example.sw2.service;

import com.example.sw2.dtoReportes.AnhosDtos;
import com.example.sw2.dtoReportes.ReporteVenta;
import com.example.sw2.dtoReportes.SedesDtos;
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
import java.util.List;

@Service
public class ReportesService2222 implements ServiceReportes2222 {

    @Autowired
    VentasRepositoryXxSedesNClientes ventasRepositoryXxSedesNClientes;

    @Override
    public ByteArrayInputStream SedeOrClienteXxAnual(int mes, int trimestre, int anho, int orderBy, int type) throws Exception {

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
        List<SedesDtos> sedesDtos;

        String Intro;
        String Intro1 = "Ventas de la sede ";
        String Intro2 = "Ventas al cliente ";
        if(orderBy == 2){
            sedesDtos = ventasRepositoryXxSedesNClientes.obtenerSedes();
            Intro = Intro1;
        }else /*if(orderBy == 5)*/{
            sedesDtos = ventasRepositoryXxSedesNClientes.obtenerClientes();
            Intro = Intro2;
        }


        for(SedesDtos sedes : sedesDtos){

            int sede = sedes.getSedeOrCliente();


            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue(Intro + sede + " en el anho " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas;
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

                for(ReporteVenta ven : ventas){

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
