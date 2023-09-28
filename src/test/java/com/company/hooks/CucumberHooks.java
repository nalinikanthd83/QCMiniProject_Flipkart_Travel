package com.company.hooks;

import com.company.driver.CreateDriver;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.company.locators.Locators.CANCEL_LOGIN;
import static com.company.locators.Locators.URL;

public class CucumberHooks {
    private WebDriver driver;

    @Before
    public void before() {
        driver = CreateDriver.createDriver(System.getProperty("browser", "chrome"));
        driver.get(URL);
        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofSeconds(20L))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(CANCEL_LOGIN))).click();
    }

//    @After
//    public void tearDown() {
//        driver.quit();
//    }
}
