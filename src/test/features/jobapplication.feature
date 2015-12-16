Feature: Job Application

    Scenario: Job application happy path
        Given java programmer offer
        When I apply to this offer
        Then an email to sent to the owner of the offer
        And the application is registered in the system
