package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.api.Client.UserRegistrationApi;
import com.carecoordination.healthcare.api.DTO.UserRegistrationDetailsResponse;
import com.carecoordination.healthcare.constants.OtpUserContext;
import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.AppDashboardPage;
import com.carecoordination.healthcare.pages.modules.accountSetup.AccountSetupPage;
import com.carecoordination.healthcare.pages.landingPages.LandingPage;
import com.carecoordination.healthcare.pages.landingPages.OtpVerifyPage;
import com.carecoordination.healthcare.pages.landingPages.RegisterPage;
import com.carecoordination.healthcare.pages.modules.accountSetup.MpinSetupPage;
import com.carecoordination.healthcare.utilities.ConfigReader;
import com.carecoordination.healthcare.utilities.OtpAPIUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestDataProvider;


public class RegisterPageTest extends BaseTest {

    private RegisterPage registerPage;
    private OtpVerifyPage otpVerifyPage;
    private OtpAPIUtil otpAPIUtil;
    private AccountSetupPage accountSetupPage;
    private UserRegistrationApi userRegistrationApi;
    private UserRegistrationDetailsResponse userDetailDto;
    private MpinSetupPage mpinSetupPage;
    private AppDashboardPage appDashboardPage;
    private LandingPage landingPage;

    private static final Logger logger = LogManager.getLogger(RegisterPageTest.class);

    @BeforeMethod
    public void setUpPages(){
        LandingPage landingPage = new LandingPage(actionDriver);
        registerPage = new RegisterPage(actionDriver);
        otpVerifyPage = new OtpVerifyPage(actionDriver);
        otpAPIUtil = new OtpAPIUtil();
        accountSetupPage = new AccountSetupPage(actionDriver);
        userRegistrationApi = new UserRegistrationApi();
        mpinSetupPage = new MpinSetupPage(actionDriver);
        appDashboardPage = new AppDashboardPage(actionDriver);
        landingPage = new LandingPage(actionDriver);

        landingPage.clickOnRegisterLink();
        Assert.assertTrue(registerPage.isRegisterPageDisplayed(), "Register page does not displayed");
    }

    @Test(groups = "skip-login",
    description = "Verify basic UI on default register screen")
    public void VerifyDefaultUIonRegisterPage(){

        // Verify Title
        Assert.assertEquals(registerPage.getRegisterPageTitle(), "Register", "Title on register page does not displayed");

        //Verify Invitation code and Email field is displayed
        Assert.assertTrue(registerPage.isInputInvitationCodeAndEmailFieldDisplayed(),
                "Invitation and Email does not displayed on Register page");

        //Verify Default state for Continue button is disabled
        Assert.assertFalse(registerPage.verifyContinueButtonEnabled(), "continue button on register page appear to enabled by default");

    }

    @Test(groups = "skip-login", dataProvider = "invalidRegisterAccountData",
    dataProviderClass = TestDataProvider.class,
    description = "Verify the Register page with valid and invalid email and invitation code")
    public void validateRegisterPageWithInvitationEmailCombination(String invitationCode,
                                                                   String emailKey,
                                                                   String expectedMsgKey){

        // Verify Title
        Assert.assertEquals(registerPage.getRegisterPageTitle(), "Register", "Title on register page does not displayed");

        String invCode = ConfigReader.getProperty(invitationCode);
        String email = ConfigReader.getProperty(emailKey);

        registerPage.completeRegistration(invCode, email);

        String actualMessage= registerPage.getErrorMessage();
        String expectedMessage = ConfigReader.getProperty(expectedMsgKey);

        Assert.assertEquals(actualMessage, expectedMessage, "On register page the message does not match");
    }

    @Test(groups = "skip-login",
    description = "Verify the Login link present on the register page")
    public void validateRegisterPageContainsLoginLink(){

        Assert.assertTrue(registerPage.isLoginLinkDisplayed(), "Login link does not displayed on register page");
    }

    @Test(groups = "skip-login",
    description = "Verify register guide displayed on register page")
    public void validateRegisterPageDisplayedRegisterGuide(){

        Assert.assertTrue(registerPage.isRegisterGuideDisplayed(), "Register guide does nor displayed on register page");
        Assert.assertTrue(registerPage.isRegisterGuideModalOpenWhenClicked(), "Register guide modal does not displayed");
    }

    @Test(groups = "skip-login",
    description = "Verify User navigates Verify OTP page on entering valid email and invitation code")
    public void validateUserNavigatesToOtpPageOnValidCodeAndEmail(){

       String invitationCode = ConfigReader.getProperty("validCode");
       String email = ConfigReader.getProperty("validInvitedEmail");

       registerPage.completeRegistration(invitationCode, email);

       Assert.assertTrue(otpVerifyPage.isOtpPageTitleDisplayed(), "Title for OTP verification page not displayed");

    }

