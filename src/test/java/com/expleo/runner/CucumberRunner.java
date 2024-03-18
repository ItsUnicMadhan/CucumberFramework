package com.expleo.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "html:target/cucumber-Report.html", "json:target/report.json"},
		glue = {"com.expleo.stepdef"},
		features = {"src/test/resources/features/GitCRUD.feature"},
		dryRun = false)
public class CucumberRunner {}
