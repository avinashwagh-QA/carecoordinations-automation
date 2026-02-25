package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;

import java.util.EnumSet;
import java.util.Set;

public class BranchInfoPermission {


    private static final Set<UserRole> BRANCHINFO_ALLOWED=
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN
            );

    private BranchInfoPermission(){
        // prevent Instantiation
    }

    public static boolean canAccessBranchInfo(UserRole role){
        return BRANCHINFO_ALLOWED.contains(role);
    }

}
