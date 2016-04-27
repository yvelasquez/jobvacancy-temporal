@txn
Feature: Company reputation

    Scenario: Reputation Zero when no offers published
        Given the "ACME" company
        When  no offers published
        Then reputation is "0"

    Scenario: Reputation is proportional to recent published offers
        Given the "ACME" company
        When  "3" offers published in the last month
        Then reputation is "3"
