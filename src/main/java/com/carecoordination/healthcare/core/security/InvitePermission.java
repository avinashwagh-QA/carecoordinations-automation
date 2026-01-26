package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;

import java.util.EnumSet;
import java.util.Set;

/**
 * Defines which roles can invite users.
 *
 * NOTE:
 * - Invite permission is NOT same as privilege
 * - Kept separate to avoid future confusion
 */

public class InvitePermission   {

    private static final Set<UserRole> INVITED_ALLOWED =
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN,
                    UserRole.MANAGER_SUPERVISOR
            );


    public static boolean canInvite(UserRole role){
        return INVITED_ALLOWED.contains(role);
    }


}
