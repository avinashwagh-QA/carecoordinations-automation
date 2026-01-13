package com.carecoordination.healthcare.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class OtpAPIUtil {


    /**
     * Fetches the latest OTP for forgot/reset password flow
     * by calling backend OTP API (QA environments only).
     *
     * Flow:
     * 1. Validates email and API enable flag
     * 2. Calls OTP API endpoint
     * 3. Extracts OTP from nested response object
     * 4. Returns OTP for UI automation usage
     *
     * Note: Used only to support Selenium tests, not API validation.
     */


    public static Logger logger = LogManager.getLogger(OtpAPIUtil.class);

    public String getOtp(){

        //Check Email if email is valid
        String email = ConfigReader.getProperty("otpEmail");
        if( email==null || email.isBlank()){
            logger.error("OTP Email is invalid or missing");// Use ERROR level
            throw new RuntimeException("OTP Email not configured - skipping");  // Stop execution
        }

        boolean statusAPI = Boolean.parseBoolean(ConfigReader.getProperty("otp.api.enabled"));

        if (!statusAPI) {
            logger.warn("OTP API disabled for this environment - skipping");
            throw new RuntimeException("API not enabled");
        }

        logger.info("Email and API checks passed");

        String completeUrl = ConfigReader.getProperty("otp.api.base.url")+ email;

        Response response = RestAssured
                .given()
                .when()
                .get(completeUrl);

        int statusCode = response.getStatusCode();
        logger.info("OTP API Status Code: {}", statusCode);

        if (statusCode!=200){
            logger.error("API fails to get the response with status 200...");
            throw new RuntimeException("API fails - 200 status code not received");
        }

        logger.info("API called Successfully {}", completeUrl);

        String otp = response.jsonPath().getString("data.User.otp");

        if(otp==null || otp.trim().isBlank()){
            logger.error("OTP is missing or Blank in API Response");
            throw new RuntimeException("OTP not found in API Response");
        }

      logger.info("OTP received  successfully of length {}", otp.length());

        return otp;


    }





}
