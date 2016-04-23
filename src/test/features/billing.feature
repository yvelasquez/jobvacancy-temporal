Feature: Billing

    Scenario: Basic subscription is free
        Given the "ACME" company with a basic subscription
        When  calculate billing
        Then  the total amount to pay is: 0