    @Test(groups = "skip-login",
    description = "Verify navigating back from register verify otp page then user navigates to register page")
    public void validateNavigateToBackFromOtpPage(){

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isOtpPageTitleDisplayed(), "Title for OTP verification page not displayed");

        registerPage.clickOnGoBack();

        Assert.assertTrue(registerPage.isRegisterPageDisplayed(), "Register page not displayed after go back From OTP");

    }

    @Test(groups = "skip-login",
    description = "Verify Message on invalid OTP is enter from register page")
    public void verifyMessageOnInvalidOtp(){

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isOtpPageTitleDisplayed(), "Title for OTP verification page not displayed");

        otpVerifyPage.setOTPInputs(ConfigReader.getProperty("invalidOtp"));

        String actualMSg = registerPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty("incorrectOtpMsg");

        Assert.assertEquals(actualMSg, expectedMsg, "Message does not match");
    }

    @Test(groups = "skip-login",
            description = "Verify OTP-Resend link is displayed on the OTP verify page when user navigates to register page")
    public void verifyResendOtpLinkIsPresent(){

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isOtpPageTitleDisplayed(), "Title for OTP verification page not displayed");

        Assert.assertTrue(otpVerifyPage.isResendOtpDisplayedOnRegisterPage(), "Resend otp link is not displayed on OTP page ");
    }

    @Test(groups = "skip-login",
    description = "Verify old OTP becomes invalid after resend ")
    public void verifyOldOtpExpiresAfterResend(){

        logger.info("Verify on resend OTP the old Otp expire successfully");

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isOtpPageTitleDisplayed(), "Title for OTP verification page not displayed");

        //Fetching 1st OTP in otp1
        String otp1 = otpAPIUtil.getOtp(email, OtpUserContext.UNREGISTERED_USER);
        logger.info("The OTP on first attempt is {}", otp1);

        //Verify resend OTP link and click
        Assert.assertTrue(otpVerifyPage.isResendOtpDisplayedOnRegisterPage(), "ResendOTP link is not present");
        otpVerifyPage.clickOnResendOtpForRegisterPage();

        // Enter old OTP
        otpVerifyPage.setOTPInputs(otp1);

        String actualMSg = otpVerifyPage.getErrorMessage();
        String expectedMsg = ConfigReader.getProperty("incorrectOtpMsg");

        Assert.assertEquals(actualMSg, expectedMsg, "Message does not match");

    }

    @Test(groups = "skip-login",
    description = "Verify default UI for the account setup page")
    public void validateDefaultUIOnAccountSetupPage(){

        logger.info("Verify Basic UI on Default screen for account setup page...");

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isOtpPageTitleDisplayed(), "Title for OTP verification page not displayed");

        //Fetching and set OTP
        String otp = otpAPIUtil.getOtp(email, OtpUserContext.UNREGISTERED_USER);
        otpVerifyPage.setOTPInputs(otp);

        //Verify welcome title
        String actualTitle = accountSetupPage.getWelcomeTitleOnAccountSetup();
        String expectedTitle = ConfigReader.getProperty("accountSetUpTitle");

        Assert.assertEquals(actualTitle, expectedTitle, "Title for CC does not match in Account setup page");

        // Verify logo displayed
        Assert.assertTrue(accountSetupPage.isCompanyLogoDisplayed(), "Company logo does not displayed");

        //verify input title and password field displayed
        Assert.assertTrue(accountSetupPage.isTitleAndPasswordDisplayed(), "Title and password not displayed in account setup page");

        //Verify terms and condition is displayed
        Assert.assertTrue(accountSetupPage.isTermsAndConditionDisplayed(), "Terms and condition does not displayed");

        //Verify default register button
       Assert.assertFalse(accountSetupPage.isRegisterButtonEnabled(),"Register button not enabled by default");
    }

    private void navigateToAccountSetupPage(){

        String invitationCode = ConfigReader.getProperty("validCode");
        String email = ConfigReader.getProperty("validInvitedEmail");

        registerPage.completeRegistration(invitationCode, email);

        Assert.assertTrue(otpVerifyPage.isOtpPageTitleDisplayed(), "Account setup page not displayed after OTP verification");

        //Fetching and set OTP
        String otp = otpAPIUtil.getOtp(email, OtpUserContext.UNREGISTERED_USER);
        otpVerifyPage.setOTPInputs(otp);

        Assert.assertTrue(accountSetupPage.isCompanyLogoDisplayed(), "Company logo does not displayed");
    }


    @Test(groups = "skip-login",
    description = "Verify Username displayed on Account setup is displayed as per invited user ")
    public void validateUsernameDisplayed(){

        navigateToAccountSetupPage();

        String email = ConfigReader.getProperty("validInvitedEmail");
        userDetailDto = userRegistrationApi.getInvitedUserDetails(email);

        String actualUserName = accountSetupPage.getDisplayedUserName().trim();
        logger.info("Actual user name from UI: {}", actualUserName);

        String expectedUserName = userDetailDto.getName().trim();
        logger.info("Expected user name from API: {}", expectedUserName);

        Assert.assertTrue(actualUserName.contains(expectedUserName), "User name incorrectly displayed");
    }

    @Test(groups = "skip-login",
    description = "Verify user role correctly displayed as per user invited")
    public void validateCorrectUserRoleDisplayed(){

        navigateToAccountSetupPage();

        String email = ConfigReader.getProperty("validInvitedEmail");
        userDetailDto = userRegistrationApi.getInvitedUserDetails(email);

        String actualUserRole = accountSetupPage.getDisplayedRoleAndOrg().trim();
        logger.info("Actual user role and organization name displayed in account setup page {}", actualUserRole);

        String expectedUserRole = userDetailDto.getUserType().trim();
        logger.info("Actual user role displayed in account setup page {}", expectedUserRole);

        Assert.assertTrue(actualUserRole.contains(expectedUserRole), "User role does not match");
    }


    @Test(groups = "skip-login",
            dataProvider = "passwordValidationData", dataProviderClass = TestDataProvider.class,
            description = "Verify the Rules on the Account setup page for password validation")
    public void validatePasswordRule(String password, boolean expectedSubmitState){

        navigateToAccountSetupPage();

        accountSetupPage.enterProfession("Director of Nursing");
        accountSetupPage.enterPassword(password);

        accountSetupPage.clickOnCheckBoxTermsAndCondition();

        Assert.assertEquals(accountSetupPage.isRegisterButtonEnabled(),
                expectedSubmitState,
                "Registration button state mismatch for password: " + password);
    }

    @Test(groups = "skip-login",
    description = "Verify on Account set up page user can copy password and success message is displayed")
    public void validateOnCopyPasswordSuccessMsgDisplayed(){

        navigateToAccountSetupPage();

        accountSetupPage.enterProfession("Director of Nursing");
        accountSetupPage.enterPassword("Password@123");

        accountSetupPage.clickOnCopyPassword();
        Assert.assertTrue(accountSetupPage.isSuccessToastDisplayedOnCopyPassword(), "Copy password toast does not displayed");
    }

    @Test(groups = "skip-login",
    description = "Verify tooltip Displayed for user role system admin, Branch admin and Manager supervisor role ")
    public void validateTooltipDisplayedOnAccountSetUp(){

        navigateToAccountSetupPage();

        accountSetupPage.clickOnToolTip();

        String actualTitle = accountSetupPage.getToolTipDisplayedOnAccountSetup()
                .replace("\n", " ") //Replaces line breaks with a space
                .replaceAll("\\s+", " ") //Collapses multiple spaces into one
                .trim();
        String expectedTitle = ConfigReader.getProperty("titleA")
                .replaceAll("\\s+", " ")
                .trim();

        Assert.assertEquals(actualTitle,expectedTitle, "Title does not match on Account setup page");
    }

    @Test(groups = "skip-login",
    description = "Verify by completing registration with MPIN set up")
    public void validateCompleteRegistrationWithSetMpin(){

        navigateToAccountSetupPage();

        accountSetupPage.enterProfession("Director of Nursing");
        accountSetupPage.enterPassword("Password@123");

        accountSetupPage.clickOnCheckBoxTermsAndCondition();

        accountSetupPage.clickOnRegisterButton();

        Assert.assertTrue(mpinSetupPage.isAccountSetMpinModalIsDisplayed(), "Modal for account set up MPIN does not displayed ...");

        String actualModalTitle = mpinSetupPage.getTitleForMpinAccountSetup().replaceAll("\\s+", " ").trim();
        String expectedModalTitle = ConfigReader.getProperty("accountSetupModalTitle").replaceAll("\\s+", " ").trim();

        Assert.assertEquals(actualModalTitle.toLowerCase(), expectedModalTitle, "Title does not matched Account setup");

        Assert.assertFalse(mpinSetupPage.isBtnEnabled(), "Button appears to enabled for setMPIn by default");

        mpinSetupPage.enterMpin("000000");
        mpinSetupPage.clickOnSetInputs();

        Assert.assertTrue(appDashboardPage.isUserNamePresent(), "User name does not displayed");

        appDashboardPage.clickOnLogOut();
        Assert.assertTrue(landingPage.isHomePageTitleDisplayed(), "Home page title not displayed, user not logout");
    }










}


