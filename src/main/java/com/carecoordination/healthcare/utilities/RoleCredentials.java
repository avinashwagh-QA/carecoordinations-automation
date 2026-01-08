package com.carecoordination.healthcare.utilities;

import com.carecoordination.healthcare.constants.UserRole;


public class RoleCredentials {

    public static String getEmail(UserRole role) {
        return switch (role) {
            case SUPER_ADMIN -> ConfigReader.getProperty("superadmin.email");
            case BRANCH_ADMIN -> ConfigReader.getProperty("branchadmin.email");
            case MANAGER_SUPERVISOR -> ConfigReader.getProperty("managerSupervisor.email");
            case CLERICAL_STAFF -> ConfigReader.getProperty("clerical.email");
            case FIELD_CLINICIAN -> ConfigReader.getProperty("fieldclinician.email");
            case TRIAGE_STAFF -> ConfigReader.getProperty("triagestaff.email");
        };
    }

    public static String getPassword(UserRole role) {
        return switch (role) {
            case SUPER_ADMIN -> ConfigReader.getProperty("superadmin.password");
            case BRANCH_ADMIN -> ConfigReader.getProperty("branchadmin.password");
            case MANAGER_SUPERVISOR -> ConfigReader.getProperty("managerSupervisor.password");
            case CLERICAL_STAFF -> ConfigReader.getProperty("clerical.password");
            case FIELD_CLINICIAN -> ConfigReader.getProperty("fieldclinician.password");
            case TRIAGE_STAFF -> ConfigReader.getProperty("triagestaff.password");
        };
    }



}
