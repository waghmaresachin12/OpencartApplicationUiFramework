package com.qa.opencart.pages;

import com.qa.opencart.contants.AppContants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    // page class/ page Library / page object
    private WebDriver driver;
    private ElementUtil eleUtil;

    // 1. Private by locators
    private By emailID = By.id("input-email");
    private By password = By.id("input-password");
    private By loginButton = By.xpath("//input[@value='Login']");
    private By forgotPwdLink = By.linkText("Forgotten Password");
    private By registerLink = By.linkText("Register");

    // 2. public page class constructor
    public LoginPage(WebDriver driver){
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    // 3. Public page actions/methods
    @Step("getting login page title....")
    //Above @Step line is from Allure report
    public String getLoginPageTitle(){
        String title = eleUtil.waitForTitleIs(AppContants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
        System.out.println("Login page title is : " + title);
        return title;
    }

    @Step("getting login page url....")
    public String getLoginPageUrl(){
        String url = eleUtil.waitForURLContains(AppContants.LOGIN_PAGE_URL_FRACTION, TimeUtil.DEFAULT_MEDIUM_TIME);
        System.out.println("Login page url is : " + url);
        return url;
    }

    @Step("getting the state of forgot password link....")
    public boolean isForgotPasswordLinkExist(){
        return eleUtil.isElementDisplayed(forgotPwdLink);
    }

    @Step("login with username {0} and password {1}")
    public AccountsPage doLogin(String username, String pwd){
        eleUtil.waitForElementVisible(emailID, TimeUtil.DEFAULT_LONG_TIME).sendKeys(username);
        eleUtil.doSendKeys(password, pwd);
        eleUtil.doClick(loginButton);
        //we need to follow this rule like
        //If this page redirects to other page then this method will return that page object
        return new AccountsPage(driver);
    }

    @Step("navigating to the registration page...")
    public RegistrationPage navigateToRegistrationPage(){
        eleUtil.waitForElementVisible(registerLink, TimeUtil.DEFAULT_LONG_TIME).click();
        return new RegistrationPage(driver);
    }
}
