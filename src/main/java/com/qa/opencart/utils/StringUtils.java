package com.qa.opencart.utils;

public class StringUtils {

    public static String getRandomEmailId(){
        String emailId = "testAutomation" + System.currentTimeMillis() + "@opencart.com";
        return emailId;
    }
}
