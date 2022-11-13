Feature: Logging into MarketAlertUm

  To be able to view my alerts, I must first
  log into the website using my user ID.

  Scenario: Valid Login
    Given I am a user of MarketAlertUm
    When I login using valid credentials
    Then I should see my alerts

  Scenario: Invalid Login
    Given I am a user of MarketAlertUm
    When I login using invalid credentials
    Then I should see the login screen again

