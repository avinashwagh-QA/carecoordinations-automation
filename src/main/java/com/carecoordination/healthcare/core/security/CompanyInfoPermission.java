package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;


public class CompanyInfoPermission {

    private CompanyInfoPermission(){
        // Prevent instantiation
    }

    public static boolean canAccess(UserRole role){
        return role == UserRole.SYSTEM_ADMIN;
    }


}
