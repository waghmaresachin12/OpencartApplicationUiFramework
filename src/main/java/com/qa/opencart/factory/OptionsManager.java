package com.qa.opencart.factory;

import com.qa.opencart.logger.Log;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OptionsManager {

    //OptionsManager is required for Headless and incognito, to get value from prop file and save it to respective browser option manager

    private Properties prop;
    private ChromeOptions co;
    private FirefoxOptions fo;
    private EdgeOptions eo;

    public OptionsManager(Properties prop){
        this.prop = prop;

    }

    //Do not create Static method bcz it will create problem in parallel run execution in OptionManager

    public ChromeOptions getChromeOptions(){
        co = new ChromeOptions();

        if (Boolean.parseBoolean(prop.getProperty("remote").trim())){
            co.setCapability("browserName", "chrome");
            co.setBrowserVersion(prop.getProperty("browserversion").trim());
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("screenResolution", "1280x1024x24");
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("name", prop.getProperty("testname"));
            co.setCapability("selenoid:options", selenoidOptions);
        }

        if (Boolean.parseBoolean(prop.getProperty("headless").trim())){
//            System.out.println("Running chrome in headless mode");
            Log.info("Running chrome in headless mode");
            co.addArguments("--headless");
        }

        if (Boolean.parseBoolean(prop.getProperty("incognito").trim())){
            Log.info("Running chrome in incognito mode");
            co.addArguments("--incognito");
        }

        return co;
    }

    public FirefoxOptions getFirefoxOptions(){
        fo = new FirefoxOptions();
        if (Boolean.parseBoolean(prop.getProperty("remote").trim())){
            fo.setCapability("browserName", "firefox");
            fo.setBrowserVersion(prop.getProperty("browserversion").trim());

            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("screenResolution", "1280x1024x24");
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("name", prop.getProperty("testname"));
            fo.setCapability("selenoid:options", selenoidOptions);
        }

        if (Boolean.parseBoolean(prop.getProperty("headless").trim())){
            System.out.println("Running firefox in headless mode");
            fo.addArguments("--headless");
        }

        if (Boolean.parseBoolean(prop.getProperty("incognito").trim())){
            fo.addArguments("--incognito");
        }

        return fo;
    }

    public EdgeOptions getEdgeOptions(){
        eo = new EdgeOptions();
        if (Boolean.parseBoolean(prop.getProperty("remote"))) {
            eo.setCapability("browserName", "edge");
            eo.setCapability("platform", Platform.LINUX);
        }

        if (Boolean.parseBoolean(prop.getProperty("headless").trim())){
            System.out.println("Running edge in headless mode");
            eo.addArguments("--headless");
        }

        if (Boolean.parseBoolean(prop.getProperty("incognito").trim())){
            eo.addArguments("--incognito");
        }

        return eo;
    }

}
