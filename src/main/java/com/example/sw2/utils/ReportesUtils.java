package com.example.sw2.utils;

import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.dtoReportes.*;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.util.List;
import static org.apache.poi.ss.util.CellUtil.createCell;

public abstract class ReportesUtils {


    protected final String MENSAJE_NO_VENTA = "No se encontró información de ventas para este reporte";
    protected final Integer BEGINNING_ROW = 3;
    protected final Integer BEGINNING_COLUMN = 1;
    private final short HEADER_FONT = 12;
    private final short CONTENT_FONT = 10;


    //Cliente
    protected void fillCellsInSheet(Sheet sheet, String[] columns, Object data, Workbook workbook,String titulo){

        List<Object> list = (List<Object>)data;
        if(list.isEmpty()) {
            sheet.createRow(BEGINNING_ROW).createCell(BEGINNING_COLUMN).setCellValue(MENSAJE_NO_VENTA);
        }
        else {
            CellStyle style1 = createContentCellStyle1(workbook);
            CellStyle style2 = createContentCellStyle2(workbook);
            CellStyle headerStyle = createHeaderCellStyle(workbook);
            Row row = sheet.createRow(BEGINNING_ROW);
            Row temp = sheet.createRow(1);
            createCell(temp,1,titulo);

            for(int i=1,col=BEGINNING_COLUMN; i<columns.length + 1; i++)
                createCell(row,col++,columns[i-1],headerStyle);

            int fila = BEGINNING_ROW;
            CellStyle style;
            int i = BEGINNING_COLUMN;
            if (list.get(0) instanceof ReportesClienteDto){
                //Cliente
                for(ReportesClienteDto dataRow : (List<ReportesClienteDto>)(Object)list){
                    i = BEGINNING_COLUMN;
                    row = sheet.createRow(++fila);
                    style = ((fila%2)==0)?style1:style2;
                    createCell(row,i++,dataRow.getNombre(),style);
                    createCell(row,i++, "",style).setCellValue(dataRow.getRuc_dni());
                    createCell(row,i++,dataRow.getProducto(),style);
                    createCell(row,i++,"",style).setCellValue(dataRow.getSumaventas());
                    createCell(row,i,"",style).setCellValue(dataRow.getCantidadvendidos());
                }
            }else
            if (list.get(0) instanceof ReportesComunidadDto){
                //Comunidad
                for(ReportesComunidadDto dataRow : (List<ReportesComunidadDto>)(Object)list){
                    i = BEGINNING_COLUMN;
                    row = sheet.createRow(++fila);
                    style = ((fila%2)==0)?style1:style2;
                    createCell(row, i++,dataRow.getNombre(),style);
                    createCell(row,i++,dataRow.getCodigo(),style);
                    createCell(row,i++,"",style).setCellValue(dataRow.getCantidadartesanos());
                    createCell(row,i++,"",style).setCellValue(dataRow.getSumaventas());
                    createCell(row,i,"",style).setCellValue(dataRow.getCantidadvendidos());
                }
            }else
            if (list.get(0) instanceof ReportesArticuloDto){
                //Articulo
                for(ReportesArticuloDto dataRow : (List<ReportesArticuloDto>)(Object)list){
                    i = BEGINNING_COLUMN;
                    row = sheet.createRow(++fila);
                    style = ((fila%2)==0)?style1:style2;
                    createCell(row,i++,dataRow.getNombre(),style);
                    createCell(row,i++,dataRow.getLinea(),style);
                    createCell(row,i++,dataRow.getCodigonom(),style);
                    createCell(row,i++,"",style).setCellValue(dataRow.getSumaventas());
                    createCell(row,i,"",style).setCellValue(dataRow.getCantidadvendidos());
                }
            }else
            if (list.get(0) instanceof ReportesSedesDto){
                //Sede
                for(ReportesSedesDto dataRow : (List<ReportesSedesDto>)(Object)list){
                    i = BEGINNING_COLUMN;
                    row = sheet.createRow(++fila);
                    style = ((fila%2)==0)?style1:style2;
                    createCell(row,i++,dataRow.getNombre(),style);
                    createCell(row,i++,"",style).setCellValue(dataRow.getDni());
                    createCell(row,i++,dataRow.getCorreo(),style);
                    createCell(row,i++,"",style).setCellValue(dataRow.getTelefono());
                    createCell(row,i++,"",style).setCellValue(dataRow.getSumaventas());
                    createCell(row,i,"",style).setCellValue(dataRow.getCantidadvendidos());
                }
            } else
            if (list.get(0) instanceof ReportesTotalDto){
                //Total
                for(ReportesTotalDto dataRow : (List<ReportesTotalDto>)(Object)list){
                    i = BEGINNING_COLUMN;
                    row = sheet.createRow(++fila);
                    style = ((fila%2)==0)?style1:style2;
                    createCell(row,i++,CustomConstants.getTiposDocumento().get(dataRow.getTipodocumento()),style);
                    createCell(row,i++,dataRow.getNumerodocumento(),style);
                    createCell(row,i++,CustomConstants.getMediosDePago().get(dataRow.getMediopago()),style);
                    createCell(row,i++,dataRow.getProductoinventario(),style);
                    createCell(row,i++,dataRow.getNombrecliente(),style);
                    if(dataRow.getRuc_dni().length() == 11){
                        createCell(row,i++,dataRow.getRuc_dni(),style);
                        createCell(row,i++,"-",style);
                    }else{
                        createCell(row,i++,"-",style);
                        createCell(row,i++,dataRow.getRuc_dni(),style);
                    }
                    createCell(row,i++,dataRow.getVendedor(),style);
                    createCell(row,i++, String.valueOf(dataRow.getDnivendedor()),style);
                    createCell(row,i++,"",style).setCellValue(dataRow.getCantidad());
                    createCell(row,i++,"",style).setCellValue(dataRow.getPrecio_venta()/dataRow.getCantidad());
                    createCell(row,i++,"",style).setCellValue(dataRow.getPrecio_venta());
                    createCell(row,i++,dataRow.getFecha(),style);
                }
            }
        }
    }


