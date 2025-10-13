//package reports;
//
//import io.cucumber.plugin.ConcurrentEventListener;
//import io.cucumber.plugin.event.*;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class ExtentCucumberListener implements ConcurrentEventListener {
//
//    private static final Map<String, ExtentTest> featureTestMap = new ConcurrentHashMap<>();
//    private static final ThreadLocal<ExtentTest> scenarioNode = new ThreadLocal<>();
//
//    public static ExtentTest getCurrentScenario() {
//        return scenarioNode.get();
//    }
//
//    @Override
//    public void setEventPublisher(EventPublisher publisher) {
//        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
//        publisher.registerHandlerFor(TestStepFinished.class, this::onTestStepFinished);
//        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
//        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
//    }
//
//    private void onTestCaseStarted(TestCaseStarted event) {
//        String featurePath = event.getTestCase().getUri().getPath();
//        String featureName = extractFeatureName(featurePath);
//        String scenarioName = event.getTestCase().getName();
//
//        ExtentReports extent = ExtentManager.getInstance();
//
//        ExtentTest featureTest = featureTestMap.computeIfAbsent(featureName,
//                fn -> extent.createTest(fn));
//
//        ExtentTest scenario = featureTest.createNode(scenarioName);
//        scenarioNode.set(scenario);
//    }
//
//    private void onTestStepFinished(TestStepFinished event) {
//        Result result = event.getResult();
//        String stepText = getStepText(event.getTestStep());
//        ExtentTest scen = scenarioNode.get();
//
//        if (scen == null) return;
//
//        switch (result.getStatus()) {
//            case PASSED:
//                scen.pass(stepText);
//                break;
//            case FAILED:
//                Throwable err = result.getError();
//                scen.fail(stepText + " : " + (err != null ? err.getMessage() : "No exception"));
//                break;
//            default:
//                scen.skip(stepText);
//        }
//    }
//
//    private void onTestCaseFinished(TestCaseFinished event) {
//        scenarioNode.remove();
//    }
//
//    private void onTestRunFinished(TestRunFinished event) {
//        ExtentManager.flushReports();
//    }
//
//    private String extractFeatureName(String featurePath) {
//        if (featurePath == null) return "UnknownFeature";
//        String[] parts = featurePath.replace("\\", "/").split("/");
//        String file = parts[parts.length - 1];
//        if (file.endsWith(".feature")) {
//            return file.substring(0, file.length() - ".feature".length());
//        }
//        return file;
//    }
//
//    private String getStepText(TestStep step) {
//        if (step instanceof PickleStepTestStep) {
//            return ((PickleStepTestStep) step).getStep().getText();
//        } else {
//            return step.getCodeLocation();
//        }
//    }
//}
//package reports;
//
//import io.cucumber.plugin.ConcurrentEventListener;
//import io.cucumber.plugin.event.*;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class ExtentCucumberListener implements ConcurrentEventListener {
//
//    private static final Map<String, ExtentTest> featureTestMap = new ConcurrentHashMap<>();
//    private static final ThreadLocal<ExtentTest> scenarioNode = new ThreadLocal<>();
//
//    public static ExtentTest getCurrentScenario() {
//        return scenarioNode.get();
//    }
//
//    @Override
//    public void setEventPublisher(EventPublisher publisher) {
//        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
//        publisher.registerHandlerFor(TestStepFinished.class, this::onTestStepFinished);
//        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
//        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
//    }
//
//    private void onTestCaseStarted(TestCaseStarted event) {
//        String featurePath = event.getTestCase().getUri().getPath();
//        String featureName = extractFeatureName(featurePath);
//        String scenarioName = event.getTestCase().getName();
//
//        // Get first tag (if exists) for grouping
//        final String tagName;
//        if (!event.getTestCase().getTags().isEmpty()) {
//            tagName = event.getTestCase().getTags().iterator().next().replace("@", "");
//        } else {
//            tagName = "Other";
//        }
//
//        ExtentReports extent = ExtentManager.getInstance();
//
//        // Group by feature name + tag
//        ExtentTest featureTest = featureTestMap.computeIfAbsent(featureName + "_" + tagName,
//                fn -> extent.createTest( " [" + tagName + "]"));
//
//        ExtentTest scenario = featureTest.createNode(scenarioName);
//        scenarioNode.set(scenario);
//    }
//
//    private void onTestStepFinished(TestStepFinished event) {
//        Result result = event.getResult();
//        String stepText = getStepText(event.getTestStep());
//        ExtentTest scen = scenarioNode.get();
//
//        if (scen == null) return;
//
//        switch (result.getStatus()) {
//            case PASSED:
//                scen.pass(stepText);
//                break;
//            case FAILED:
//                Throwable err = result.getError();
//                scen.fail(stepText + " : " + (err != null ? err.getMessage() : "No exception"));
//                break;
//            default:
//                scen.skip(stepText);
//        }
//    }
//
//    private void onTestCaseFinished(TestCaseFinished event) {
//        scenarioNode.remove();
//    }
//
//    private void onTestRunFinished(TestRunFinished event) {
//        ExtentManager.flushReports();
//    }
//
//    private String extractFeatureName(String featurePath) {
//        if (featurePath == null) return "UnknownFeature";
//        String[] parts = featurePath.replace("\\", "/").split("/");
//        String file = parts[parts.length - 1];
//        if (file.endsWith(".feature")) {
//            return file.substring(0, file.length() - ".feature".length());
//        }
//        return file;
//    }
//
//    private String getStepText(TestStep step) {
//        if (step instanceof PickleStepTestStep) {
//            return ((PickleStepTestStep) step).getStep().getText();
//        } else {
//            return step.getCodeLocation();
//        }
//    }
//}
//package reports;
//
//import io.cucumber.plugin.ConcurrentEventListener;
//import io.cucumber.plugin.event.*;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class ExtentCucumberListener implements ConcurrentEventListener {
//
//    private static final Map<String, ExtentTest> featureTestMap = new ConcurrentHashMap<>();
//    private static final ThreadLocal<ExtentTest> scenarioNode = new ThreadLocal<>();
//    private long startTime;
//
//    public static ExtentTest getCurrentScenario() {
//        return scenarioNode.get();
//    }
//
//    @Override
//    public void setEventPublisher(EventPublisher publisher) {
//        publisher.registerHandlerFor(TestRunStarted.class, this::onTestRunStarted);
//        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
//        publisher.registerHandlerFor(TestStepFinished.class, this::onTestStepFinished);
//        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
//        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
//    }
//
//    private void onTestRunStarted(TestRunStarted event) {
//        startTime = System.currentTimeMillis();
//    }
//
//    private void onTestCaseStarted(TestCaseStarted event) {
//        String featurePath = event.getTestCase().getUri().getPath();
//        String featureName = extractFeatureName(featurePath);
//        String scenarioName = event.getTestCase().getName();
//
//        // Get first tag (if exists) for grouping
//        String tagName = "Other";
//        if (!event.getTestCase().getTags().isEmpty()) {
//            tagName = event.getTestCase().getTags().iterator().next().replace("@", "");
//        }
//
//        ExtentReports extent = ExtentManager.getInstance();
//
//        // Group by feature name + tag
//        ExtentTest featureTest = featureTestMap.get(featureName + "_" + tagName);
//        if (featureTest == null) {
//            featureTest = extent.createTest(featureName + " [" + tagName + "]");
//            featureTestMap.put(featureName + "_" + tagName, featureTest);
//        }
//
//        ExtentTest scenario = featureTest.createNode(scenarioName);
//        scenarioNode.set(scenario);
//    }
//
//    private void onTestStepFinished(TestStepFinished event) {
//        Result result = event.getResult();
//        String stepText = getStepText(event.getTestStep());
//        ExtentTest scen = scenarioNode.get();
//
//        if (scen == null) return;
//
//        switch (result.getStatus()) {
//            case PASSED:
//                scen.pass(stepText);
//                break;
//            case FAILED:
//                Throwable err = result.getError();
//                scen.fail(stepText + " : " + (err != null ? err.getMessage() : "No exception"));
//                break;
//            case SKIPPED:
//                scen.skip(stepText);
//                break;
//            default:
//                scen.info(stepText);
//                break;
//        }
//    }
//
//    private void onTestCaseFinished(TestCaseFinished event) {
//        scenarioNode.remove();
//    }
//
//    private void onTestRunFinished(TestRunFinished event) {
//        long endTime = System.currentTimeMillis();
//        long durationMillis = endTime - startTime;
//
//        long seconds = (durationMillis / 1000) % 60;
//        long minutes = (durationMillis / (1000 * 60)) % 60;
//        long hours = (durationMillis / (1000 * 60 * 60)) % 24;
//
//        System.out.println(String.format("Total time: %02d:%02d:%02d", hours, minutes, seconds));
//
//        ExtentManager.flushReports();
//    }
//
//    private String extractFeatureName(String featurePath) {
//        if (featurePath == null) return "UnknownFeature";
//        String[] parts = featurePath.replace("\\", "/").split("/");
//        String file = parts[parts.length - 1];
//        if (file.endsWith(".feature")) {
//            return file.substring(0, file.length() - ".feature".length());
//        }
//        return file;
//    }
//
//    private String getStepText(TestStep step) {
//        if (step instanceof PickleStepTestStep) {
//            return ((PickleStepTestStep) step).getStep().getText();
//        } else {
//            return step.getCodeLocation();
//        }
//    }
//}
package reports;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentCucumberListener implements ConcurrentEventListener {

    private static final Map<String, ExtentTest> featureTestMap = new ConcurrentHashMap<>();
    private static final ThreadLocal<ExtentTest> scenarioNode = new ThreadLocal<>();

    private long startTime; // Track test start
    private long endTime;   // Track test end

    public static ExtentTest getCurrentScenario() {
        return scenarioNode.get();
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, this::onTestRunStarted);
        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::onTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
    }

    private void onTestRunStarted(TestRunStarted event) {
        startTime = System.currentTimeMillis();
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        String featurePath = event.getTestCase().getUri().getPath();
        String featureName = extractFeatureName(featurePath);
        String scenarioName = event.getTestCase().getName();

        // Get first tag for grouping
        final String tagName;
        if (!event.getTestCase().getTags().isEmpty()) {
            tagName = event.getTestCase().getTags().iterator().next().replace("@", "");
        } else {
            tagName = "Other";
        }

        ExtentReports extent = ExtentManager.getInstance();

        // Create or reuse feature group based on tag
        ExtentTest featureTest = featureTestMap.computeIfAbsent(tagName, fn ->
                extent.createTest(tagName)
        );

        ExtentTest scenario = featureTest.createNode(scenarioName);
        scenarioNode.set(scenario);
    }

    private void onTestStepFinished(TestStepFinished event) {
        Result result = event.getResult();
        String stepText = getStepText(event.getTestStep());
        ExtentTest scen = scenarioNode.get();

        if (scen == null) return;

        if (result.getStatus() == Status.PASSED) {
            scen.pass(stepText);
        } else if (result.getStatus() == Status.FAILED) {
            Throwable err = result.getError();
            scen.fail(stepText + " : " + (err != null ? err.getMessage() : "No exception"));
        } else {
            scen.skip(stepText);
        }
    }

    private void onTestCaseFinished(TestCaseFinished event) {
        scenarioNode.remove();
    }

    private void onTestRunFinished(TestRunFinished event) {
        endTime = System.currentTimeMillis();
        long totalTimeMillis = endTime - startTime;
        String formattedTime = formatDuration(totalTimeMillis);

        // Append total time to each tag header (like summary line)
        ExtentReports extent = ExtentManager.getInstance();
        for (ExtentTest tagTest : featureTestMap.values()) {
            tagTest.info("🕒 Total time: " + formattedTime);
        }

        ExtentManager.flushReports();
    }

    private String extractFeatureName(String featurePath) {
        if (featurePath == null) return "UnknownFeature";
        String[] parts = featurePath.replace("\\", "/").split("/");
        String file = parts[parts.length - 1];
        if (file.endsWith(".feature")) {
            return file.substring(0, file.length() - ".feature".length());
        }
        return file;
    }

    private String getStepText(TestStep step) {
        if (step instanceof PickleStepTestStep) {
            return ((PickleStepTestStep) step).getStep().getText();
        } else {
            return step.getCodeLocation();
        }
    }

    private String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }
}

