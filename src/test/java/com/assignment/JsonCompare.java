package com.assignment;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.BasicConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;



import com.flipkart.zjsonpatch.*;
import com.jayway.jsonpath.internal.filter.ValueNode.JsonNode;
import com.jayway.restassured.response.Response;

import java.util.*;

public class JsonCompare {
	
	@Test
	public void Json_Test() throws IOException, EncryptedDocumentException, InvalidFormatException {
		BasicConfigurator.configure();
		Response resp=given().
					when().
					get("https://apiuat2.voot.com/wsv_1_0/config.json");

		String fis1=resp.asString();
		FileOutputStream fos=new  FileOutputStream(fis1);
		Response resp1=given().
						when().
						get("https://apiv2.voot.com/wsv_1_0/config.json");
		
}}
