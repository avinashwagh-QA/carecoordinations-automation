package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.UserContext;


public class CompanyInfoPermission {

    private CompanyInfoPermission(){
        // Prevent instantiation
    }

    public static boolean canAccess(UserContext userContext){
        return userContext.getRole() == UserRole.SYSTEM_ADMIN;
    }


}
