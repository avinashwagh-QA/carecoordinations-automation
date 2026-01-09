package com.carecoordination.healthcare.constants;

import java.util.EnumSet;
import java.util.Set;

public enum UserRole {

    SUPER_ADMIN( true),
    BRANCH_ADMIN(true),
    MANAGER_SUPERVISOR( true),
    CLERICAL_STAFF( true),
    FIELD_CLINICIAN(false),
    TRIAGE_STAFF(false),
    EXTERNAL_VENDOR(false);

    private final boolean isPrivileged;

    UserRole(boolean isPrivileged){
        this.isPrivileged = isPrivileged;
    }

    public boolean isPrivileged() {
        return isPrivileged;
    }

    /* ---------- ROLE GROUPS ---------- */

    public static Set<UserRole> privileged(){
        return EnumSet.of(  SUPER_ADMIN,
                BRANCH_ADMIN,
                MANAGER_SUPERVISOR,
                CLERICAL_STAFF);
    }

    public static Set<UserRole> nonPrivileged(){
        return EnumSet.of(FIELD_CLINICIAN,
                TRIAGE_STAFF,
        EXTERNAL_VENDOR);
    }


}

