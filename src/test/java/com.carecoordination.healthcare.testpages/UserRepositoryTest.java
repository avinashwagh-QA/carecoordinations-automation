package com.carecoordination.healthcare.testpages;

import com.carecoordination.healthcare.model.TestUser;
import org.testng.annotations.Test;
import com.carecoordination.healthcare.repository.UserRepository;

public class UserRepositoryTest {

    @Test
    public void testLoadUser() {

        TestUser user =
                UserRepository.getUser("sysadmin_multi_nonintegrated");

        System.out.println(user.getEmail());
        System.out.println(user.getRole());
    }
    
}
