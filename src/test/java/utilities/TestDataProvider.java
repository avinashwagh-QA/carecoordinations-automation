package utilities;


import com.carecoordination.healthcare.constants.UserRole;
import org.testng.annotations.DataProvider;


public class TestDataProvider {


    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"invalidEmail", "invalidPassword", "invalidEmailMsg"},
                {"invalidEmail", "password", "invalidEmailMsg"},
                {"email", "invalidPassword", "invalidPasswordMsg"},
                {"", "", "blankEmailMsg"},
                {"email", "", "blankPasswordMsg"}
        };
    }

    @DataProvider(name = "roleBasedLogin")
    public static Object[][] roleBasedLogin(){
        return new Object[][]{
                {UserRole.SUPER_ADMIN},
                {UserRole.BRANCH_ADMIN},
                {UserRole.MANAGER_SUPERVISOR},
                {UserRole.CLERICAL_STAFF},
                {UserRole.FIELD_CLINICIAN},
                {UserRole.TRIAGE_STAFF}
        };
    }

    @DataProvider(name = "invalidForgotPasswordMobileData")
    public static Object[][] invalidForgotPasswordMobileData(){
        return new Object[][]{
                { "countryCode","mobileLessThan10Digit", "msgMobileLessThan10Digit"},
                {"invalidCountryCode", "validPhoneNumber", "invalidCountryCodeMsg"},
                {"countryCode", "invalidPhoneNumber", "unregisterPhone"}
        };

    }

    @DataProvider(name = "passwordValidationData")
    public static Object[][] passwordValidationData(){
        return new Object[][]{
                {"Test1@", false},          // < 8 chars
                {"password12@", false},     // No uppercase
                {"PASSWORD1@", false},     // No lowercase
                {"Password@", false},      // No number
                {"Password12", false},      // No special char
                {"Password@123", true}       // Fully valid
        };
    }



}
