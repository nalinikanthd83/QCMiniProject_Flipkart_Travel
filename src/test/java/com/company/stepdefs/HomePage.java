package com.company.stepdefs;

import com.company.driver.CreateDriver;
import com.company.locators.Locators;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.company.locators.Locators.TRAVEL;

public class HomePage {

    @FindBy(xpath = TRAVEL)
    private WebElement travel;

    @Given("I am on Home page")
    public void i_am_on_home_page() {
        WebDriver driver = CreateDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @When("I click Travel")
    public void i_click_travel() {
        travel.click();
    }
}
