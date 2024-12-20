package org.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example",
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber-reports.json", "junit:target/cucumber-reports.xml"}
)
public class RunnerTest {
}