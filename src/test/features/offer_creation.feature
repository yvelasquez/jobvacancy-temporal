@txn
Feature: Offer management

    Scenario: Offer creation main flow
        Given the user "Micheal" with company "ACME Limited"
        When he posts an offer with title "Java Programmer"
        Then the offer is created
        And the offer is listed in public offers

    Scenario: User without company can not post offers
        Given the user "JamesSmith" with no company
        When he posts an offer with title "Node Programmer"
        Then the offer is not created
        Then the offer is not listed in public offers
