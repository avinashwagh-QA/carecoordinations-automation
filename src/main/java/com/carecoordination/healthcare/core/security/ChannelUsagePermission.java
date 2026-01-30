package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;

import java.util.EnumSet;
import java.util.Set;

public class ChannelUsagePermission {

    private static final Set<UserRole> CHANNELUSAGE_ALLOWED =
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN
            );


    public static boolean canAccessChannelUsage(UserRole role){
        return  CHANNELUSAGE_ALLOWED.contains(role);

    }

}
