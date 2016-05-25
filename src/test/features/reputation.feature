Feature: Reputation
    Every company has a reputation that is calculated with the following formula:
      RO: Count of Offers publish in the last month
      MR: Months since registration
      ST: subscription type
          BASIC => 0
          ONDEMAND => 0
          STANDARD => 1
          ADVANCED => 2
      Reputation = (0.5) * RO + (0.3) * MR + (0.2) * ST
