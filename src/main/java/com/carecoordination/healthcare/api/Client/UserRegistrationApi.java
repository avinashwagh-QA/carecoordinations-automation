package com.carecoordination.healthcare.api.Client;

import com.carecoordination.healthcare.api.DTO.UserRegistrationDetailsResponse;
import com.carecoordination.healthcare.utilities.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRegistrationApi {

    public static Logger logger = LogManager.getLogger(UserRegistrationApi.class);
    private static final int MAX_ATTEMPTS = 3;
    private static final int WAIT_TIME_MS = 3000;

    public UserRegistrationDetailsResponse getInvitedUserDetails(String email){


        //Check Email if email is valid
        if (email == null || email.isBlank()) {
            logger.error("Invited Email is invalid or missing");
            throw new RuntimeException("Invited Email not configured - skipping");  // Stop execution
        }

        boolean statusAPI = Boolean.parseBoolean(ConfigReader.getProperty("register.api.enabled"));

        if (!statusAPI) {
            logger.warn("Register API disabled for this environment - skipping");
            throw new RuntimeException("API not enabled");
        }

        logger.info("Email and API checks passed");

        String baseUrl = ConfigReader.getProperty("register.api.base.url");
        String completeUrl = baseUrl + email;

        logger.info("Fetching User data for invited email: {}", email);

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            Response response = RestAssured
                    .given()
                    .when()
                    .get(completeUrl);

            logger.info("Attempt {} - Status Code: {}", attempt, response.getStatusCode());

            if (response.getStatusCode() == 200) {

                return response.jsonPath().getObject(
                        "data.InviteAgencyStaff",
                        UserRegistrationDetailsResponse.class);
            }
            sleep();
        }
        throw new RuntimeException("User registration details not received after retries");
    }

    private void sleep () {
        try {
            Thread.sleep(WAIT_TIME_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}





