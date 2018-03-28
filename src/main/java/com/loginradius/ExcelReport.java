package com.loginradius;

import org.automationtesting.excelreport.Xl;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;

public class ExcelReport {

@Test(priority=10)
public void excelreport() {
	RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
	try {
		Xl.generateReport("excel-report.xlsx");
	} catch (Exception e) {
		
		e.printStackTrace();
	}
}
}

