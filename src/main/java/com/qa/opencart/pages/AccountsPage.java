package com.qa.opencart.pages;

import com.qa.opencart.contants.AppContants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountsPage {

    // page class/ page Library / page object
    private WebDriver driver;
    private ElementUtil eleUtil;

    // 1. Private by locators
    private By myAccountink = By.linkText("My Account");
    private By logoutLink = By.linkText("Logout");
    private By headers = By.cssSelector("div#content h2");
    private By seach = By.name("search");
    private By searchIcon = By.cssSelector("div#search button");

    // 2. public page class constructor
    public AccountsPage(WebDriver driver){
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    // 3. Public page actions/methods
    public String getAccountPageTitle(){
        String title = eleUtil.waitForTitleIs(AppContants.ACCOUNTS_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
        System.out.println("Acc page title is : " + title);
        return title;
    }

    public String getAccountPageUrl(){
        String url = eleUtil.waitForURLContains(AppContants.ACC_PAGE_URL_FRACTION, TimeUtil.DEFAULT_MEDIUM_TIME);
        System.out.println("Acc page url is : " + url);
        return url;
    }

    public boolean isLogoutLinkExist(){
        return eleUtil.waitForElementVisible(logoutLink, TimeUtil.DEFAULT_LONG_TIME).isDisplayed();
    }

    public boolean myAccountLinkExist(){
        return eleUtil.waitForElementVisible(myAccountink, TimeUtil.DEFAULT_LONG_TIME).isDisplayed();
    }

    public List<String> getAccountsPageHeaders(){
        List<WebElement> headerEleList = eleUtil.getElements(headers);
        List<String> headersList = new ArrayList<String>();
        for (WebElement e : headerEleList){
            String header = e.getText();
            headersList.add(header);
        }
        return headersList;
    }

    public SearchResultsPage doSearch(String searchKey){
        System.out.println("Searching for  : " + searchKey);
        eleUtil.doSendKeys(seach, searchKey);
        eleUtil.doClick(searchIcon);
        return new SearchResultsPage(driver);
    }
}

