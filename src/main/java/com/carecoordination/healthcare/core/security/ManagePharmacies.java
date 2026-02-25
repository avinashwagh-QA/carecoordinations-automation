package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;

import java.util.EnumSet;
import java.util.Set;

public class ManagePharmacies {

    private static final Set<UserRole> MANAGEPHARMACIES_ALLOWED =
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN,
                    UserRole.MANAGER_SUPERVISOR
            );

    private  ManagePharmacies(){
        //Prevent instantiation
    }

    public static boolean getManagePharmaciesUserAccess(UserRole role){
        return MANAGEPHARMACIES_ALLOWED.contains(role);
    }

}
