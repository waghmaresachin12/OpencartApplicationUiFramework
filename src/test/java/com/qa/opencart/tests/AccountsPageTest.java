package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTests;
import com.qa.opencart.contants.AppContants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Epic 102 : Design my account of opencart application page")
@Story("US 103 : Design Account page features for open cart application")
@Feature("Feature 104 : Adding Account page features")
public class AccountsPageTest extends BaseTests {

    @Description("Checking user is able to login....")
    @Severity(SeverityLevel.BLOCKER)
    @BeforeClass
    public void accSetup(){
        //we are using directly accountPage variable of object which is created in BaseTest and we are inherit that
       accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Description("Checking account page title....")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void accPageTitleTest(){
        String actTitle = accountsPage.getAccountPageTitle();
        Assert.assertEquals(actTitle, AppContants.ACCOUNTS_PAGE_TITLE);
    }

    @Description("Checking account page URL....")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void accPageUrlTest(){
        String actUrl = accountsPage.getAccountPageUrl();
        Assert.assertTrue(actUrl.contains(AppContants.ACC_PAGE_URL_FRACTION));
    }

    @Description("Checking Logout link on account page....")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void isLogoutLinkExistTest(){
        Assert.assertTrue(accountsPage.isLogoutLinkExist());
    }

    @Description("Checking My Account link on account page....")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void isMyAccLinkExistTest(){
        Assert.assertTrue(accountsPage.myAccountLinkExist());
    }

    @Description("Checking headers of account page....")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void accPageHeadersTest(){
        List<String> actHeadersList = accountsPage.getAccountsPageHeaders();
        System.out.println(actHeadersList);
    }

    @Description("Checking search of product on account page....")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void searchTest(){
        accountsPage.doSearch("macbook");
    }
}
