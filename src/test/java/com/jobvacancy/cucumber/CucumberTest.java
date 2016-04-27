package com.jobvacancy.cucumber;

import cucumber.api.java.Before;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/features", tags = "~@wip", glue = { "com.jobvacancy.cucumber","cucumber.api.spring"})
public class CucumberTest {


}
