package com.company.stepdefs;

import com.company.driver.CreateDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.company.locators.Locators.*;

public class TravelPage {

    private WebDriver driver;

    @FindBy(id = ONE_WAY)
    private WebElement oneWay;

    @FindBy(xpath = FROM_CITY)
    private WebElement fromCity;

    @FindBy(xpath = TO_CITY)
    private WebElement toCity;

    @FindBy(xpath = START_DATE)
    private WebElement startDate;

    @FindBy(xpath = TRAVELLERS_COUNT_AND_CABIN_CLASS)
    private WebElement travellersCountClass;

    @FindBy(xpath = SEARCH)
    private WebElement search;


    @And("One Way is selected by default")
    public void one_way_is_selected_by_default() {
        Assert.assertTrue(oneWay.isSelected());
    }

    @And("From and To dropdowns are same")
    public void from_and_to_dropdowns_are_same() {
        fromCity.click();

        WebDriverWait wait = getWebDriverWait(driver, 20L);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(FROM_CITY_DROP_DOWN))));

        List<String> fromCities = driver.findElements(By.xpath(CITY_NAMES_IN_FROM_CITY_DROP_DOWN))
                .stream().map(WebElement::getText)
                .filter(text -> text.length() > 0).sorted().toList();

        System.out.println("From Cities -> " + fromCities);

        toCity.click();

        wait = getWebDriverWait(driver, 20L);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(TO_CITY_DROP_DOWN))));

        List<String> toCities = driver.findElements(By.xpath(CITY_NAMES_IN_TO_CITY_DROP_DOWN))
                .stream().map(WebElement::getText)
                .filter(text -> text.length() > 0).sorted().toList();

        System.out.println("To Cities   -> " + toCities);

        Assert.assertEquals(fromCities, toCities);

    }

    private WebDriverWait getWebDriverWait(WebDriver driver, long waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        return wait;
    }

    @When("I select starting city {string}")
    public void i_select_starting_city(String city) {

        fromCity.click();

        WebDriverWait wait = getWebDriverWait(driver, 20L);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement
                (By.xpath(FROM_CITY_DROP_DOWN))));

        Optional<WebElement> from = driver.findElements(By.xpath(CITY_NAMES_IN_FROM_CITY_DROP_DOWN))
                .stream()
                .filter(e -> e.getText().equalsIgnoreCase(city))
                .findFirst();

        if (from.isPresent()) {
            from.get().click();
        } else {
            throw new RuntimeException("Incorrect From city name");
        }
    }

    @When("I select destination city {string}")
    public void i_select_destination_city(String city) {

        toCity.click();

        WebDriverWait wait = getWebDriverWait(driver, 20L);
        wait.until(ExpectedConditions
                .elementToBeClickable(driver.findElement(By.xpath(TO_CITY_DROP_DOWN))));

        Optional<WebElement> to = driver.findElements(By.xpath(CITY_NAMES_IN_TO_CITY_DROP_DOWN))
                .stream()
                .filter(e -> e.getText().equalsIgnoreCase(city))
                .findFirst();

        if (to.isPresent()) {
            to.get().click();
        } else {
            throw new RuntimeException("Incorrect destination city name");
        }
    }

    @When("I select date {string}")
    public void i_select_date(String date) {

        List<String> months = List.of("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
                "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");

        String[] dateSplit = date.trim().split("\\s+");
        int enteredYear = Integer.parseInt(dateSplit[2].trim());
        int currentYear = Integer.parseInt(Year.now().toString());

        if (currentYear > enteredYear) {
            throw new RuntimeException("Past Date Entered");
        }

        if (currentYear < enteredYear) {
            if (enteredYear - currentYear > 1) {
                throw new RuntimeException("Not possible to select this date");
            }
            monthAndDayValidation(dateSplit);
            clickDate(dateSplit);
        }

        if (currentYear == enteredYear) {
            monthAndDayValidation(dateSplit);
            clickPresentYearDate(months, dateSplit);
        }
    }


    @When("I select {string} adults")
    public void i_select_adults(String numOfAdults) {
        // Write code here that turns the phrase above into concrete actions
        travellersCountClass.click();
        int counter = Integer.parseInt(driver.findElement(By.xpath("(//div[contains(@class,'1Di8FC')]/descendant::div[contains(@class,'3ahBnm')])[1]"))
                .getText());
        while (counter < Integer.parseInt(numOfAdults)) {
            driver.findElement(By.xpath("(//div[contains(@class,'1Di8FC')])[1]/descendant::div[contains(@class,'VjWsXZ')][2]"))
                    .click();
            counter = Integer.parseInt(driver.findElement(By.xpath("(//div[contains(@class,'1Di8FC')]/descendant::div[contains(@class,'3ahBnm')])[1]"))
                    .getText());
        }
    }

    @When("I select {string} class")
    public void i_select_class(String cabinClass) {
        // Write code here that turns the phrase above into concrete actions
        WebDriverWait webDriverWait = getWebDriverWait(driver, 30L);
        switch (cabinClass.toLowerCase()) {
            case "economy":
                break;
            case "premium economy":
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                        driver.findElement(By.id("w")));
                break;
            case "business":
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                        driver.findElement(By.id("b")));
                break;
            default:
                throw new IllegalArgumentException("Incorrect value");
        }
        driver.findElement(By.xpath("//button[text()='Done']")).click();
    }

    @When("I click Search button")
    public void i_click_search_button() {
        WebDriverWait webDriverWait = getWebDriverWait(driver, 20L);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(search)).click();
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("I am on Travel page")
    public void i_am_on_travel_page() {
        driver = CreateDriver.getDriver();
        getWebDriverWait(driver, 30L).until(ExpectedConditions.presenceOfElementLocated(
                (By.xpath(FROM_CITY))));
        PageFactory.initElements(driver, this);
    }

    @When("I enter details and perform search")
    public void i_enter_details_and_perform_search(DataTable dataTable) {
        List<String> list = dataTable.row(0);

        i_select_starting_city(list.get(0));
        i_select_destination_city(list.get(1));
        i_select_date(list.get(2));
        i_select_adults(list.get(3));
        i_select_class(list.get(4));
        i_click_search_button();
    }


    private void clickPresentYearDate(List<String> months, String[] dateSplit) {

        startDate.click();
        String[] currentMonthYear = driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]"))
                .getText().trim().split("\\s+");
        String inputtedMonth = dateSplit[1].trim();

        if (months.indexOf(currentMonthYear[0].trim().toUpperCase()) > months.indexOf(inputtedMonth.toUpperCase())) {
            throw new IllegalArgumentException("Past Date Entered");
        }

        while (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[2]"))
                .getText().trim().toLowerCase().contains(dateSplit[2].trim().toLowerCase())) {
            if (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]"))
                    .getText().trim().toLowerCase().contains((dateSplit[1].trim().toLowerCase()) + " " + (dateSplit[2].trim()))) {
                try {
                    if (driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                            dateSplit[0].trim() + "'" + "])")).isEnabled()) {
                        driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                                dateSplit[0].trim() + "'" + "])")).click();
                        System.out.println(startDate.getAttribute("value"));
                        break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Date not valid " + Arrays.toString(dateSplit));
                }
            }
            if (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[2]"))
                    .getText().trim().toLowerCase().contains((dateSplit[1].trim().toLowerCase()) + " " + (dateSplit[2].trim()))) {
                try {
                    if (driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                            dateSplit[0].trim() + "'" + "])")).isEnabled()) {
                        driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                                dateSplit[0].trim() + "'" + "])")).click();
                        System.out.println(startDate.getAttribute("value"));
                        break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Date not valid " + Arrays.toString(dateSplit));
                }
            }

            driver.findElement(By.xpath("(//table/descendant::button[1])[2]")).click();
        }
    }

    private void monthAndDayValidation(String[] dateSplit) {

        List<String> months = List.of(
                "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
                "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        );

        if (!months.contains(dateSplit[1].trim().toUpperCase())) {
            throw new RuntimeException("Invalid month name");
        }

        if (Integer.parseInt(dateSplit[0]) <= 0 || Integer.parseInt(dateSplit[0]) > 31) {
            throw new RuntimeException("Invalid date");
        }
    }

    private void clickDate(String[] dateSplit) {

        while (true) {
            startDate.click();
            driver.findElement(By.xpath("(//table/descendant::button[1])[2]")).click();
            if (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]"))
                    .getText().trim().toLowerCase().contains(dateSplit[1].trim().toLowerCase() + " " + dateSplit[2].trim())) {
                if (!driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]")).isEnabled()) {
                    throw new RuntimeException();
                }
                try {
                    driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                            dateSplit[0].trim() + "'" + "])[1]")).click();
                    System.out.println(startDate.getAttribute("value"));
                } catch (RuntimeException e) {
                    System.out.println(Arrays.toString(dateSplit));
                    throw new RuntimeException("Check the date entered");
                }
                break;
            } else if (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[2]"))
                    .getText().trim().toLowerCase().contains(dateSplit[1].trim().toLowerCase() + " " + dateSplit[2].trim())) {
                try {
                    if (!driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                            dateSplit[0].trim() + "'" + "])[2]")).isEnabled()) {
                        throw new RuntimeException();
                    }
                    driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                            dateSplit[0].trim() + "'" + "])[2]")).click();
                    System.out.println(startDate.getAttribute("value"));
                } catch (RuntimeException e) {
                    System.out.println(Arrays.toString(dateSplit));
                    throw new RuntimeException("Check the date entered");
                }
                break;
            } else if (!driver.findElement(By.xpath("(//table/descendant::button[1])[2]"))
                    .isEnabled()) {
                throw new RuntimeException("Date cannot be selected");
            }
        }
    }
}
