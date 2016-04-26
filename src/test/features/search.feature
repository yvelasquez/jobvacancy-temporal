Feature: Search offers

    Scenario: Search for all offers
        Given the "Heart of Darkness" company published offer for "Agile Opressor"
          And the "Agile Satori" company published offer for "Agile Explorer"
        When I search for active offers
        Then I should find offer with title "Agile Opressor'
        And I should find offer with title 'Agile Explorer'