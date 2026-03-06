package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.UserContext;

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

    public static boolean canAccess(UserContext userContext){
        return MANAGEPHARMACIES_ALLOWED.contains(userContext.getRole()) && userContext.getOrganizationContext().isNonIntegrated();
    }

}
