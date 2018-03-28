package com.assignment;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class Issue {
	@Test
	public void Issue_Test() throws IOException, EncryptedDocumentException, InvalidFormatException {
		BasicConfigurator.configure();
		Response resp = given().
					when().
					get("https://wapiv2.voot.com/wsv_1_0/tabs.json");

		resp.prettyPrint();
		resp.then().and().assertThat().statusCode(400);
		

}}
