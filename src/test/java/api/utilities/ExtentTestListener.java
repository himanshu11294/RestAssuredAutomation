package api.utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        ExtentReportManager.createTest(className + " :: " + methodName);
        ExtentReportManager.info("Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.pass("Test passed");
        ExtentReportManager.unloadTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.fail("Test failed");
        if (result.getThrowable() != null) {
            ExtentReportManager.fail(result.getThrowable());
        }
        ExtentReportManager.unloadTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.skip("Test skipped");
        ExtentReportManager.unloadTest();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReports();
    }
}