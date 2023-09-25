package com.company.stepdefs;

import com.company.driver.CreateDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class TravelPage {

    private WebDriver driver;

    @FindBy(id = "ONE_WAY")
    private WebElement oneWay;

    @FindBy(xpath = "//input[contains(@name,'departcity')]")
    private WebElement fromCity;

    @FindBy(xpath = "//input[contains(@name,'arrivalcity')]")
    private WebElement toCity;

    @And("One Way is selected by default")
    public void one_way_is_selected_by_default()  {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(oneWay.isSelected());
    }

    @And("From and To dropdowns are same")
    public void from_and_to_dropdowns_are_same() throws InterruptedException {
        fromCity.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement
                (By.xpath("(//div[contains(@class,'24hoQ2')])[1]"))));

        List<WebElement> from = driver.findElements
                (By.xpath("//div[contains(@class,'3uA4ax')]/descendant::span[1]"));
        List<String> fromCities = from.stream().map(WebElement::getText)
                .filter(text -> text != "").sorted().toList();

        System.out.println(fromCities);

        toCity.click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement
                (By.xpath("(//div[contains(@class,'24hoQ2')])[2]"))));

        List<WebElement> to = driver.findElements
                (By.xpath("//div[contains(@class,'3uA4ax')]/descendant::span[1]"));

        List<String> toCities = to.stream().map(WebElement::getText)
                .filter(text -> text != "").sorted().toList();

        System.out.println(toCities);

        Assert.assertEquals(fromCities, toCities);


    }



    @Given("I am on Travel page")
    public void i_am_on_travel_page(){
        driver = CreateDriver.getDriver();
        PageFactory.initElements(driver, this);
    }
}
