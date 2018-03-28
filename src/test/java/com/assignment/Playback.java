package com.assignment;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.cert.CertificateExpiredException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;

import org.apache.log4j.BasicConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hslf.record.Environment;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.eval.BlankEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class Playback {
	
	@Test
	public void playback_test_positive() throws EncryptedDocumentException, InvalidFormatException, IOException,SSLException, CertificateExpiredException  {
		
		String path1="C:\\Users\\iFoucs02\\eclipse-workspace\\APITESTING\\testdata.xls";
		FileInputStream fis=new FileInputStream(path1);
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet("Sheet2");
		int rowcount=sh.getLastRowNum();
		System.out.println(rowcount+1);
		for(int i=0;i<=rowcount;i++) {
		Row row=sh.getRow(i);
		String mediaid=row.getCell(0).getStringCellValue();
			BasicConfigurator.configure();
			Response resp = given().
							param("mediaId",mediaid).
							when().
							get("https://apiv2.voot.com/wsv_1_0/playBack.json");
			
			resp.prettyPrint();
			resp.then().and().assertThat().statusCode(200);
			//to check whether status code is zero or not
			
			int act=resp. then().
				 extract().path("status.code");
	
			String URL=resp. 
					then().
					extract().path("assets[0].assets[0].items[0].URL");
		
		Assert.assertNotNull(URL,"URL is  NULL");
		//to check file Id is null or not
		String fileID=resp. 
				then().
				extract().path("assets[0].assets[0].items[0].fileID");
	
		Assert.assertNotNull(fileID,"fileID is NULL");
		//image url has HTTP 200 or not
	
			//To get the size of file and to check FileIDcount=URLcount
			List<String> jsonResponse = resp.jsonPath().getList("assets.Files");
			int size=  jsonResponse.size();
			System.out.println("size is:"+size);
			
			for(int k=0; k<size; k++)
			{
				try {
				String url1 = resp.getBody().jsonPath().getJsonObject("assets[0].assets[0].items[0].files["+k+"].URL");	
				String FileID=resp.getBody().jsonPath().getJsonObject("assets[0].assets[0].items[0].files["+k+"].FileID");
				String Format=resp.getBody().jsonPath().getJsonObject("assets[0].assets[0].items[0].files["+k+"].Format");
				
				

				System.out.println("Media Id is: "+mediaid+"status code for url "+url1+" is "+given().when().get(url1).getStatusCode()+"FileID is:"+FileID);
				
				FileInputStream fis1=new FileInputStream("C:\\Users\\iFoucs02\\eclipse-workspace\\APITESTING\\testdata.xls");
				Workbook wb1=WorkbookFactory.create(fis1);
				
				Sheet sh1=wb1.getSheet("Sheet2");
				Row row1=sh1.createRow(k);
				row1.createCell(0);
				Cell cel1=	row1.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cel1.setCellType(CellType.STRING);
				cel1.setCellValue(url1);
				row1.createCell(1);
				Cell cel2=	row1.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cel2.setCellType(CellType.NUMERIC);
				cel2.setCellValue(mediaid);
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
				
				
				
				FileOutputStream fos=new FileOutputStream("C:\\Users\\iFoucs02\\eclipse-workspace\\APITESTING\\testdata.xls");
				wb1.write(fos);
				
				fos.close();
				}
				catch(SSLException s) {
					
					s.toString();
				}
				
				
				}
			
			
			
			
		}}
		
		
	
	
	
	//@Test
	public void playback_test_negative() {
		BasicConfigurator.configure();
		Response resp = given().
						param("mediaId","470321").
						when().
						get("https://wapiv2.voot.com/wsv_1_0/playback.json");
		resp.prettyPrint();
		resp.then().and().assertThat().statusCode(200);
		//to check whether status code is zero or not
		int act=resp. then().
				   extract().path("status.code");
		Assert.assertEquals(act, 1);
		Response resp1 = given().
				param("mediaId","470321").
				when().
				get("https://wapiv2.voot.com/wsv_1_0/playback.json");
		resp1.prettyPrint();
		resp1.then().and().assertThat().statusCode(200);
		int act1=resp. then().
				   extract().path("status.code");
		Assert.assertEquals(act1, 1);
		
		
	}
	
}
