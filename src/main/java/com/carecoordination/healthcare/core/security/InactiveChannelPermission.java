package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.UserContext;

import java.util.EnumSet;
import java.util.Set;

// we need to re-define when accessing inside menus
public class InactiveChannelPermission {


    private static final Set<UserRole> INACTIVECHANNEL_ALLOWED =
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN,
                    UserRole.MANAGER_SUPERVISOR,
                    UserRole.CLERICAL_STAFF,
                    UserRole.TRIAGE_STAFF,
                    UserRole.FIELD_CLINICIAN,
                    UserRole.EXTERNAL_VENDOR);

    private InactiveChannelPermission(){
        //Prevent instantiation
    }

    public static boolean canAccess(UserContext userContext) {
        return INACTIVECHANNEL_ALLOWED.contains(userContext.getRole());
    }
}
