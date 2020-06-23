package com.example.sw2.controller.sede;

import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateExcel {

    public void newExcel() {
        // Creating an instance of HSSFWorkbook.
        HSSFWorkbook workbook = new HSSFWorkbook();

        // Create two sheets in the excel document and name it First Sheet and
        // Second Sheet.
        HSSFSheet firstSheet = workbook.createSheet("FIRST SHEET");
        HSSFSheet secondSheet = workbook.createSheet("SECOND SHEET");

        // Manipulate the firs sheet by creating an HSSFRow which represent a
        // single row in excel sheet, the first row started from 0 index. After
        // the row is created we create a HSSFCell in this first cell of the row
        // and set the cell value with an instance of HSSFRichTextString
        // containing the words FIRST SHEET.
        HSSFRow rowA = firstSheet.createRow(0);
        HSSFCell cellA1 = rowA.createCell(0);
        HSSFCell cellA2 = rowA.createCell(1);
        HSSFCell cellA3 = rowA.createCell(2);
        cellA1.setCellValue(new HSSFRichTextString("first"));
        cellA2.setCellValue(new HSSFRichTextString("second"));
        cellA3.setCellValue(new HSSFRichTextString("third"));

        HSSFRow rowB = secondSheet.createRow(0);
        HSSFCell cellB1 = rowB.createCell(0);
        HSSFCell cellB2 = rowB.createCell(1);
        HSSFCell cellB3 = rowB.createCell(2);
        cellB1.setCellValue(new HSSFRichTextString("f"));
        cellB2.setCellValue(new HSSFRichTextString("s"));
        cellB3.setCellValue(new HSSFRichTextString("t"));

        // To write out the workbook into a file we need to create an output
        // stream where the workbook content will be written to.
        try (FileOutputStream fos =
                     new FileOutputStream(new File("CreateExcelDemo.xls"))) {
            workbook.write(fos);
            System.out.println("Excel creado exitosamente");
        } catch (IOException e) {
            System.out.println("Excepcion generada");
            e.printStackTrace();
        }
    }
}
