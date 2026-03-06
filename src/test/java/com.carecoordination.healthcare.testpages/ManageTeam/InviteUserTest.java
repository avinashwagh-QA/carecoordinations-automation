package com.carecoordination.healthcare.testpages.ManageTeam;

import com.carecoordination.healthcare.factory.BaseTest;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam.InviteUserModal;
import com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam.ManageTeamPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

public class InviteUserTest extends BaseTest {

    private ManageTeamPage manageTeamPage;
    private InviteUserModal inviteUserModal;

    private static final Logger logger = LogManager.getLogger(InviteUserTest.class);

    @BeforeMethod
    public void setUpPages() {
        manageTeamPage = new ManageTeamPage(actionDriver);
        inviteUserModal = new InviteUserModal(actionDriver);
    }


    public void verifyInviteButtonIsDisbale(){

    }







}
