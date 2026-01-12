package com.carecoordination.healthcare.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OtpAPIUtil {


    public static Logger logger = LogManager.getLogger(OtpAPIUtil.class);

    public String getOTP(){

        //Check Email if email is valid
        String email = ConfigReader.getProperty("otpEmail");
        if( email==null || email.trim().isEmpty() || email.isBlank()){
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






    }





}
