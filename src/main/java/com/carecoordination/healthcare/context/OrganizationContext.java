package com.carecoordination.healthcare.context;


/**
 * Holds organization-level configuration for a test run.
 *
 * IMPORTANT:
 * - This represents HOW the organization is configured
 * - It is NOT related to user roles or permissions
 *
 * Current usage:
 * - Single branch vs Multi-branch behavior
 *
 * Future usage (if needed):
 * - Organization type
 * - Feature flags
 * - Subscription plans
 */


public class OrganizationContext {

    private final boolean multiBranch;

    /**
     * @param multiBranch
     *        true  -> organization has multiple branches
     *        false -> organization has only one branch
     */

    public OrganizationContext(boolean multiBranch){
        this.multiBranch = multiBranch;
    }


    /**
     * @return true if organization has more than one branch
     */

    public boolean isMultiBranch(){
        return multiBranch;
    }

}
