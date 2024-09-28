package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTests;
import com.qa.opencart.contants.AppContants;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegPageTest extends BaseTests {

    @Description("Checking navigation to registration page....")
    @Severity(SeverityLevel.BLOCKER)
    @BeforeClass
    public void infoPageSetup(){
        registrationPage = loginPage.navigateToRegistrationPage();
    }

    @DataProvider
    public Object[][] getUserRegData(){
        return new Object[][]{
                {"Gaurav", "Gupta", "8734229898", "gg@123", "yes"},
                {"Tarun", "roy", "8734229456", "troy@123", "no"},
                {"om", "sharma", "8734223879", "om@123", "yes"}
        };
    }

    @DataProvider
    public Object[][] getUserRegDataFromExcel(){
        return ExcelUtil.getTestData(AppContants.REGISTER_SHEET_NAME);
    }

    //Even you can give name to DataProvider if you want & same you can use to call that
    @DataProvider(name = "csvregdata")
    public Object[][] getUserRegDataFromCSV(){
        return CSVUtil.csvData(AppContants.REGISTER_SHEET_NAME);
    }

    @Description("Checking user is able to register into opencart application....")
    @Severity(SeverityLevel.CRITICAL)
    @Step("checking user registration")
    @Test(dataProvider = "csvregdata")
    public void userRegTest(String firstName, String lastName, String telephone, String password, String subscribe){
       Assert.assertTrue(registrationPage.userRegistration(firstName, lastName, StringUtils.getRandomEmailId(), telephone, password, subscribe));
    }
}
