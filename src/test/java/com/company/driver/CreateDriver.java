package com.company.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CreateDriver {

    private static WebDriver driver;

    public static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome" -> driver = new ChromeDriver();
            case "firefox" -> driver = new FirefoxDriver();
            default -> throw new RuntimeException("Unsupported browser " + browser);
        }
        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

}
