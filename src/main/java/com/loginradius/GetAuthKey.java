package com.loginradius;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

public class GetAuthKey {
	@Test(priority=6)
	public void getauthkey_positive() throws EncryptedDocumentException, InvalidFormatException, IOException {
		//generic method
		RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
		String path1="C:\\Users\\iFoucs02\\eclipse-workspace\\APITESTING\\testdata.xls";
		GenericMethods r=new GenericMethods();
		Response resp=	r.createapi();
		String Uid = resp.jsonPath().get("LoginRadius.Uid");
				//reading data for get auth key
					FileInputStream fis=new FileInputStream(path1);
					Workbook wb=WorkbookFactory.create(fis);
					Sheet sh=wb.getSheet("Sheet1");
					Row row1=sh.getRow(6);
					String platform=row1.getCell(0).getStringCellValue();
					String pId=row1.getCell(1).getStringCellValue();
					String URL_getauthkey=row1.getCell(4).getStringCellValue();
					Response resp1=	RestAssured.
									given().
									relaxedHTTPSValidation().
									contentType(ContentType.JSON).
									accept(ContentType.JSON).
									queryParam("platform",platform).
									queryParam("pId",pId).
									queryParam("UID",Uid).
									when().
									post(URL_getauthkey);
			resp1.prettyPrint();
			resp1.then().assertThat().statusCode(200);
			resp1.then().body("$", hasKey("token"));
			resp1.then().body("token", is(IsNull.notNullValue()));
			
			
			String str1=resp1.asString();
			
			FileInputStream fis1=new FileInputStream(path1);
			Workbook wb1=WorkbookFactory.create(fis1);
			
			Sheet sh1=wb1.getSheet("Sheet1");
			Row row2=sh1.getRow(6);
			row2.createCell(5);
			Cell cel2=	row2.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cel2.setCellType(CellType.STRING);
			cel2.setCellValue(str1);
			
			Row row3=sh1.getRow(6);
			row3.createCell(6);
			Cell cel3=	row3.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cel3.setCellType(CellType.NUMERIC);
			cel3.setCellValue(resp1.statusCode());
			
			FileOutputStream fos=new FileOutputStream(path1);
			wb1.write(fos);
			
			fos.close();
			
			
				
				
		
	}

}
