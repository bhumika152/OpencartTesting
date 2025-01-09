package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String repName;

    @Override
    public void onStart(ITestContext testContext) {
        // Generate a timestamp for the report name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        // Specify the location of the report
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("Opencart Automation Report"); // Title of the report
        sparkReporter.config().setReportName("Opencart Functional Testing"); // Name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system information
        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Custom");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        
        
        String os = System.getProperty("os.name");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        // Add included test groups
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        };
    }  @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        // Capture screenshot
        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " was skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush(); // Save the report
        String pathofExtentReport =System.getProperty("user.dir")+"\\reports\\"+repName; 
        File extentReport =new File(pathofExtentReport); 
        try {
        	Desktop.getDesktop().browse(extentReport.toURI());
        	} 
        catch (IOException e) {
        		 e.printStackTrace(); }
    
//    try {
//        // Construct the report URL
//        URL reportUrl = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + repName);
//
//        // Open the report in the default browser
//        try {
//            Desktop.getDesktop().browse(new File(reportUrl.getPath()).toURI());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Configure the email
//        ImageHtmlEmail email = new ImageHtmlEmail();
//        email.setDataSourceResolver(new DataSourceUrlResolver(reportUrl));
//        email.setHostName("smtp.googlemail.com");
//        email.setSmtpPort(465);
//        email.setSSLOnConnect(true);
//        email.setAuthenticator(new DefaultAuthenticator("braheja248@gmail.com", "@Bhumika_15")); // Replace with actual credentials
//
//        email.setFrom("braheja248@gmail.com"); // Sender's email
//        email.setSubject("Test Results");
//        email.setMsg("Please find the attached report.");
//        email.addTo("bhawnadiya15@gmail.com"); // Receiver's email
//
//        // Attach the report
//        email.attach(reportUrl, "Extent Report", "Please check the report...");
//
//        // Send the email
//        email.send();
//        System.out.println("Email sent successfully.");
//
//    } catch (IOException | EmailException e) {
//        e.printStackTrace();
//    }
}
}