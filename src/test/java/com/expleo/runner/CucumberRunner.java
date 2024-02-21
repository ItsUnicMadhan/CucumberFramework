package com.expleo.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty"},
		glue = {"com.expleo.stepdef"},
		features = {"src/test/resource/features"})
public class CucumberRunner {}
