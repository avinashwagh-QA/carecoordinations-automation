package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;

import java.util.EnumSet;
import java.util.Set;

/**
 * InvitePermission defines who is allowed to invite users.
 *
 * IMPORTANT BUSINESS RULE:
 * - Only users who can invite users
 *   are allowed to access Manage Team
 * - Non-invite users must NOT see Manage Team
 *
 * This class represents EXPECTED behavior,
 * not application implementation.
 */

public class InvitePermission   {

    private static final Set<UserRole> INVITED_ALLOWED =
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN,
                    UserRole.MANAGER_SUPERVISOR
            );

    private InvitePermission() {
        // Prevent instantiation
    }

    public static boolean canInvite(UserRole role){
        return INVITED_ALLOWED.contains(role);
    }




}
