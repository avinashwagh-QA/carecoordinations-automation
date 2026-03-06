package utilities;


import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.UserContext;
import com.carecoordination.healthcare.core.security.ManageTeamPermission;
import com.carecoordination.healthcare.model.TestUser;
import com.carecoordination.healthcare.repository.UserRepository;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;


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

    @DataProvider(name ="invalidRegisterAccountData")
    public static Object[][] invalidRegisterAccountData(){
        return new Object[][]{
                {"invalidCode", "invalidInvitedEmail", "msgInvalidEmailAndCode"},
                {"invalidCodeLessThan6digit", "validInvitedEmail", "msgInvalidCodeSize"},
                {"invalidCodeMoreThan8digit", "validInvitedEmail", "msgInvalidCodeSize"},
                {"invalidCode", "validInvitedEmail", "msgInvalidCode"},
                {"validCode", "invalidInvitedEmail", "msgInvalidEmailAndCode"},
                {"validCode", "removedUserEmail", "msgAccessDenied"},
                {"validCode", "validRegisterEmail", "msgInvalidEmailAndCode"}
        };
    }

    @DataProvider(name = "roleMatrix")
    public Object[][] roleMatrix() {
        return new Object[][] {
                { UserRole.SYSTEM_ADMIN, true },
                { UserRole.BRANCH_ADMIN, true },
                { UserRole.MANAGER_SUPERVISOR, true },
                { UserRole.CLERICAL_STAFF, true },
                { UserRole.FIELD_CLINICIAN, true },
                { UserRole.TRIAGE_STAFF, true },
                { UserRole.EXTERNAL_VENDOR, true }
        };
    }

    @DataProvider(name = "personaMatrix")
    public Object[][] personaMatrix(){
        return new Object[][]{
                {"sysadmin_multi_nonintegrated"},
                {"branchadmin_multi_nonintegrated"},
                {"managersupervisor_multi_nonintegrated"},
                {"clerical_multi_nonintegrated"},
                {"fieldclinician_multi_nonintegrated"},
                {"triagestaff_multi_nonintegrated"},
                {"externalvendor_multi_nonintegrated"}

        };
    }



//    public Object[][] inviteUserPersonas(){
//
//        List <TestUser> users = new ArrayList<>(UserRepository.getAllUser());
//
//        List<TestUser> allowedUser = users.stream()
//                .filter(user -> ManageTeamPermission.canAccessAndInvite(user)).toList();
//
//
//
//
//        };
//
//    }


}
