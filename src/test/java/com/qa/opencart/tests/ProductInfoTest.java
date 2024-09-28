package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTests;
import com.qa.opencart.contants.AppContants;
import com.qa.opencart.utils.ExcelUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("Epic 105: Design open cart product info page")
@Story("US 106: Design product info page features for open cart application")
@Feature("Feature 107: Adding product info features")
public class ProductInfoTest extends BaseTests {

    //AAA - AAA is a pattern, Arrange (proper flow), Act, Assert
    //TC - only one hard assertion or can have multiple soft assertions
    @Description("Checking user is able to login....")
    @Severity(SeverityLevel.BLOCKER)
    @BeforeClass
    public void infoPageSetup(){
        //we are using directly accountPage variable of object which is created in BaseTest and we are inherit that
        accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    //Whenever we have to supply the data then we can use DataProvider
    @DataProvider
    public Object[][] getProductSearchData(){
        return new Object[][]{
                {"macbook", "MacBook Pro"},
                {"imac", "iMac"},
                {"samsung", "Samsung SyncMaster 941BW"},
                {"samsung", "Samsung Galaxy Tab 10.1"}
        };
    }

    @Description("Checking name of searched product on product details {1} page....")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "getProductSearchData")
    public void productHeaderTest(String searchKey, String productName){
        searchResultsPage = accountsPage.doSearch(searchKey);
        productInfoPage = searchResultsPage.selectProduct(productName);
        Assert.assertEquals(productInfoPage.getProductHeader(), productName);
    }

    //This is called parameterization using Dataprovider and test mutiple data
    @DataProvider
    public Object[][] getProductImagesData(){
        return new Object[][]{
                {"macbook", "MacBook Pro", 4},
                {"imac", "iMac", 3},
                {"samsung", "Samsung SyncMaster 941BW", 1},
                {"samsung", "Samsung Galaxy Tab 10.1", 7}
        };
    }

    @DataProvider
    public Object[][] getProductImagesDataFromExcel(){
        return ExcelUtil.getTestData(AppContants.PRODUCT_SHEET_NAME);
    }

    @Description("Checking count of product images on product details {1} page....")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "getProductImagesDataFromExcel")
    public void productImagesCountTest(String searchKey, String productName, String imagesCount){
        searchResultsPage = accountsPage.doSearch(searchKey);
        productInfoPage = searchResultsPage.selectProduct(productName);
        Assert.assertEquals(productInfoPage.getProductImagesCount(), Integer.parseInt(imagesCount));
    }

    @Description("Checking complete details of searched product....")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void productInfoTest(){
        searchResultsPage = accountsPage.doSearch("macbook");
        productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
        Map<String, String> productActDetailsMap = productInfoPage.getProductDetailsMap();
        softAssert.assertEquals(productActDetailsMap.get("Brand"), "Apple");
        softAssert.assertEquals(productActDetailsMap.get("Product Code"), "Product 18");
        softAssert.assertEquals(productActDetailsMap.get("Availability"), "In Stock");
        softAssert.assertEquals(productActDetailsMap.get("productprice"), "$2,000.00");
        softAssert.assertEquals(productActDetailsMap.get("exTaxPrice"), "$2,000.00");

        softAssert.assertAll();
        //assertAll method from softAssert tells that from above all assertion what all assertion got failed

    }

}
