package com.carecoordination.healthcare.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Fetches the latest OTP for forgot/reset password flow
 * by calling backend OTP API (QA environments only).
 *
 * Flow:
 * 1. Validates email configuration and API enable flag
 * 2. Calls OTP API with retry to handle async OTP generation
 * 3. Re-invokes API until OTP is available or retries are exhausted<
 * 4. Extracts OTP from nested response object
 * 5. Returns OTP for UI automation usage
 *
 * Note:
 * * - Designed to handle backend timing delays (async OTP generation)
 * * - Used only to support Selenium tests, not for API validation
 */

public class OtpAPIUtil {

    public static Logger logger = LogManager.getLogger(OtpAPIUtil.class);
    private static final int MAX_ATTEMPTS = 3;
    private static final int WAIT_TIME_MS = 3000;

    public String getOtp() {

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

        String baseUrl = ConfigReader.getProperty("otp.api.base.url");
        String completeUrl = baseUrl + email;

        logger.info("Fetching OTP for email: {}", email);

        String otp = null;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            Response response = RestAssured
                    .given()
                    .when()
                    .get(completeUrl);

            logger.info("Attempt {} - Status Code: {}", attempt, response.getStatusCode());


            if (response.getStatusCode() != 200) {
                logger.warn("OTP API returned non-200 response");
            } else {
                otp = response.jsonPath().getString("data.User.otp");
                if (otp != null && !otp.isBlank()) {
                    logger.info("OTP fetched successfully on attempt {}", attempt);
                    return otp;
                }
            }

            try {
                Thread.sleep(WAIT_TIME_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("OTP fetch interrupted", e);
            }
        }

        logger.error("OTP not received after {} attempts", MAX_ATTEMPTS);
        throw new RuntimeException("OTP not available after retries");
    }

}
