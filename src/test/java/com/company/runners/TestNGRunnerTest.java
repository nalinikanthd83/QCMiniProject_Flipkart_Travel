package com.company.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
features = "src/test/resources/features",
glue = "com.company",
plugin = {"pretty", "html:target/cucumber.html"},
tags = "@FlightsInfo"
)
public class TestNGRunnerTest extends AbstractTestNGCucumberTests {
}
