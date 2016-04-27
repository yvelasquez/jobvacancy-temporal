@txn
Feature: Search offers

    Scenario: Search for all offers
        Given the "Heart of Darkness" company published offer for "Agile Opressor"
          And the "Agile Satori" company published offer for "Agile Explorer"
        When I search for active offers
        Then I should find offer with title "Agile Opressor"
        And I should find offer with title "Agile Explorer"

    Scenario: Search for all offers with Advanced subscription
        Given the "Heart of Darkness" company published offer for "Agile Opressor"
          And the "Agile Satori" company with Advanced subscription published offer for "Agile Explorer"
        When I search for active offers
        Then I should first find offer with title "Agile Explorer"
        And I should find offer with title "Agile Opressor"

