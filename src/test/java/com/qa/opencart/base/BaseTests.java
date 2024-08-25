package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.util.Properties;

public class BaseTests {
    //Configuration level code we are writing here like initProp() and then passing it to initDriver for browser and other details
    //So this driver and prop and driver factory will be given to child class and also in same session

    WebDriver driver;
    DriverFactory df;
    protected Properties prop;
    protected LoginPage loginPage;
    protected AccountsPage accountsPage;
    protected SearchResultsPage searchResultsPage;
    protected ProductInfoPage productInfoPage;
    protected RegistrationPage registrationPage;
    protected SoftAssert softAssert;

    @Step("Setup: launching {0} browser & init properties")
    @Parameters({"browser"})
    @BeforeTest
    //in below line we are getting broswer name from xml file as a parameter
    public void setup(String browserName){
        df = new DriverFactory();
        //Below line will have the complete properties for Properties file in Prop object
        prop = df.initProp();
        //As shown in below line, Pls provide complete prop file object to intiDriver so that whatever we required that we can take it
        //Do not pass browser/username or other properties seperately from here

        if (browserName != null){
            //Below line will not update actual value in Properties file but in runtime memory it will get change
            //Below line is to set browser as given in xml file but it won't actually change in prop file
            prop.setProperty("browser", browserName);
        }

        driver = df.initDriver(prop);

        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();

    }

    @Step("closing browser")
    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
