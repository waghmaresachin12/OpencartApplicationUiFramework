package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTests;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchResultsTest extends BaseTests {

    @Description("Checking user is able to login....")
    @Severity(SeverityLevel.BLOCKER)
    @BeforeClass
    public void accSetup(){
        //we are using directly accountPage variable of object which is created in BaseTest and we are inherit that
        accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @DataProvider
    //Whenever we have to supply the data then we can use DataProvider
    public Object[][] getProductCountData(){
        return new Object[][]{
            {"macbook", 3},
            {"imac", 1},
            {"samsung", 2}
        };
    }

    @Description("Checking count of product {0}....")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "getProductCountData")
    public void searchResultsTest(String searchKey, int productCount){
        searchResultsPage = accountsPage.doSearch(searchKey);
        Assert.assertEquals(searchResultsPage.getSearchProductCount(),productCount);
    }
}
