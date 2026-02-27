package com.carecoordination.healthcare.context;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.core.security.*;

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
        return RolePrivilege.isPrivileged(this);
    }

    //true if user is allowed to invite other users
    public boolean canInviteUser(){
        return ManageTeamPermission.canAccessAndInvite(this);
    }

    public boolean canAccessManageTeam() {
        return ManageTeamPermission.canAccessAndInvite(this);
    }

    public boolean canAccessChannelUsage(){
        return ChannelUsagePermission.canAccess(this);
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
        return CompanyInfoPermission.canAccess(this);
    }

    //Branch info
    public boolean canAccessBranchInfo(){
        return BranchInfoPermission.canAccess(this);
    }

    //Inactive channel
    public boolean canAccessInactiveChannel(){
        return InactiveChannelPermission.canAccess(this);
    }

    //Manage Milestone
    public boolean canAccessManageMilestone(){
        return MilestonePermission.canAccess(this);
    }

    //Manage physician
    public boolean canAccessManagePhysician(){
        return ManagePhysician.canAccess(this);
    }

    //Manage pharmacies
    public boolean canAccessManagePharmacies(){
        return ManagePharmacies.canAccess(this);
    }

}
