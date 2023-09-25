package com.company.hooks;

import com.company.driver.CreateDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CucumberHooks {
    private WebDriver driver;

    @Before
    public void before() {
        driver = CreateDriver.createDriver(System.getProperty("browser","chrome"));
        driver.get("http://flipkart.com");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15L));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement
                (By.xpath("//span[@role='button' and text()]"))));
        driver.findElement
                (By.xpath("//span[@role='button' and text()]")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
