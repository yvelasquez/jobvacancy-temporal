Feature: Offer management

    Scenario: Offer creation main flow
        Given the user "Micheal" with company "ACME Limited"
        When he posts an offer with title "Java Programmer"
        Then the offer is listed in public offers

    @wip
    Scenario: User without company can not post offers
        Given the user "JohnDoe" with company "ACME Limited"
        When he posts an offer with title "Java Programmer"
        Then the offer is listed in public offers
