package com.carecoordination.healthcare.context;


import com.carecoordination.healthcare.constants.CompanyType;

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

    private final boolean isMultiBranch;
    private final CompanyType companyType;

    /**
     * @param isMultiBranch
     *        true  -> organization has multiple branches
     *        false -> organization has only one branch
     * @param companyType
     *  Integrated - Type or Non-Integrated type of company
     */

    public OrganizationContext(boolean isMultiBranch, CompanyType companyType){

        this.isMultiBranch = isMultiBranch;
        this.companyType = companyType;
    }


    /**
     * @return true if organization has more than one branch
     */
    public boolean isMultiBranch(){
        return isMultiBranch();
    }

    public CompanyType getCompanyType(){
        return companyType;
    }

    public boolean isIntegrated(){
        return companyType == CompanyType.INTEGRATED;
    }

    public boolean isNonIntegrated(){
        return companyType == CompanyType.NONINTEGRATED;
    }












}