    protected void setColumnWidths(Sheet sheet, Integer orderBy){
        int i = BEGINNING_COLUMN;
        switch (orderBy){
            case 1: //Total
                sheet.setColumnWidth(i++, 3800);
                sheet.setColumnWidth(i++, 4000);
                sheet.setColumnWidth(i++, 5300);
                sheet.setColumnWidth(i++, 6500);
                sheet.setColumnWidth(i++, 4000);
                sheet.setColumnWidth(i++, 3500);
                sheet.setColumnWidth(i++, 2500);
                sheet.setColumnWidth(i++, 4000);
                sheet.setColumnWidth(i++, 4200);
                sheet.setColumnWidth(i++, 3300);
                sheet.setColumnWidth(i++, 4200);
                sheet.setColumnWidth(i++, 4200);
                sheet.setColumnWidth(i, 5000);
                break;
            case 2: // Sede
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i, 6000);
                break;
            case 3: // Producto
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i, 6500);
                break;
            case 4: //Comunidad
                sheet.setColumnWidth(i++, 6001);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i++, 6000);
                sheet.setColumnWidth(i, 11000);
                break;
            case 5: //Cliente
                sheet.setColumnWidth(i++, 3100);
                sheet.setColumnWidth(i++, 4000);
                sheet.setColumnWidth(i++, 7000);
                sheet.setColumnWidth(i++, 5000);
                sheet.setColumnWidth(i, 7000);
                break;

        }
    }

    protected CellStyle createHeaderCellStyle(Workbook workbook){
        Font font = workbook.createFont();
        font.setFontHeightInPoints(HEADER_FONT);
        font.setFontName("Courier New");
        font.setBold(true);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        return style;
    }

    protected CellStyle createContentCellStyle1(Workbook workbook){
        Font font = workbook.createFont();
        font.setFontHeightInPoints(CONTENT_FONT);
        font.setFontName("Arial");
        CellStyle style = workbook.createCellStyle();
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        //style.setBorderTop(BorderStyle.MEDIUM_DASHED);
        //style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillBackgroundColor(IndexedColors.WHITE1.getIndex());
        style.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        return style;
    }

    protected CellStyle createContentCellStyle2(Workbook workbook){
        Font font = workbook.createFont();
        font.setFontHeightInPoints(CONTENT_FONT);
        font.setFontName("Arial");
        CellStyle style = workbook.createCellStyle();
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        //style.setBorderTop(BorderStyle.MEDIUM_DASHED);
        //style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(font);
        return style;
    }

}
