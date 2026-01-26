package com.carecoordination.healthcare.core.security;

import com.carecoordination.healthcare.constants.UserRole;

import java.util.EnumSet;
import java.util.Set;

/**
 * Central place to define which roles are considered privileged.
 *
 * Why this exists:
 * - Privilege rules can change
 * - Keeps UserRole enum clean
 * - Used by menu visibility & access checks
 */


public class RolePrivilege {

    private static final Set<UserRole> PRIVILEGED_ROLES =
            EnumSet.of(
                    UserRole.SYSTEM_ADMIN,
                    UserRole.BRANCH_ADMIN,
                    UserRole.MANAGER_SUPERVISOR,
                    UserRole.CLERICAL_STAFF
            );

    public static boolean isPrivileged(UserRole role){
        return PRIVILEGED_ROLES.contains(role);
    }


}
