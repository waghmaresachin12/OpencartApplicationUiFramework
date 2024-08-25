package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTests;
import com.qa.opencart.contants.AppContants;
import com.qa.opencart.errors.AppErrors;
import com.qa.opencart.listeners.ExtentReportListener;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


//@Listeners(ExtentReportListener.class)
@Epic("Epic 100: Design open cart login page")
@Story("US 101: Design login page features for open cart application")
@Feature("Feature 201: Adding login features")
public class LoginPageTest extends BaseTests {

    @Description("Checking login page title....")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 1)
    public void loginPageTitleTest(){
        String actualTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actualTitle, AppContants.LOGIN_PAGE_TITLE, AppErrors.TITLE__NOT_FOUND);
    }

    @Description("Checking login page URL....")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 2)
    public void loginPageURLTest(){
        String actualUrl = loginPage.getLoginPageUrl();
        Assert.assertTrue(actualUrl.contains(AppContants.LOGIN_PAGE_URL_FRACTION), AppErrors.URL_NOT_FOUND);
    }

    @Description("Checking forgot pwd link on login page....")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void forgotPwdLinkExistText(){
        Assert.assertTrue(loginPage.isForgotPasswordLinkExist());
    }

    @Description("Checking user is able to login....")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 4)
    public void loginTest(){
         accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        Assert.assertEquals(accountsPage.getAccountPageTitle(), "My Account");
    }
}
