package com.loginradius;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;


public class Imageupdate {
	@Test(enabled=false)
	public void imageupdate_positive() throws EncryptedDocumentException, InvalidFormatException, IOException {
		RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
		GenericMethods g=new GenericMethods();
		   Response resp= g.createapi();
		    
		    String account_id= resp. 
		    					then().
		    					extract().
		    					path("LoginRadius.Uid");
		    String path1="C:\\Users\\iFoucs02\\eclipse-workspace\\APITESTING\\testdata.xls";
			FileInputStream fis=new FileInputStream(path1);
			Workbook wb=WorkbookFactory.create(fis);
			Sheet sh=wb.getSheet("Sheet1");
			Row row=sh.getRow(11);
			String platform=row.getCell(0).getStringCellValue();
			String pId=row.getCell(1).getStringCellValue();
			String URL_checkemail=row.getCell(4).getStringCellValue();
			
			
			resp.prettyPrint();
			Response resp1=	RestAssured.
				given().
		       multiPart("image", new File("C:\\Users\\iFoucs02\\eclipse-workspace\\APITESTING\\ANDROID.png")).
	           accept("application/json").
	           	queryParam("platform",platform).
				queryParam("pId",pId).
				queryParam("user_id",account_id).
				queryParam("email", "vishnu26121993@gmail.com").
			     post(URL_checkemail);
			resp1.prettyPrint();
			
		
	}

}
