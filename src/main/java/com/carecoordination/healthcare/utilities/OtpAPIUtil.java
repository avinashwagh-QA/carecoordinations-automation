package com.carecoordination.healthcare.utilities;

import com.carecoordination.healthcare.constants.OtpUserContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Fetches the latest OTP for forgot password and registration flows
 * by calling backend OTP API (QA environments only).
 *
 * Flow:
 * 1. Validates email configuration and API enable flag
 * 2. Calls OTP API with retry to handle async OTP generation
 * 3. Re-invokes API until OTP is available or retries are exhausted<
 * 4. Extracts OTP from nested response object based on user context
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

    public String getOtp(String email, OtpUserContext context) {

        //Check Email if email is valid
        if (email == null || email.isBlank()) {
            logger.error("OTP Email is invalid or missing");
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

        String otp;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            Response response = RestAssured.get(completeUrl);

            logger.info("Attempt {} - Status Code: {}", attempt, response.getStatusCode());

            if (response.getStatusCode() == 200) {

                otp = extractOtp(response, context);

                if (otp != null && !otp.isBlank()) {
                    logger.info("OTP fetched for {} on attempt {}", context, attempt);
                    return otp;
                }
            }

            sleep();
        }

        throw new RuntimeException("OTP not received after retries");
    }


    private String extractOtp(Response response, OtpUserContext context) {

        return switch (context) {
            case REGISTERED_USER -> response.jsonPath().getString("data.User.otp");
            case UNREGISTERED_USER -> response.jsonPath().getString("data.InviteAgencyStaff.otp");
        };
    }

        private void sleep () {
            try {
                Thread.sleep(WAIT_TIME_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }


    }