//package reports;
//
//import io.cucumber.plugin.ConcurrentEventListener;
//import io.cucumber.plugin.event.*;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.TimeUnit;
//
//public class ExtentCucumberListener implements ConcurrentEventListener {
//
//    private static final Map<String, ExtentTest> featureTestMap = new ConcurrentHashMap<>();
//    private static final ThreadLocal<ExtentTest> scenarioNode = new ThreadLocal<>();
//
//    private long runStartMillis = 0L;
//
//    public static ExtentTest getCurrentScenario() {
//        return scenarioNode.get();
//    }
//
//    @Override
//    public void setEventPublisher(EventPublisher publisher) {
//        publisher.registerHandlerFor(TestRunStarted.class, this::onTestRunStarted);
//        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
//        publisher.registerHandlerFor(TestStepFinished.class, this::onTestStepFinished);
//        publisher.registerHandlerFor(TestCaseFinished.class, this::onTestCaseFinished);
//        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
//    }
//
//    private void onTestRunStarted(TestRunStarted event) {
//        runStartMillis = System.currentTimeMillis();
//    }
//
//    private void onTestCaseStarted(TestCaseStarted event) {
//        String featurePath = event.getTestCase().getUri().getPath();
//        String featureName = extractFeatureName(featurePath);
//        String scenarioName = event.getTestCase().getName();
//
//        ExtentReports extent = ExtentManager.getInstance();
//        ExtentTest featureTest = featureTestMap.computeIfAbsent(featureName, 
//            fn -> extent.createTest(featureName));
//
//        ExtentTest scenario = featureTest.createNode(scenarioName);
//        scenarioNode.set(scenario);
//    }
//
//    private void onTestStepFinished(TestStepFinished event) {
//        Result result = event.getResult();
//        String stepText = getStepText(event.getTestStep());
//        ExtentTest scen = scenarioNode.get();
//
//        if (scen == null) return;
//
//        switch (result.getStatus()) {
//            case PASSED:
//                scen.pass(stepText);
//                break;
//            case FAILED:
//                scen.fail(stepText + " : " + (result.getError() != null ? result.getError().getMessage() : "No exception"));
//                break;
//            default:
//                scen.skip(stepText);
//                break;
//        }
//    }
//
//    private void onTestCaseFinished(TestCaseFinished event) {
//        scenarioNode.remove();
//    }
//
//    private void onTestRunFinished(TestRunFinished event) {
//        long totalTimeMillis = System.currentTimeMillis() - runStartMillis;
//        String totalTimeFormatted = formatTime(totalTimeMillis);
//
//        // Log total time in last executed scenario (if exists)
//        ExtentTest scen = scenarioNode.get();
//        if (scen != null) {
//            scen.info("🕒 Total time: " + totalTimeFormatted);
//        } else if (!featureTestMap.isEmpty()) {
//            // if no scenario node available, log to last feature
//            ExtentTest lastFeature = featureTestMap.values().iterator().next();
//            lastFeature.info("🕒 Total time: " + totalTimeFormatted);
//        }
//
//        ExtentManager.flushReports();
//    }
//
//    private String extractFeatureName(String featurePath) {
//        if (featurePath == null) return "UnknownFeature";
//        String[] parts = featurePath.replace("\\", "/").split("/");
//        String file = parts[parts.length - 1];
//        if (file.endsWith(".feature")) {
//            return file.substring(0, file.length() - ".feature".length());
//        }
//        return file;
//    }
//
//    private String getStepText(TestStep step) {
//        if (step instanceof PickleStepTestStep) {
//            return ((PickleStepTestStep) step).getStep().getText();
//        } else {
//            return step.getCodeLocation();
//        }
//    }
//
//    private String formatTime(long millis) {
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
//        return String.format("%02d:%02d min", minutes, seconds);
//    }
//}
