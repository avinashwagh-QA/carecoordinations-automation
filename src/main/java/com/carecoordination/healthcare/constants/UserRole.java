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

    SYSTEM_ADMIN,
    BRANCH_ADMIN,
    MANAGER_SUPERVISOR,
    CLERICAL_STAFF,
    FIELD_CLINICIAN,
    TRIAGE_STAFF,
    EXTERNAL_VENDOR;

}

