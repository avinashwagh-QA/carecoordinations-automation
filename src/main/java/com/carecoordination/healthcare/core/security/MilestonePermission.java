package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;

import java.util.EnumSet;
import java.util.Set;

public class MilestonePermission {

    private static final Set<UserRole> MILESTONE_ALLOWED=
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN
            );

    private MilestonePermission(){
        //Prevent instantiation
    }

    public static boolean getManageMilestoneAllowed(UserRole role){
        return MILESTONE_ALLOWED.contains(role);
    }



}
