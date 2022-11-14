Feature: Viewing alerts on MarketAlertUm

  As a user or administrator of MarketAlertUm, after
  uploading alerts to the website, I should be able
  to view them on the dashboard.

  Scenario:  Alert layout
    Given I am an administrator of the website and I upload 3 alerts
    Given I am a logged in user of MarketAlertUm
    When I view a list of alerts
    Then each alert should contain an icon
    And each alert should contain a heading
    And each alert should contain a description
    And each alert should contain an image
    And each alert should contain a price
    And each alert should contain a link to the original product website

  Scenario: Alert limit
    Given I am an administrator of the website and I upload more than 5 alerts
    Given I am a logged in user of MarketAlertUm
    When I view a list of alerts
    Then I should see 5 alerts

  Scenario Outline: Icon check
    Given I am an administrator of the website and I upload an alert of type <alertType>
    Given I am a logged in user of MarketAlertUm
    When I view a list of alerts
    Then I should see 1 alerts
    And the icon displayed should be <iconFileName>

    Examples:
      | alertType | iconFileName           |
      | 1         | icon-car.png           |
      | 2         | icon-boat.png          |
      | 3         | icon-property-rent.png |
      | 4         | icon-property-sale.png |
      | 5         | icon-toys.png          |
      | 6         | icon-electronics.png   |