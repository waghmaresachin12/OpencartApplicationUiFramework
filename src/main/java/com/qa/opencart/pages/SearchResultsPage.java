package com.qa.opencart.pages;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage {

    // Page class/Page Library/Page Object
    private WebDriver driver;
    private ElementUtil eleUtil;

    // 1. Private By Locators

    private By searchProducts = By.cssSelector("div.product-thumb");

    // 2. Public Page Class Const...
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    @Step("getting count of products as per search")
    public int getSearchProductCount() {
        return eleUtil.waitForElementsVisible(searchProducts, TimeUtil.DEFAULT_LONG_TIME).size();
    }

    @Step("select the product {0}")
    public ProductInfoPage selectProduct(String productName){
        System.out.println("searching for product: "+productName);
        eleUtil.waitForElementVisible(By.linkText(productName), TimeUtil.DEFAULT_LONG_TIME).click();
        return new ProductInfoPage(driver);
    }

}
