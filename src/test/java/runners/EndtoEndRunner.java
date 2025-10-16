package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/End_to_End_social_comments_flow.feature",
//    features="src/test/resources/features/negative.feature",
    glue = {"social_comments"},
    plugin = {"pretty", "reports.ExtentCucumberListener"},
    monochrome = true
)

//@CucumberOptions(
//		features = "src/test/resources/features/End_to_End_social_comments_flow.feature",
//	    glue = {"social_comments"},
//    plugin = {
//    		"pretty",
//    		"html:target/Social-report.html",
//    		"json:target/cucumber.json",
//    		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
//    		
//    		},
//    monochrome = true
//)



public class EndtoEndRunner {}
