package cn.ianzhang.homework2;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/test.feature", format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class RunCucumberTest {
}