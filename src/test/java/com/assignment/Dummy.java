package com.assignment;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.response.Response;

public class Dummy {
	@Test
	public void dummy()  {
		RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
		Dummy1 Dummy1=new Dummy1();
		Dummy1.setemail("vishnu26121993@gmail.com");
		BasicConfigurator.configure();
			Response resp=RestAssured.
							given().
							parameter("email",Dummy1).
							contentType("application/json").
							request().
							post("https://api.loginradius.com/identity/v2/auth/email");
			
			resp.prettyPrint();
			resp.then().assertThat().statusCode(200);
			
}}
