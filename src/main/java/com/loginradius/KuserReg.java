package com.loginradius;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
import org.hamcrest.core.IsNull;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class KuserReg {
	@Test(priority=8)
	public void kuserreg_positive() throws EncryptedDocumentException, InvalidFormatException, IOException {
		//using random email generator
		RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
				GenericMethods r=new GenericMethods();
				String email=	r.getSaltString()+"@voot.com";
				System.out.println(email);
				String UID=	r.getSaltString()+"UID";
				System.out.println(UID);
				RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
				String path1="C:\\Users\\iFoucs02\\eclipse-workspace\\APITESTING\\testdata.xls";
				FileInputStream fis=new FileInputStream(path1);
				Workbook wb=WorkbookFactory.create(fis);
				Sheet sh=wb.getSheet("Sheet1");
				Row row=sh.getRow(5);
				String platform=row.getCell(0).getStringCellValue();
				String pId=row.getCell(1).getStringCellValue();
				String firstname=row.getCell(2).getStringCellValue();
				String lastname =row.getCell(3).getStringCellValue();
				String URL=row.getCell(4).getStringCellValue();
				
				BasicConfigurator.configure();
				Response resp=	RestAssured.
								given().
								queryParam("platform",platform).
								queryParam("pId",pId).
								queryParam("email",email).
								queryParam("UID",UID).
								queryParam("firstname",firstname).
								queryParam("lastname",lastname).
								when().
								post(URL);
				String str=	resp.asString();
				
				resp.then().assertThat().statusCode(200);
				
				resp.prettyPrint();
				
				resp.then().body("$", hasKey("SiteGuid"));
				resp.then().body("$", hasKey("DomainId"));
				
				
				resp.then().body("SiteGuid", is(IsNull.notNullValue()));
				resp.then().body("DomainId", is(IsNull.notNullValue()));
				
				
				FileInputStream fis1=new FileInputStream(path1);
				Workbook wb1=WorkbookFactory.create(fis1);
				
				Sheet sh1=wb1.getSheet("Sheet1");
				Row row1=sh1.getRow(5);
				row1.createCell(5);
				Cell cel1=	row1.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cel1.setCellType(CellType.STRING);
				cel1.setCellValue(str);
				
				Row row2=sh1.getRow(1);
				row2.createCell(6);
				Cell cel2=	row1.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cel2.setCellType(CellType.NUMERIC);
				cel2.setCellValue(resp.statusCode());
				
				FileOutputStream fos=new FileOutputStream(path1);
				wb1.write(fos);
				
				fos.close();
				
		
	}

}
