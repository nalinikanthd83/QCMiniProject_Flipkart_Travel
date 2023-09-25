package com.company.stepdefs;

import com.company.driver.CreateDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
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
import java.util.Optional;

public class TravelPage {

    private WebDriver driver;

    @FindBy(id = "ONE_WAY")
    private WebElement oneWay;

    @FindBy(xpath = "//input[contains(@name,'departcity')]")
    private WebElement fromCity;

    @FindBy(xpath = "//input[contains(@name,'arrivalcity')]")
    private WebElement toCity;

    @FindBy(name = "0-datefrom")
    private WebElement toDate;

    @And("One Way is selected by default")
    public void one_way_is_selected_by_default() {

        Assert.assertTrue(oneWay.isSelected());
    }

    @And("From and To dropdowns are same")
    public void from_and_to_dropdowns_are_same() {
        fromCity.click();
        WebDriverWait wait = getWebDriverWait(driver, 20L);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement
                (By.xpath("(//div[contains(@class,'24hoQ2')])[1]"))));

        List<String> fromCities = driver.findElements
                        (By.xpath("//div[contains(@class,'3uA4ax')]/descendant::span[1]"))
                .stream().map(WebElement::getText)
                .filter(text -> text.length() > 0).sorted().toList();

        System.out.println(fromCities);

        toCity.click();

        wait = getWebDriverWait(driver, 20L);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement
                (By.xpath("(//div[contains(@class,'24hoQ2')])[2]"))));

        List<String> toCities = driver.findElements
                        (By.xpath("//div[contains(@class,'3uA4ax')]/descendant::span[1]"))
                .stream().map(WebElement::getText)
                .filter(text -> text.length() > 0).sorted().toList();

        System.out.println(toCities);

        Assert.assertEquals(fromCities, toCities);

    }

    private WebDriverWait getWebDriverWait(WebDriver driver, long waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        return wait;
    }

    @When("I select starting city {string}")
    public void i_select_starting_city(String string) {
        // Write code here that turns the phrase above into concrete actions
        fromCity.click();

        WebDriverWait wait = getWebDriverWait(driver, 20L);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement
                (By.xpath("(//div[contains(@class,'24hoQ2')])[1]"))));

        Optional<WebElement> fromCity = driver.findElements
                        (By.xpath("//div[contains(@class,'3uA4ax')]/descendant::span[1]"))
                .stream()
                .filter(e -> e.getText().equalsIgnoreCase(string))
                .findFirst();


            if (fromCity.isPresent()) {
                fromCity.get().click();
             } else {
                throw new RuntimeException("Incorrect source city name");
        }
    }

    @When("I select destination city {string}")
    public void i_select_destination_city(String string) {
        // Write code here that turns the phrase above into concrete actions
        toCity.click();

        WebDriverWait wait = getWebDriverWait(driver, 20L);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement
                (By.xpath("(//div[contains(@class,'24hoQ2')])[2]"))));

        Optional<WebElement> toCity = driver.findElements
                        (By.xpath("//div[contains(@class,'3uA4ax')]/descendant::span[1]"))
                .stream()
                .filter(e -> e.getText().equalsIgnoreCase(string))
                .findFirst();

        if (toCity.isPresent()) {
            toCity.get().click();
        } else {
            throw new RuntimeException("Incorrect destination city name");
        }
    }

        @When("I select date {string}")
        public void i_select_date (String date) throws InterruptedException {
            // Write code here that turns the phrase above into concrete actions

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

    private void clickPresentYearDate(List<String> months, String[] dateSplit) {

        toDate.click();
        String[] currentMonthYear = driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]"))
                .getText().trim().split("\\s+");
        String inputtedMonth = dateSplit[1].trim();

         if (months.indexOf(currentMonthYear[0].trim().toUpperCase()) > months.indexOf(inputtedMonth.toUpperCase())) {
             throw new RuntimeException("Past Date Entered");
         }


        while (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]"))
                .getText().trim().toLowerCase().contains(dateSplit[2].trim())) {
            if (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]"))
                    .getText().trim().toLowerCase().contains(dateSplit[1].trim().toLowerCase() + " " + dateSplit[2].trim())) {
                    if (driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                            dateSplit[0].trim() + "'" + "])[1]")).isEnabled()) {
                        driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                                dateSplit[0].trim() + "'" + "])[1]")).click();
                        System.out.println(toDate.getAttribute("value"));
                        break;
                    } else {
                        System.out.println(Arrays.toString(dateSplit));
                        throw new RuntimeException("Check the date entered");
                    }
                } else if (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[2]"))
                    .getText().trim().toLowerCase().contains(dateSplit[1].trim().toLowerCase() + " " + dateSplit[2].trim())) {
                    if (driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                            dateSplit[0].trim() + "'" + "])[2]")).isEnabled()) {
                        driver.findElement(By.xpath("(//table/descendant::button[text()=" + "'" +
                                dateSplit[0].trim() + "'" + "])[2]")).click();
                        System.out.println(toDate.getAttribute("value"));
                        break;
                    } else {
                        System.out.println(Arrays.toString(dateSplit));
                        throw new RuntimeException("Check the date entered");
                    }
            } else {
                driver.findElement(By.xpath("(//table/descendant::button[1])[2]")).click();
            }
        }
    }

    private void clickDate(String[] dateSplit) {

        while (true) {
            toDate.click();
            driver.findElement(By.xpath("(//table/descendant::button[1])[2]")).click();
            if (driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]"))
                    .getText().trim().toLowerCase().contains(dateSplit[1].trim().toLowerCase() + " " + dateSplit[2].trim())) {
                if (!driver.findElement(By.xpath("(//table/descendant::div[text()][1])[1]")).isEnabled()) {
                    throw new RuntimeException();
                }
                try {
                driver.findElement(By.xpath("(//table/descendant::button[text()="+"'"+
                           dateSplit[0].trim()+"'"+"])[1]")).click();
                System.out.println(toDate.getAttribute("value"));
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
                    System.out.println(toDate.getAttribute("value"));
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

    private void monthAndDayValidation (String[] dateSplit) {

        List<String> months = List.of("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
                "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");

            if (!months.contains(dateSplit[1].trim().toUpperCase())) {
                throw new RuntimeException("Invalid month name");
            }

            if (Integer.parseInt(dateSplit[0]) <= 0 || Integer.parseInt(dateSplit[0]) > 31) {
                throw new RuntimeException("Invalid date");
            }
        }

        @When("I select {int} adults")
        public void i_select_adults (Integer int1){
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
        }
        @When("I select {string} class")
        public void i_select_class (String string){
            // Write code here that turns the phrase above into concrete actions
            throw new io.cucumber.java.PendingException();
        }
        @Given("I am on Travel page")
        public void i_am_on_travel_page () {
            driver = CreateDriver.getDriver();
            PageFactory.initElements(driver, this);
            getWebDriverWait(driver, 20L).until(ExpectedConditions.elementToBeClickable(fromCity));
        }
    }
