package com.company.locators;

public interface Locators {

    String URL = "https://flipkart.com";
    String CANCEL_LOGIN = "//span[@role='button']";
    String TRAVEL = "//a[@aria-label='Travel']";

    /* Travel Page */
    String ONE_WAY = "ONE_WAY";
    String FROM_CITY = "//input[contains(@name,'departcity')]";

    String TO_CITY = "//input[contains(@name,'arrivalcity')]";

    String START_DATE = "//input[contains(@name,'datefrom')]";

    String TRAVELLERS_COUNT_AND_CABIN_CLASS = "//input[contains(@name,'travellerclasscount')]";

    String SEARCH = "//span[text()='SEARCH']/parent::button";

    String FROM_CITY_DROP_DOWN = "(//div[contains(@class,'24hoQ2')])[1]";

    String CITY_NAMES_IN_FROM_CITY_DROP_DOWN = "//div[contains(@class,'3uA4ax')]/descendant::span[1]";

    String TO_CITY_DROP_DOWN = "(//div[contains(@class,'24hoQ2')])[2]";

    String CITY_NAMES_IN_TO_CITY_DROP_DOWN = "//div[contains(@class,'3uA4ax')]/descendant::span[1]";

    String DURATION = "//span[contains(text(),'DURATION')]";

    String FLIGHTS_WITH_SAME_PRICE = "//span[contains(text(),'more flights at same price')]";

    String FLIGHT_DURATION_DETAILS = "div[id*='listing-layover-info-']>span:nth-of-type(1)";


}
