package Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excel_utils {

    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static CellStyle style;
    String xlfile;
    
    public Excel_utils(String xlfile) {
    	this.xlfile = xlfile;
    }
    public  int getRowCount( String xlsheet) throws IOException {
        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        int rowCount = ws.getLastRowNum();
        wb.close();
        fi.close();
        return rowCount;
    }

    // Get Cell Count
    public int getCellCount( String xlsheet, int rownum) throws IOException {
        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rownum);
        int cellCount = row.getPhysicalNumberOfCells();
        wb.close();
        fi.close();
        return cellCount;
    }
 // Get Cell Data
 
        
        public String getCellData( String xlsheet, int rownum, int colnum) throws IOException {
            fi = new FileInputStream(xlfile);
            wb = new XSSFWorkbook(fi);
            ws = wb.getSheet(xlsheet);
            row = ws.getRow(rownum);
            
            // Handle case where row or cell may be null
            if (row == null) {
                wb.close();
                fi.close();
                return ""; // Return empty string if row is null
            }
            
            cell = row.getCell(colnum);
            if (cell == null) {
                wb.close();
                fi.close();
                return ""; // Return empty string if cell is null
            }

            // Use DataFormatter to get a formatted string
            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);

            wb.close();
            fi.close();
            return cellData;
        }
        public  void setCellData(String xlsheet, int rownum, int colnum, String data, boolean isGreen) throws IOException {
            fi = new FileInputStream(xlfile);
            wb = new XSSFWorkbook(fi);
            ws = wb.getSheet(xlsheet);

            // Create or get the specified row
            row = ws.getRow(rownum);
            if (row == null) {
                row = ws.createRow(rownum);
            }

            // Create or get the specified cell
            cell = row.getCell(colnum);
            if (cell == null) {
                cell = row.createCell(colnum);
            }

            // Set cell value
            cell.setCellValue(data);

            // Apply color based on isGreen flag
            if (isGreen) {
                applyGreenColor(cell, wb);
            } else {
                applyRedColor(cell, wb);
            }

            // Write the changes back to the file
            fo = new FileOutputStream(xlfile);
            wb.write(fo);

            // Close resources
            wb.close();
            fi.close();
            fo.close();
        }

        public static void applyGreenColor(Cell cell, Workbook wb) {
            CellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }

        // Method to apply red color to a cell
        public static void applyRedColor(Cell cell, Workbook wb) {
            CellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }

    }



