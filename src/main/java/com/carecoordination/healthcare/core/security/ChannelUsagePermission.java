package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.context.UserContext;

import java.util.EnumSet;
import java.util.Set;

public class ChannelUsagePermission {

    private static final Set<UserRole> CHANNELUSAGE_ALLOWED =
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN
            );

    public static boolean canAccess(UserContext userContext){
        return  CHANNELUSAGE_ALLOWED.contains(userContext.getRole());

    }

}
