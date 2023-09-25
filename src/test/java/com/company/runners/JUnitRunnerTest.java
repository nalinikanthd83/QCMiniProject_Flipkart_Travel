package com.company.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.company",
        plugin = {"pretty", "html:target/cucumber.html"},
        tags = "@SearchFlights"
)
public class JUnitRunnerTest {

}
