package com.expleo.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class Reporting  {
	
	public static void generateJVMReport(String jsonFile) throws FileNotFoundException, IOException {
		File file = new File("//target");
		Configuration config = new Configuration(file, "GW-Cucumber");
		config.addClassifications("Testing", "api");
		List<String> json = new ArrayList<String>();
		json.add(jsonFile);
		ReportBuilder builder = new ReportBuilder(json, config);
		builder.generateReports();
	}
	
}
