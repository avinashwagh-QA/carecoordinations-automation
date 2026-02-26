package com.carecoordination.healthcare.model;

import java.util.List;

public class TestUserConfig {


    private List<TestUser> users;

    public TestUserConfig(){}

    public List<TestUser> getUsers(){
        return users;
    }

    public void setUsers(List<TestUser> users){
        this.users = users;
    }

}
