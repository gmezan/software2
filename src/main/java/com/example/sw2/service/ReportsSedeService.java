package com.example.sw2.service;

import com.example.sw2.entity.Reportes;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class ReportsSedeService implements ServiceReportsSede{

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
