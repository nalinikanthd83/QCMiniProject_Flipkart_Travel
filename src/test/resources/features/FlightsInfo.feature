@FlightsInfo
Feature: Flights Information

  Background: Click Travel link
    Given  I am on Home page
    When   I click Travel

  @FlightsInfo
  Scenario: Flights Info
    Then   I am on Travel page
    When   I enter details and perform search
    | Mumbai   | Dubai  | 29 September 2023 | 3     | Business   |
    Then   I am on FlightInfo page
    And    I book flight having least travel time