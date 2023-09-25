Feature: Flipkart Travel

  Background: Click Travel link
    Given  I am on Home page
    When   I click Travel

#  Scenario: One Way is selected by default
#    Then   I am on Travel page
#    And    One Way is selected by default

  Scenario: City names in From and To dropdowns should be same
    Then   I am on Travel page
    And    From and To dropdowns are same