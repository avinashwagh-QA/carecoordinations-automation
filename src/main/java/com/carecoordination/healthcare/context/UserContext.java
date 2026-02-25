package com.carecoordination.healthcare.context;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.core.security.ChannelUsagePermission;
import com.carecoordination.healthcare.core.security.CompanyInfoPermission;
import com.carecoordination.healthcare.core.security.InvitePermission;
import com.carecoordination.healthcare.core.security.RolePrivilege;

public class UserContext {

    private final UserRole role;
    private final OrganizationContext organizationContext;

    public UserContext(UserRole role, OrganizationContext organizationContext) {
        this.role = role;
        this.organizationContext = organizationContext;
    }

    //@return role of the logged-in user
    public UserRole getRole() {
        return role;
    }

    // @return organization context for this user session
    public OrganizationContext getOrganizationContext() {
        return organizationContext;
    }

    //@return true if user is considered privileged
    public boolean isPrivileged(){
        return RolePrivilege.isPrivileged(role);
    }

    //true if user is allowed to invite other users
    public boolean canInviteUser(){
        return InvitePermission.canInvite(role);
    }

    // true if organization has multiple branches
    public boolean isMultiBranchOrganization(){
        return organizationContext.isMultiBranch();
    }

    public boolean canAccessManageTeam() {
        return InvitePermission.canInvite(role);
    }

    public boolean canAccessChannelUsage(){
        return ChannelUsagePermission.canAccessChannelUsage(role);
    }

    // Alerts and Availability can be access to all users
    public boolean canAccessAlerts(){
        return true;
    }
    public boolean canAccessAvailability(){
        return true;
    }

    //Company info permission
    public boolean canAccessCompanyInfo(){
        return CompanyInfoPermission.canAccess(role);
    }

}
