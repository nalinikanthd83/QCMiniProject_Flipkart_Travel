package com.company.stepdefs;

import com.company.driver.CreateDriver;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.company.locators.Locators.DURATION;
import static com.company.locators.Locators.FLIGHTS_WITH_SAME_PRICE;

public class FlightsInfo {

    private WebDriver driver;

    @Then("I am on FlightInfo page")
    public void i_am_on_flight_info_page() {
        driver = CreateDriver.getDriver();
        getWebDriverWait(driver, 30L)
                .until(ExpectedConditions
                        .presenceOfElementLocated(By.xpath("//span[text()='Flipkart.com']")));
        PageFactory.initElements(driver, this);
    }

    @Then("I book flight having least travel time")
    public void i_book_flight_having_least_travel_time() {

        getWebDriverWait(driver, 30L).until(ExpectedConditions.elementToBeClickable
                (driver.findElement(By.xpath(DURATION)))).click();

        getWebDriverWait(driver, 30L).until(ExpectedConditions.invisibilityOfElementLocated
                (By.xpath(FLIGHTS_WITH_SAME_PRICE)));

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        long lastHeight = (long) javascriptExecutor.executeScript("return document.body.scrollHeight");
        long newHeight = 0L;

        scrollingToBottom(javascriptExecutor, lastHeight, newHeight);

        List<WebElement> flightDurations = driver.findElements
                (By.cssSelector("div[id*='listing-layover-info-']>span:nth-of-type(1)"));

        flightDurationsAscendingOrder(flightDurations);
        selectFlightLeastTravelTime(javascriptExecutor);


    }

    private void flightDurationsAscendingOrder(List<WebElement> flightDurations) {
        flightDurations.stream()
                .map(flightDuration -> flightDuration.getText().trim()).forEach(System.out::println);
    }

