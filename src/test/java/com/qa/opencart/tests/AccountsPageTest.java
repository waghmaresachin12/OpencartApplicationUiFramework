package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTests;
import com.qa.opencart.contants.AppContants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


public class AccountsPageTest extends BaseTests {

    @BeforeClass
    public void accSetup(){
        //we are using directly accountPage variable of object which is created in BaseTest and we are inherit that
       accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test
    public void accPageTitleTest(){
        String actTitle = accountsPage.getAccountPageTitle();
        Assert.assertEquals(actTitle, AppContants.ACCOUNTS_PAGE_TITLE);
    }

    @Test
    public void accPageUrlTest(){
        String actUrl = accountsPage.getAccountPageUrl();
        Assert.assertTrue(actUrl.contains(AppContants.ACC_PAGE_URL_FRACTION));
    }

    @Test
    public void isLogoutLinkExistTest(){
        Assert.assertTrue(accountsPage.isLogoutLinkExist());
    }

    @Test
    public void isMyAccLinkExistTest(){
        Assert.assertTrue(accountsPage.myAccountLinkExist());
    }

    @Test
    public void accPageHeadersTest(){
        List<String> actHeadersList = accountsPage.getAccountsPageHeaders();
        System.out.println(actHeadersList);
    }

    @Test
    public void searchTest(){
        accountsPage.doSearch("macbook");
    }
}
