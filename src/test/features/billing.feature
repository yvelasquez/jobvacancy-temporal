Feature: Billing

    Scenario: Basic subscription is free
        Given the "ACME" company with a basic subscription
        When  calculate billing
        Then  the total amount to pay is: 0

    Scenario: Standard subscription costs 100
        Given the "ACME" company with a standard subscription
        When  calculate billing
        Then  the total amount to pay is: 100

    Scenario: On-demand subscription costs 0 if no published offers
        Given the "ACME" company with a on-demand subscription
        When  "0" published offers
        When  calculate billing
        Then  the total amount to pay is: 0

    Scenario: On-demand subscription costs 30 per published offer
        Given the "ACME" company with a on-demand subscription
        When  "2" published offers
        When  calculate billing
        Then  the total amount to pay is: 60

    Scenario: Advanced subscription costs 200
        Given the "ACME" company with a advanced subscription
        When  calculate billing
        Then  the total amount to pay is: 200
