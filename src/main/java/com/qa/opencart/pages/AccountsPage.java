package com.qa.opencart.pages;

import com.qa.opencart.contants.AppContants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;
import io.qameta.allure.Step;
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
    @Step("getting account page title....")
    public String getAccountPageTitle(){
        String title = eleUtil.waitForTitleIs(AppContants.ACCOUNTS_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
        System.out.println("Acc page title is : " + title);
        return title;
    }

    @Step("getting account page URL....")
    public String getAccountPageUrl(){
        String url = eleUtil.waitForURLContains(AppContants.ACC_PAGE_URL_FRACTION, TimeUtil.DEFAULT_MEDIUM_TIME);
        System.out.println("Acc page url is : " + url);
        return url;
    }

    @Step("getting the state of logout link....")
    public boolean isLogoutLinkExist(){
        return eleUtil.waitForElementVisible(logoutLink, TimeUtil.DEFAULT_LONG_TIME).isDisplayed();
    }

    @Step("getting the state of My Account link....")
    public boolean myAccountLinkExist(){
        return eleUtil.waitForElementVisible(myAccountink, TimeUtil.DEFAULT_LONG_TIME).isDisplayed();
    }

    @Step("getting all headers of the account page...")
    public List<String> getAccountsPageHeaders(){
        List<WebElement> headerEleList = eleUtil.getElements(headers);
        List<String> headersList = new ArrayList<String>();
        for (WebElement e : headerEleList){
            String header = e.getText();
            headersList.add(header);
        }
        return headersList;
    }

    @Step("Searching product {0} in Search box on Account page...")
    public SearchResultsPage doSearch(String searchKey){
        System.out.println("Searching for  : " + searchKey);
        eleUtil.doSendKeys(seach, searchKey);
        eleUtil.doClick(searchIcon);
        return new SearchResultsPage(driver);
    }
}