    private void selectFlightLeastTravelTime(JavascriptExecutor javascriptExecutor) {
        WebElement leastTravelTime = driver.findElement(By.xpath("(//div[contains(@class,'367J6x')]/child::div[@class='ZiOg5a'])[1]"));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();",leastTravelTime);
        javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",leastTravelTime);
        try {
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        javascriptExecutor.executeScript("arguments[0].click();",leastTravelTime);
    }

    private static void scrollingToBottom(JavascriptExecutor javascriptExecutor, long lastHeight, long newHeight) {
        while(lastHeight != newHeight) {
            javascriptExecutor.executeScript("window.scrollBy(0,"+ lastHeight +")");
            newHeight = (long) javascriptExecutor.executeScript("return document.body.scrollHeight;");
            if (lastHeight == newHeight) {
                break;
            } else {
                javascriptExecutor.executeScript("window.scrollBy(0,"+newHeight+")");
                lastHeight = newHeight;
                //javascriptExecutor.executeScript("window.scrollBy(0,"+ lastHeight +")");
                newHeight = (long) javascriptExecutor.executeScript("return document.body.scrollHeight;");
            }
        }
    }


    private WebDriverWait getWebDriverWait(WebDriver driver, long waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        return wait;
    }
}

//
//    @Then("I book flight having least travel time")
//    public void i_book_flight_having_least_travel_time() {
//
//        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
//        long lastHeight = (long) javascriptExecutor.executeScript("return document.body.scrollHeight");
//        long newHeight = 0L;
//        while (true) {
//            try {
//                getWebDriverWait(driver, 50L).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//span[contains(text(),'more flights at same price')]"))));
//                WebElement moreFlightsWithSameFare = driver.findElement(By.xpath("//span[contains(text(),'more flights at same price')]"));
//                javascriptExecutor.executeScript("arguments[0].scrollIntoView();", moreFlightsWithSameFare);
//                javascriptExecutor.executeScript("arguments[0].click();", moreFlightsWithSameFare);
//                newHeight = (long) javascriptExecutor.executeScript("return document.body.scrollHeight");
//                if (lastHeight == newHeight) {
//                    break;
//                } else {
//                    lastHeight = newHeight;
//                    //javascriptExecutor.executeScript("window.scrollBy(0,"+lastHeight+")");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                break;
//            }
//        }
//
//        List<WebElement> flightsDuration = driver.findElements
//                (By.cssSelector("div[id*='listing-layover-info-']>span:nth-of-type(1)"));
//
//        List<String> sortedByHours = flightsDuration.stream().map(flightDuration -> flightDuration.getText()
//                        .trim()).filter(flightDuration -> flightDuration.trim()
//                        .matches("^([0-9]{1,2}[h])?(\\s)?([0-9]{1,2}[m])?"))
//                .map(flightDuration -> {
//                    String[] timeComponents = flightDuration.trim().split(" ");
//                    StringBuilder hours = new StringBuilder();
//                    StringBuilder minutes = new StringBuilder();
//                    switch (timeComponents.length) {
//                        case 1 -> {
//                            if (timeComponents[0].contains("h") & timeComponents[0].length() == 2) {
//                                hours.append("0").append(timeComponents[0]);
//                            } else if (timeComponents[0].contains("h") & timeComponents[0].length() > 2) {
//                                hours.append(timeComponents[0]);
//                            }
//                            if (timeComponents[0].contains("m") & timeComponents[0].length() == 2) {
//                                minutes.append("00h 0").append(timeComponents[0]);
//                            } else if (timeComponents[0].contains("m") & timeComponents[0].length() > 2) {
//                                minutes.append(timeComponents[0]);
//                            }
//                        }
//                        case 2 -> {
//                            if (timeComponents[0].contains("h") & timeComponents[0].length() == 2) {
//                                hours.append("0").append(timeComponents[0]);
//                            } else if (timeComponents[0].contains("h") & timeComponents[0].length() > 2) {
//                                hours.append(timeComponents[0]);
//                            }
//                            if (timeComponents[1].contains("m") & timeComponents[1].length() == 2) {
//                                minutes.append("0").append(timeComponents[1]);
//                            } else if (timeComponents[1].contains("m") & timeComponents[1].length() > 2) {
//                                minutes.append(timeComponents[1]);
//                            }
//                        }
//                    }
//                    return hours.append(" ").append(minutes).toString().trim();
//                }).sorted().toList();
//
//        List<String> sortedByDays = flightsDuration.stream().map(flightDuration -> flightDuration.getText()
//                        .trim()).filter(flightDuration ->
//                        flightDuration.matches("^[0-9]+d\\s?([0-9]{1,2}h)?\\s?([0-9]{1,2}m)?"))
//                .map(flightDuration -> {
//                    String[] timeComponents = flightDuration.trim().split(" ");
//                    StringBuilder days = new StringBuilder();
//                    StringBuilder hours = new StringBuilder();
//                    StringBuilder minutes = new StringBuilder();
//                    switch (timeComponents.length) {
//                        case 1:
//                            if (timeComponents[0].contains("d") & timeComponents[0].length() == 2) {
//                                days.append("0").append(timeComponents[0]);
//                            } else if (timeComponents[0].contains("d") & timeComponents[0].length() > 2) {
//                                days.append(timeComponents[0]);
//                            }
//                            break;
//                        case 2:
//                            if (timeComponents[0].contains("d") & timeComponents[0].length() == 2) {
//                                days.append("0").append(timeComponents[0]);
//                            } else if (timeComponents[0].contains("d") & timeComponents[0].length() > 2) {
//                                days.append(timeComponents[0]);
//                            }
//
//                            if (timeComponents[1].contains("h") & timeComponents[1].length() == 2) {
//                                hours.append("0").append(timeComponents[1]);
//                            } else if (timeComponents[1].contains("h") & timeComponents[1].length() > 2) {
//                                hours.append(timeComponents[1]);
//                            }
//
//                            if (timeComponents[1].contains("m") & timeComponents[1].length() == 2) {
//                                minutes.append("00h 0").append(timeComponents[1]);
//                            } else if ((timeComponents[1].contains("m") & timeComponents[1].length() > 2)) {
//                                minutes.append("00h ").append(timeComponents[1]);
//                            }
//                            break;
//                        case 3:
//                            if (timeComponents[0].contains("d") & timeComponents[0].length() == 2) {
//                                days.append("0").append(timeComponents[0]);
//                            } else if (timeComponents[0].contains("d") & timeComponents[0].length() > 2) {
//                                days.append(timeComponents[0]);
//                            }
//
//                            if (timeComponents[1].contains("h") & timeComponents[1].length() == 2) {
//                                hours.append("0").append(timeComponents[1]);
//                            } else if (timeComponents[1].contains("h") & timeComponents[1].length() > 2) {
//                                hours.append(timeComponents[1]);
//
//                            }
//                            if (timeComponents[2].contains("m") & timeComponents[2].length() == 2) {
//                                minutes.append("0").append(timeComponents[2]);
//                            } else if (timeComponents[2].contains("m") & timeComponents[2].length() > 2) {
//                                minutes.append(timeComponents[2]);
//                            }
//                            break;
//                    }
//                    return days.append(" ").append(hours).append(" ").append(minutes).toString().trim();
//                }).sorted().toList();
//
//        List<String> sortedDuration = Stream.concat(sortedByHours.stream(), sortedByDays.stream()).toList();
//
//        sortedDuration.forEach(System.out::println);
//
//    }

