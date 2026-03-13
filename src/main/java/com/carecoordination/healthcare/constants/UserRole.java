package com.carecoordination.healthcare.constants;



/**
 * Represents all user roles supported by the Care Coordination system.
 *
 * IMPORTANT:
 * - This enum represents role identity only
 * - Do NOT add permission or UI logic here
 * - Business rules must live outside this enum
 *
 *
 * Used by:
 * - UserContext
 * - LoginService
 * - Permission & privilege rules
 */


public enum UserRole {

    SYSTEM_ADMIN("System Admin"),
    BRANCH_ADMIN("Branch Admin"),
    MANAGER_SUPERVISOR("Manager/Supervisor"),
    CLERICAL_STAFF("Clerical Staff"),
    FIELD_CLINICIAN("Field Clinician/Staff"),
    TRIAGE_STAFF("Triage Staff"),
    EXTERNAL_VENDOR("External Vendor/Partner");

    private final String displayName;

    UserRole(String displayName) {
    this.displayName=displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}

