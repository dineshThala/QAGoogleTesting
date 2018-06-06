package Application;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.bcel.generic.IF_ACMPEQ;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.xalan.xsltc.DOM;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.DOMConfiguration;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
//import com.thoughtworks.selenium.Wait;

import Utilities.*;
import PageObjects.*;

public class GoogleExtensions_Test extends UtilCommon {

	public ExtentReports extent;
	public ExtentTest logger;
	public String PATH_TO_PACKAGE = System.getProperty("user.dir");
	String PathForExcel = PATH_TO_PACKAGE + "\\Resources\\ExcelData\\FlextrioData.xlsx";
	ExcelFunctionality data = new ExcelFunctionality();

	GoogleExtensionsPage GoogleExtn = new GoogleExtensionsPage();

	@BeforeMethod
	public void SetUpTestData(Method method) {
		try {
			SetUp(PathForExcel, data, method.getName());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void TC001_CheckSearchBox() {
		try {
			Assert.assertTrue(GoogleExtn.CheckSearchBox(), "Google Extension Search Testcase Failed");
		} catch (Exception e) {
			Log.error("*******Please verify the code error will occured ...*******" + e);
		}
	}
	@Test
	public void TC003_CheckSettingDetails() {
		try {
			Assert.assertTrue(GoogleExtn.CheckSettingDetails(), "Google Extension Setting Details Testcase Failed");
		} catch (Exception e) {
			Log.error("*******Please verify the code error will occured ...*******" + e);
		}
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (ITestResult.FAILURE == result.getStatus()) {
			TakeLogFail("Failed at:", "FailedAt");
		}
		if (ITestResult.SUCCESS == result.getStatus()) {
			TakeLog("Successfully Passed", "Passed");
		}
		// resultScreenshot(data.TestCaseID);
		CloseReport();
		Log.info("*******SysOut: Closing Browser*******");
		CloseBrowser(data);

	}

	@AfterClass
	public void afterSuiteMethod() {
		CloseReportFile();
	}
}
