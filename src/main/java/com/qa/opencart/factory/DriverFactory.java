package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppErrors;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.logger.Log;

public class DriverFactory {

    WebDriver driver;
    Properties prop;
    OptionsManager optionsManager;

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

    public static String highlight;

    public WebDriver initDriver(Properties prop){

        String browserName = prop.getProperty("browser");
//        System.out.print("Browser name is : "+ browserName);

        Log.info("Browser name is : "+ browserName);

        //If you want to get browsername from mvn cmd line the use below line and make comment String browserName = prop.getProperty("browser");
        //but below line will not be suggested as we will not able to achieve parallel execution
        //System.getProperty("browser");

        //While initDriver, we are taking value of highlight and storing to variable which is static in nature
         highlight = prop.getProperty("highlight");

        //optionsManager object to get values of optionManager class
        optionsManager = new OptionsManager(prop);

        switch (browserName.trim().toLowerCase()){
            case "chrome":
                //optionsManager will give respective browser values to driver
                //driver = new ChromeDriver(optionsManager.getChromeOptions());

                if (Boolean.parseBoolean(prop.getProperty("remote"))){
                    //remote - grid execution
                    init_remoteDriver("chrome");
                }else{
                    //else run it on local
                    tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions())); //tlDriver value set and in getter we will get this
                }
                break;

            case "firefox":
                if (Boolean.parseBoolean(prop.getProperty("remote"))){
                    //remote - grid execution
                    init_remoteDriver("firefox");
                }else{
                    //driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
                    tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
                }
                break;

            case "edge":
                if (Boolean.parseBoolean(prop.getProperty("remote"))){
                    //remote - grid execution
                    init_remoteDriver("edge");
                }else {
                    //driver = new EdgeDriver(optionsManager.getEdgeOptions());
                    tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
                }
                break;

            case "safari":
                //driver = new SafariDriver();
                tlDriver.set(new SafariDriver());
                break;

            default:
//                System.out.println("plz enter the right browser name");
                Log.error("plz enter the right browser name");
                throw new BrowserException("No Browser Found... "+ browserName);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("url"));

        return getDriver();
    }

    //To run test on selenium grid & call this method in init_Driver()
    private void init_remoteDriver(String browserName) {
        System.out.println("Running tests on Remote GRID on browser: "+ browserName);
        try {
            switch (browserName.toLowerCase().trim()) {
                case "chrome":
                    tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
                    break;
                case "edge":
                    tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
                    break;
                case "firefox":
                    tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
                    break;
                default:
                    System.out.println("Pls pass the right supported browser on GRID....");
                    break;
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver(){
        return tlDriver.get();
    }

    public Properties initProp(){

        //envName = qa, stage, prod, uat, dev
        //mvn clean install -Denv="qa"   (Here -D tell that your passing evn variable using mvn cmd line)

        FileInputStream ip = null;
        prop = new Properties();

        //in below line, we will get env variable value from mvn cmdline
        String envName = System.getProperty("env");
        System.out.println("Running tests on Env: "+ envName);

        try{
            if (envName == null){
                System.out.println("No env is given... hence running it on QA env... ");
                ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
            } else {
                switch (envName.toLowerCase().trim()){
                    //In this switch, we are trying to make connection with respective Prod file
                    // As we will have different URL and Credentials for those env
                    case "qa":
                        //Below FileInputStream will establish connection with config.properties file
                        ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
                        break;
                    case "dev":
                        ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
                        break;
                    case "stage":
                        ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
                        break;
                    case "uat":
                        ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
                        break;
                    case "prod":
                        ip = new FileInputStream("./src/test/resources/config/config.properties");
                        break;
                    default:
//                        System.out.println("Pls pass the right env name... "+ envName);
                        Log.error("Pls pass the right env name... "+ envName);
                        throw new FrameworkException(AppErrors.ENV_NAME_NOT_FOUND + " : " + envName );
                }

            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

        try {
            //Now below line will load all properties of that file prop object
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //In below line we have given "./" before src that means "in current directory"

        return prop;
    }

    /*** take screenshot */
    public static String getScreenshot(String methodName) {
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp directory
        String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
                + ".png";

        File destination = new File(path);

        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

}
