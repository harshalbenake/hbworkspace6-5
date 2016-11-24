package com.example.harshalbenake.readexceldata;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends Activity {

    private TextView mtv_result;
    private String strResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_write = (Button) findViewById(R.id.btn_write);
        Button btn_read = (Button) findViewById(R.id.btn_read);
        mtv_result = (TextView) findViewById(R.id.tv_result);

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExcelFile("hb.xls");
            }
        });

        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readExcelFile("hb.xls");
            }
        });
    }


    private void readExcelFile(String filename) {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            System.out.println("Storage not available or read only");
            return;
        }

        try {
            // Creating Input Stream
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();

            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    System.out.println("Cell Value: " + myCell.toString());
                    strResult=strResult+" "+myCell.toString()+" ";
                }
                strResult=strResult+" \n";
                mtv_result.setText(strResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    private boolean saveExcelFile(String fileName) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            System.out.println("Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();
        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("HB Prodcut list");

        // Generate column headings
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("Sr.No.");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("Quantity");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("Price");
        c.setCellStyle(cs);

        // Generate column headings - row1
        Row row1 = sheet1.createRow(1);

        c = row1.createCell(0);
        c.setCellValue("1");
        c.setCellStyle(cs);

        c = row1.createCell(1);
        c.setCellValue("Product");
        c.setCellStyle(cs);

        c = row1.createCell(2);
        c.setCellValue("10");
        c.setCellStyle(cs);

        for(int rowItem=2;rowItem<5;rowItem++){
            Row rowNo = sheet1.createRow(rowItem);

            c = rowNo.createCell(0);
            c.setCellValue(rowItem+"");
            c.setCellStyle(cs);

            c = rowNo.createCell(1);
            c.setCellValue("Product Item"+rowItem);
            c.setCellStyle(cs);

            c = rowNo.createCell(2);
            //minimum + rn.nextInt(maxValue - minvalue + 1)
            c.setCellValue(new Random().nextInt(100-10+1) + 10+"");
            c.setCellStyle(cs);
        }

        sheet1.setColumnWidth(0, (15 * 100));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));

        // Create a path where we will place our List of objects on external storage
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            System.out.println("Writing file" + file);
            success = true;
        } catch (IOException e) {
            System.out.println("Error writing " + file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
