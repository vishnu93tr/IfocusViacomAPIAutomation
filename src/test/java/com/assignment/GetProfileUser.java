package com.assignment;

import org.apache.log4j.BasicConfigurator;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.response.Response;

public class GetProfileUser {
	@Test
	public void getProfileUser() {
		BasicConfigurator.configure();
		RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
		
		Response resp=RestAssured.
					given().
					queryParam("platform","Cellular").
					queryParam("pId",1).
					queryParam("user_id","9c01c93f8f08450784d2b69b9a96f754").
					contentType("application/json").
					request().
					post("https://px-authuat.voot.com/wsv_2_0/user/getProfile.json");
		resp.prettyPrint();
		
	}

}
