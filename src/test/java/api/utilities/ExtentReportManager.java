package api.utilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

	private static ExtentReports extent;
	private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

	private ExtentReportManager() {
	}

	public static synchronized ExtentReports getInstance() {
		if (extent == null) {
			extent = createExtentReports();
		}
		return extent;
	}

	public static void createTest(String testName) {
		ExtentTest test = getInstance().createTest(testName);
		EXTENT_TEST.set(test);
	}

	public static ExtentTest getTest() {
		return EXTENT_TEST.get();
	}

	public static void pass(String message) {
		ExtentTest test = getTest();
		if (test != null) {
			test.pass(message);
		}
	}

	public static void info(String message) {
		ExtentTest test = getTest();
		if (test != null) {
			test.info(message);
		}
	}

	public static void fail(String message) {
		ExtentTest test = getTest();
		if (test != null) {
			test.fail(message);
		}
	}

	public static void fail(Throwable throwable) {
		ExtentTest test = getTest();
		if (test != null) {
			test.fail(throwable);
		}
	}

	public static void skip(String message) {
		ExtentTest test = getTest();
		if (test != null) {
			test.skip(message);
		}
	}

	public static void unloadTest() {
		EXTENT_TEST.remove();
	}

	public static synchronized void flushReports() {
		if (extent != null) {
			extent.flush();
		}
	}

	private static ExtentReports createExtentReports() {
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
		Path reportDirectory = Paths.get("test-output", "extent-report");
		Path reportFile = reportDirectory.resolve("ExtentReport_" + timestamp + ".html");

		try {
			Files.createDirectories(reportDirectory);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create Extent report directory", e);
		}

		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFile.toString());
		sparkReporter.config().setDocumentTitle("REST API Automation Report");
		sparkReporter.config().setReportName("Rest Assured Test Execution");

		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(sparkReporter);
		extentReports.setSystemInfo("Framework", "Rest Assured + TestNG");
		extentReports.setSystemInfo("Environment", "QA");

		return extentReports;
	}


}
