package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features/End_to_End_social_comments_flow.feature",
    glue = {"social_comments"},
    plugin = {"pretty", "reports.ExtentCucumberListener"},
    monochrome = true
)
public class EndtoEndRunner {}
