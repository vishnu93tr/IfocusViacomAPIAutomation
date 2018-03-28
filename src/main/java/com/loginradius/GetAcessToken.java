package com.loginradius;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.hamcrest.core.IsNull;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class GetAcessToken extends GenericMethods  {
	@Test(priority=5)
	public void getacesstoken() throws EncryptedDocumentException, InvalidFormatException, IOException {
		//using generic methods
		RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
		GenericMethods g=new GenericMethods();
		Response resp=	g.Authentication();
		
		String token_fromauth=resp. 
							  then().
							  extract().path("access_token");
		
		
		String path1="C:\\Users\\iFoucs02\\eclipse-workspace\\APITESTING\\testdata.xls";
		
		FileInputStream fis=new FileInputStream(path1);
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sh=wb.getSheet("Sheet1");
		Row row1=sh.getRow(7);
		String platform=row1.getCell(0).getStringCellValue();
		String pId=row1.getCell(1).getStringCellValue();
		String URL_acesstoken=row1.getCell(4).getStringCellValue();
		Response resp1=	RestAssured.
						given().
						relaxedHTTPSValidation().
						contentType(ContentType.JSON).
						accept(ContentType.JSON).
						queryParam("platform",platform).
						queryParam("pId",pId).
						queryParam("token",token_fromauth).
						when().
						post(URL_acesstoken);
		
		resp1.then().assertThat().statusCode(200);
		resp1.prettyPrint();
		
		resp1.then().body("$", hasKey("access_token"));
		resp1.then().body("access_token", is(IsNull.notNullValue()));
		
		String access_token=resp1. 
				then().
				extract().path("access_token");
		
		
	assertEquals(access_token,token_fromauth,"token from auth API is not equal to acess token in Get Acess Token API");
	
	String str=	resp1.asString();
	
	FileInputStream fis1=new FileInputStream(path1);
	Workbook wb1=WorkbookFactory.create(fis1);
	
	Sheet sh1=wb1.getSheet("Sheet1");
	Row row2=sh1.getRow(7);
	row2.createCell(5);
	Cell cel2=	row2.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK);
	cel2.setCellType(CellType.STRING);
	cel2.setCellValue(str);
	
	Row row3=sh1.getRow(7);
	row3.createCell(6);
	Cell cel3=	row3.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK);
	cel3.setCellType(CellType.NUMERIC);
	cel3.setCellValue(resp1.statusCode());
	
	FileOutputStream fos=new FileOutputStream(path1);
	wb1.write(fos);
	
	fos.close();
	
		
		
		
		
		
	}

}
