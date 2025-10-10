package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

//    public synchronized static ExtentReports getInstance() {
//        if (extent == null) {
//            createInstance("target/extent-report.html");
//        }
//        return extent;
//    }
//
//    public static ExtentReports createInstance(String fileName) {
//        ExtentSparkReporter spark = new ExtentSparkReporter(fileName);
//        spark.config().setTheme(Theme.STANDARD);
//        spark.config().setDocumentTitle("API Automation Report");
//        spark.config().setReportName("API Test Execution");
//
//        extent = new ExtentReports();
//        extent.attachReporter(spark);
//
//        extent.setSystemInfo("Project", "Social Comments API");
//        extent.setSystemInfo("Tester", "Chetan Patil");
//        extent.setSystemInfo("OS", System.getProperty("os.name"));
//        
//
//        return extent;
//    }
//
//    public static void flushReports() {
//        if (extent != null) {
//            extent.flush();
//        }
//    }
//}
    private static final String REPORT_PATH = "target/Social_comments.html";

    // get the single ExtentReports instance (lazy init)
    public synchronized static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_PATH);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("API Automation Project");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Chetan Patil");
            extent.setSystemInfo("Project", "API Automation");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extent;
    }

    // flush at end of run
    public synchronized static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
