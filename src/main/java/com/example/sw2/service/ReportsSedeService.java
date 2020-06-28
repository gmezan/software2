package com.example.sw2.service;

import com.example.sw2.dtoReportes.ReportesTotalDto;
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


    @Override
    public ByteArrayInputStream generarReporte(Reportes reportes, int idusuario) throws Exception{

        Workbook workbook = new HSSFWorkbook();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        switch (reportes.getOrderBy()){


            case 1:
                //nos referimos al total
                llenarReporteTotal(workbook, reportes);
                String[] columns = {"Documento","Numero","Cliente","RUC_DNI","Vendedor","DNI vendedor"};

                Sheet sheet= workbook.createSheet("reporte total " + LocalDate.now().toString());
                setcolumnwidths(sheet,reportes.getOrderBy());

                List<ReportesTotalDto> reportesTotales;
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

    private void llenarReporteComunidad(Workbook workbook, Reportes reportes){}

    private void llenarReporteCliente(Workbook workbook, Reportes reportes){}

}
