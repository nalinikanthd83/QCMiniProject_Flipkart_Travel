Feature: Flipkart Travel

  Background: Click Travel link
    Given  I am on Home page
    When   I click Travel

    @OneWay
  Scenario: One Way is selected by default
    Then   I am on Travel page
    And    One Way is selected by default

      @CityNames
  Scenario: City names in From and To dropdowns should be same
    Then   I am on Travel page
    And    From and To dropdowns are same

    @SearchFlights
  Scenario Outline: Search Flights
    Then   I am on Travel page
    When   I select starting city "<fromCity>"
    *      I select destination city "<toCity>"
    *      I select date "<departOn>"
#    *      I select <numberOfAdults> adults
#    *      I select "<cabin>" class
    Examples:
      | fromCity | toCity    | departOn          | numberOfAdults | cabin    |
      | Mumbai   | Hyderabad | 12 July 2023   | 3              | Business |