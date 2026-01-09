package com.carecoordination.healthcare.utilities;

import com.carecoordination.healthcare.constants.UserRole;

public class RoleUserMapper {

    public static String expectedUserName(UserRole role) {
        return switch (role) {
            case SUPER_ADMIN -> "Max M Thompson";
            case BRANCH_ADMIN -> "Ana wagner";
            case MANAGER_SUPERVISOR -> "Joy Alexander";
            case CLERICAL_STAFF -> "Addison Perry";
            case FIELD_CLINICIAN -> "Ishan s Mehta";
            case TRIAGE_STAFF -> "Ellen Rogers";
            case EXTERNAL_VENDOR -> "Jhony A Walker";
        };
    }


}
