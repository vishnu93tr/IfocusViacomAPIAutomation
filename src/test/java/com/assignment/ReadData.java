package com.assignment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadData {


	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		FileInputStream fis=new FileInputStream("C:\\Users\\iFoucs02\\Desktop\\data2.xlsx");
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet("Sheet1");
		int rowcount=sh.getLastRowNum();
		System.out.println(rowcount+1);
		for(int i=0;i<=rowcount;i++) {
			Row row=sh.getRow(i);
			String mediaid=row.getCell(0).getStringCellValue();
			System.out.println("media id is:"+mediaid);
			FileInputStream fis1=new FileInputStream("C:\\Users\\iFoucs02\\Desktop\\createsheet1.xlsx");
			Workbook wb1=WorkbookFactory.create(fis1);
			wb1.createSheet(mediaid);
			
			FileOutputStream fos1=new FileOutputStream("C:\\Users\\iFoucs02\\Desktop\\createsheet1.xlsx");
			wb1.write(fos1);
			fos1.close();
		}
		
	

	}

}
