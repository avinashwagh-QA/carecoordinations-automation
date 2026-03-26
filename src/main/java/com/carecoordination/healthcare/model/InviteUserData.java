package com.carecoordination.healthcare.model;

public class InviteUserData {

    private final String role;
    private final String branchName;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;


    public InviteUserData (String role, String branchName, String firstName,
                            String lastName, String email, String phoneNumber){

        this.role= role;
        this.branchName = branchName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.phoneNumber = phoneNumber;

    }

    public String getRole() {
        return role;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDisplayedName(){
        String firstInitial = firstName.substring(0,1).toUpperCase();
        String lastInitial = lastName.substring(0,1).toUpperCase();

        return firstInitial + lastInitial + " " +firstName +" "+ lastName;


    }




}
