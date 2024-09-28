package com.qa.opencart.pages;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoPage {
    // Page class/Page Library/Page Object
    private WebDriver driver;
    private ElementUtil eleUtil;
    //If you want to create Hashmap then also create that private
    //Also if you use 'LinkedHashMap' then this will follow order & normal HashMap won't follow order to store
    private Map<String, String> productMap = new HashMap<>();

    // 1. Private By Locators

    private By productHeader = By.tagName("h1");
    private By images = By.cssSelector("ul.thumbnails img");
    private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
    private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

    // 2. Public Page Class Const...
    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    @Step("getting product headers....")
    public String getProductHeader() {
        String header = eleUtil.doGetElementText(productHeader);
        System.out.println(header);
        return header;
    }

    @Step("getting product images count....")
    public int getProductImagesCount() {
        int totalImages = eleUtil.waitForElementsVisible(images, TimeUtil.DEFAULT_LONG_TIME).size();
        System.out.println("Image count for" + getProductHeader() + " : " + totalImages);
        return totalImages;
    }


//    Brand: Apple
//    Product Code: Product 18
//    Reward Points: 800
//    Availability: In Stock
    @Step("getting product meta data (details)....")
    private void getProductMetaData(){
        List<WebElement> metaList = eleUtil.getElements(productMetaData);
        for(WebElement e : metaList){
            String text = e.getText();
            String metakey = text.split(":")[0].trim();
            String metavalue = text.split(":")[1].trim();
            productMap.put(metakey, metavalue);
        }
    }

//    $2,000.00
//    Ex Tax: $2,000.00
    @Step("getting product price data....")
    private void getProductPriceData(){
        List<WebElement> priceList = eleUtil.getElements(productPriceData);
        String price = priceList.get(0).getText();
        String exTaxPrice = priceList.get(1).getText().split(":")[1].trim();
        productMap.put("productprice", price);
        productMap.put("exTaxPrice", exTaxPrice);
    }

    @Step("Saving product details header and imagesCount in map....")
    public Map<String, String> getProductDetailsMap(){
        productMap.put("header", getProductHeader());
        productMap.put("productimages", String.valueOf(getProductImagesCount()));
        getProductMetaData();
        getProductPriceData();
        return productMap;
    }
}
