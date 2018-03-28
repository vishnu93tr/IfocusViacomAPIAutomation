package com.assignment;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateExpiredException;
import java.util.List;

import javax.net.ssl.SSLException;

import org.apache.log4j.BasicConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class Playback_tv {
	@Test
	public void playback_TV() throws IOException, EncryptedDocumentException, InvalidFormatException   {
	
	
		BasicConfigurator.configure();
		Response resp = given().
						param("mediaId",440601).
						param("rowId",728).
						when().
						get("https://apiv2.voot.com/wsv_1_0/playList.json");
		
		resp.prettyPrint();
		resp.then().and().assertThat().statusCode(200);
		List<String> jsonResponse = resp.jsonPath().getList("assets.current[0].files");
		int size=  jsonResponse.size();
		System.out.println("size is:"+size);
		for(int k=0; k<size; k++)
		{
			try {
			String url1 = resp.getBody().jsonPath().getJsonObject("assets.current[0].files["+k+"].URL");	
			String FileID=resp.getBody().jsonPath().getJsonObject("assets.current[0].files["+k+"].FileID");
			String Format=resp.getBody().jsonPath().getJsonObject("assets.current[0].files["+k+"].Format");
			
			

			
			
			FileInputStream fis1=new FileInputStream("C:\\Users\\iFoucs02\\Desktop\\createsheet1.xlsx");
			Workbook wb1=WorkbookFactory.create(fis1);
			
			Sheet sh1=wb1.getSheet("440601");
			Row row1=sh1.createRow(k);
			row1.createCell(0);
			Cell cel1=	row1.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cel1.setCellType(CellType.STRING);
			cel1.setCellValue(url1);
			row1.createCell(2);
			Cell cel3=	row1.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cel3.setCellType(CellType.NUMERIC);
			cel3.setCellValue(FileID);
			row1.createCell(3);
			Cell cel4=	row1.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cel4.setCellType(CellType.NUMERIC);
			cel4.setCellValue(given().when().get(url1).getStatusCode());
			row1.createCell(4);
			Cell cel5=	row1.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cel5.setCellType(CellType.STRING);
			cel5.setCellValue(Format);
			
			
			
			FileOutputStream fos=new FileOutputStream("C:\\Users\\iFoucs02\\Desktop\\createsheet1.xlsx");
			wb1.write(fos);
			
			fos.close();
			}
			catch(SSLException s) {
				
				s.toString();
			}
			
			
			}
		
		
		
		
	
}
}
