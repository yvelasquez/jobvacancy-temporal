Feature: Billing Process
    Subscription are managed on monthly basis
    Subscription type changes are out of scope (for now)
    The bill should include the only the amout to pay for the current period (no previous debts)
    For billing purposes, the minimum period to bill is half a month
    Each company should be billed on the day of its registration
    The billing statement should indicate the date of the next bill


    Scenario: Full month billing
        Given the "ACME" company registered on "2014-01-01"
          And with a standard subscription
        When  calculate billing on "2014-02-01"
        Then  the total amount to bill is: 100
         And the date of the next bill is "2014-03-01"

    Scenario: Partial month billing
        Given the "ACME" company registered on "2014-01-20"
        And with a standard subscription
        When  calculate billing on "2014-02-01"
        Then  the total amount to bill is: 50
        And the date of the next bill is "2014-03-01"
