package com.carecoordination.healthcare.components;

import com.carecoordination.healthcare.actiondriver.ActionDriver;
import com.carecoordination.healthcare.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableComponent {

   private final WebDriver driver;
   private final ActionDriver actionDriver;

   private final By rowLocator;
   private final By headerLocator;

   private Map<String, Integer> columnMap;
    private static final Logger logger = LogManager.getLogger(TableComponent.class);

    public TableComponent(By headerLocator, By rowLocator){
        this.driver = DriverFactory.getDriver();
        this.actionDriver = new ActionDriver(driver);

        this.rowLocator = rowLocator;
        this.headerLocator = headerLocator;

        buildColumnMap();
    }

    //Build Header Map
    private void buildColumnMap(){

        actionDriver.waitForElementToVisible(headerLocator);
        columnMap = new HashMap<>();

        List<WebElement> headers = driver.findElements(headerLocator);

        for (int i=0;i<headers.size(); i++){

            String headerText = headers.get(i).getText().trim();
            columnMap.put(headerText, i+1);
        }
        logger.info("Table column map created: {}", columnMap);
    }

    // Validate column
    private int getColumnIndex(String columnName) {

        if (!columnMap.containsKey(columnName)) {

            throw new RuntimeException(
                    "Column not found: " + columnName +
                            ". Available columns: " + columnMap.keySet()
            );
        }

        return columnMap.get(columnName);
    }

    //Get All rows
    public List<WebElement> getRows(){
        actionDriver.waitForElementToVisible(rowLocator);
        actionDriver.waitForRowsToLoad(rowLocator);

        List<WebElement> rows =driver.findElements(rowLocator);

        logger.info("Total rows found  in table : {}", rows.size());
        return rows;
    }

    //Get rows count
    public int getRowCount(){
        int count = getRows().size();
        logger.info("Row count: {}", count);
        return count;
    }

    //Find row by column value
    public WebElement findRow(String columnName, String value) {

        int columnIndex = columnMap.get(columnName);

        for (WebElement row : getRows()) {

            String cellText = row.findElement(By.xpath("./td[" + columnIndex + "]"))
                    .getText()
                    .replace("\n", " ")
                    .trim();
            logger.info("User name for debug {}", cellText);

            if (cellText.equalsIgnoreCase(value)) {

                logger.info("Row found where {} = {}", columnName, value);
                return row;
            }
        }

        logger.warn("Row not found where {} = {}", columnName, value);
        return null;
    }

    // Get cell text
    public String getCellText(String searchColumn, String searchValue, String targetColumn) {

        WebElement row = findRow(searchColumn, searchValue);

        if (row == null) {
            throw new RuntimeException(
                    "Row not found where " + searchColumn + " = " + searchValue
            );
        }
        int tagetIndex = getColumnIndex(targetColumn);

        String cellText = row.findElement(By.xpath("./td[" + tagetIndex + "]"))
                .getText().trim();

        logger.info("Cell value for {} -> {} : {}",searchColumn,searchValue,cellText);
        return cellText;
    }

    //Click action inside row
    public void clickCell(String searchColumn, String searchValue, String targetColumn){

        WebElement row = findRow(searchColumn, searchValue);
        if (row == null) {

            throw new RuntimeException(
                    "Row not found where " + searchColumn + " = " + searchValue
            );
        }

        int columnIndex = columnMap.get(targetColumn);

        WebElement cell = row.findElement(By.xpath("./div[" + columnIndex + "]"));

        actionDriver.click(cell);
        logger.info("Clicked column '{}' in row where {} = {}",targetColumn,searchColumn,searchValue);
    }

    // Verify row exists
    public boolean isRowPresent(String columnName,String value){
        return findRow(columnName,value)!=null;
    }

    public void clickOnAction(String searchColumn, String searchValue, String actionName){

        WebElement row = findRow(searchColumn, searchValue);

        if (row == null) {

            throw new RuntimeException(
                    "Row not found where " + searchColumn + " = " + searchValue
            );
        }

        int actionColumn = columnMap.get("Actions");

        // Click action button
        WebElement action = row.findElement(By.xpath("./div[" + actionColumn + "]"));
        actionDriver.click(action);

        //click on action menu
        By actionOption =  By.xpath("//button[normalize-space()='"+actionName+"']");
        actionDriver.click(actionOption);

        logger.info("Performed '{}' action on row where {} = {}",
                actionName, searchColumn, searchValue);
    }

}
