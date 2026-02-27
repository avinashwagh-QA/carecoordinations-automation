package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.UserContext;

import java.util.EnumSet;
import java.util.Set;

public class ManagePhysician {

    private static final Set<UserRole> MANAGEPHYSCIAN_ALLOWED =
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN,
                    UserRole.MANAGER_SUPERVISOR
            );

    private ManagePhysician(){
        //Prevent instantiation
    }

    public static boolean canAccess(UserContext userContext) {
        return MANAGEPHYSCIAN_ALLOWED.contains(userContext.getRole()) && userContext.getOrganizationContext().isNonIntegrated();
    }

}
