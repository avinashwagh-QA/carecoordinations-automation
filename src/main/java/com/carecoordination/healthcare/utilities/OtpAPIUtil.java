package com.carecoordination.healthcare.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Fetches the latest OTP for forgot/reset password flow
 * by calling backend OTP API (QA environments only).
 * <p>
 * Flow:
 * 1. Validates email and API enable flag
 * 2. Calls OTP API endpoint
 * 3. Retries OTP fetch if not immediately available
 * 4. Extracts OTP from nested response object
 * 5. Returns OTP for UI automation usage
 * <p>
 * Note:
 * * - Designed to handle backend timing delays (async OTP generation)
 * * - Used only to support Selenium tests, not for API validation
 */

public class OtpAPIUtil {

    public static Logger logger = LogManager.getLogger(OtpAPIUtil.class);

    int maxAttempts = 3;
    int attempt = 1;
    String otp = null;

    public String getOtp() throws InterruptedException {

        //Check Email if email is valid
        String email = ConfigReader.getProperty("otpEmail");
        if (email == null || email.isBlank()) {
            logger.error("OTP Email is invalid or missing");// Use ERROR level
            throw new RuntimeException("OTP Email not configured - skipping");  // Stop execution
        }

        boolean statusAPI = Boolean.parseBoolean(ConfigReader.getProperty("otp.api.enabled"));

        if (!statusAPI) {
            logger.warn("OTP API disabled for this environment - skipping");
            throw new RuntimeException("API not enabled");
        }

        logger.info("Email and API checks passed");

        String completeUrl = ConfigReader.getProperty("otp.api.base.url") + email;

        Response response = RestAssured
                .given()
                .when()
                .get(completeUrl);

        int statusCode = response.getStatusCode();
        logger.info("OTP API Status Code: {}", statusCode);

        if (statusCode != 200) {
            logger.error("API fails to get the response with status 200...");
            throw new RuntimeException("API fails - 200 status code not received");
        }

        logger.info("API called Successfully {}", completeUrl);

        while (attempt <= maxAttempts) {

            otp = response.jsonPath().getString("data.User.otp");

            if (otp != null && otp.isBlank()) {
                logger.info("OTP fetched successfully on attempt {}", attempt);
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Retry interrupted while waiting for OTP", e);
            }
            attempt++;

        }

        //final validation after retry
        if (otp == null || otp.trim().isBlank()) {
            logger.error("OTP not received after {} attempts", maxAttempts);
            throw new RuntimeException("OTP not available after retries");
        }

        logger.info("OTP received  successfully of length {}", otp.length());

        return otp;

    }


}
