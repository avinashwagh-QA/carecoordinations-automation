package com.carecoordination.healthcare.factory;

import com.carecoordination.healthcare.constants.UserRole;
import com.carecoordination.healthcare.model.InviteUserData;
import com.carecoordination.healthcare.utilities.Fakerutil;

public class InviteUserFactory {


    public InviteUserData createUser(UserRole role){

        String firstName = Fakerutil.getFirstName();
        String lastName = Fakerutil.getLastName();

        String email = Fakerutil.getEmail(firstName,role.getDisplayName());
        String phone = Fakerutil.getPhone();

        return new InviteUserData(
                role.getDisplayName(),
                null,
                firstName,
                lastName,
                email,
                phone);

    }

}
