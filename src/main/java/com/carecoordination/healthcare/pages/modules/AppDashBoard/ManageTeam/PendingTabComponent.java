package com.carecoordination.healthcare.pages.modules.AppDashBoard.ManageTeam;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.components.TableComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class PendingTabComponent {

    private final ActionDriver actionDriver;
    private final TableComponent tableComponent;

    private final static Logger logger = LogManager.getLogger(PendingTabComponent.class);

    public PendingTabComponent(ActionDriver actionDriver) {
        this.actionDriver = actionDriver;
        tableComponent = new TableComponent(tableHeader, tableRows);
    }

    private final By tableHeader = By.xpath("//table[@id='manageteamListingsTbl']//th");
    private final By tableRows = By.xpath("//table[@id='manageteamListingsTbl']//tr");

    public boolean isUserPresent(String username) {
        boolean user = tableComponent.isRowPresent("Name & Title", username);
        logger.info("User status in the table :{}", user);
        return user;
    }




}
