package com.example.sw2.service;

import com.example.sw2.dtoReportes.ReporteVenta;
import com.example.sw2.entity.Comunidades;
import com.example.sw2.entity.Productos;
import com.example.sw2.repository.ComunidadesRepository;
import com.example.sw2.repository.ProductosRepository;
import com.example.sw2.repository.VentasRepository;
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
public class ReportesService implements ServiceReportes{

    @Autowired
    VentasRepository ventasRepository;

    @Autowired
    ComunidadesRepository comunidadesRepository;

    @Autowired
    ProductosRepository productosRepository;


    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadPorAnho(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadPorAnho");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el anho " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadPorAnho(idComunidad, anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDelPRIMERTrimestre(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelPRIMERTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el PRIMER TRIMESTRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDelPRIMERTrimestre(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDelSEGUNDOTrimestre(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelSEGUNDOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el SEGUNDO TRIMESTRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDelSEGUNDOTrimestre(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDelTERCERTrimestre(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelTERCERTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el TERCER TRIMESTRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDelTERCERTrimestre(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDelCUARTOTrimestre(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el CUARTO TRIMESTRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDelCUARTOTrimestre(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeENERO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE ENERO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeENERO(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeFEBRERO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE FEBRERO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeFEBRERO(idComunidad,anho);

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


    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeMARZO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE MARZO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeMARZO(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeABRIL(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE ABRIL DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeABRIL(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeMAYO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE MAYO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeMAYO(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeJUNIO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE JUNIO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeJUNIO(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeJULIO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE JULIO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeJULIO(idComunidad,anho);

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


    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeAGOSTO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE AGOSTO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeAGOSTO(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeSETIEMBRE(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE SETIEMBRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeSETIEMBRE(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeOCTUBRE(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE OCTUBRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeOCTUBRE(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeNOVIEMBRE(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE NOVIEMBRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeNOVIEMBRE(idComunidad,anho);

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

    @Override
    public ByteArrayInputStream obtenerVentasDeComunidadDeDICIEMBRE(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeComunidadDelCUARTOTrimestre");

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


        List<Comunidades> comunidadesList = comunidadesRepository.findAll();

        for(Comunidades com : comunidadesList){

            String idComunidad = com.getCodigo();

            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas de la comunidad " + idComunidad + " en el MES DE DICIEMBRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeComunidadDeDICIEMBRE(idComunidad,anho);

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


    @Override
    public ByteArrayInputStream obtenerVentasDeProductoPorAnho(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoPorAnho");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en el anho " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoPorAnho(idProducto, anho);


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


    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDelPRIMERTrimestre(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDelPRIMERTrimestre");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en el PRIMER TRIMESTRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDelPRIMERTrimestre(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDelSEGUNDOTrimestre(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDelSEGUNDOTrimestre");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en el SEGUNDO TRIMESTRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDelSEGUNDOTrimestre(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDelTERCERTrimestre(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDelTERCERTrimestre");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en el TERCER TRIMESTRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDelTERCERTrimestre(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDelCUARTOTrimestre(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDelCUARTOTrimestre");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en el CUARTO TRIMESTRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDelCUARTOTrimestre(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeENERO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeENERO");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en ENERO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeENERO(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeFEBRERO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeFEBRERO");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en FEBRERO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeFEBRERO(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeMARZO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeMARZO");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en MARZO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeMARZO(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeABRIL(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeABRIL");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en ABRIL DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeABRIL(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeMAYO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeMAYO");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en MAYO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeMAYO(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeJUNIO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeJUNIO");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en JUNIO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeJUNIO(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeJULIO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeJULIO");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en JULIO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeJULIO(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeAGOSTO(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeAGOSTO");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en AGOSTO DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeAGOSTO(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeSETIEMBRE(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeSETIEMBRE");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en SETIEMBRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeSETIEMBRE(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeOCTUBRE(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeOCTUBRE");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en OCTUBRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeOCTUBRE(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeNOVIEMBRE(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeNOVIEMBRE");

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


        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en NOVIEMBRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeNOVIEMBRE(idProducto, anho);


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

    @Override
    public ByteArrayInputStream obtenerVentasDeProductoDeDICIEMBRE(int anho) throws Exception {

        String[] columns = {"RUC_DNI","Nombre Cliente","Tipo Documento","Número Documento","Lugar Venta","Producto Inventario","Fecha","Vendedor","Cantidad","Precio de Venta","Fecha Modificacion","Fecha Creación"};

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("obtenerVentasDeProductoDeDICIEMBRE");

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

        List<Productos> productosList = productosRepository.findAll();


        for(Productos pro : productosList){

            String idProducto = pro.getCodigonom();



            Row row = sheet.createRow(fila);

            //row = sheet.createRow(fila);
            row.createCell(0).setCellValue("Ventas del producto " + idProducto + " en DICIEMBRE DEL " + anho);
            fila++;
            fila++;

            List<ReporteVenta> ventas = ventasRepository.obtenerVentasDeProductoDeDICIEMBRE(idProducto, anho);


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
