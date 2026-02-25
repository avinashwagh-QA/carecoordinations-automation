package model;

import com.carecoordination.healthcare.constants.UserRole;

import java.util.Set;

public class TestUser {


    private String key;
    private String email;
    private String password;
    private UserRole role;
    private String companyType;
    private String orgStructure;
    private Set<String> branches;

    public TestUser(){
    }

    public String getKey(){ return key; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }
    public String getCompanyType() { return companyType; }
    public String getOrgStructure() { return orgStructure; }
    public Set<String> getBranches() { return branches; }


}
