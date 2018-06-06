package Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class UtilCommon {
	static WebElement elementProcess;
	public static WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static String Date = DateStringGenerator();
	public static String TimeStamp = TimeStringGenerator();
	public static ITestResult result = null;
	public static String MethodName;
	public static String PATH_TO_PACKAGE = System.getProperty("user.dir");

	public void SetUp(String PathForExcel, ExcelFunctionality data)
			throws InterruptedException, FileNotFoundException, IOException {
		DOMConfigurator.configure("log4j.xml");
		String MethodName = GetMethodName();

		SetUpExtentReport("Nitheesha", "Regression", MethodName);
		System.out.println(MethodName);
		data.getData(MethodName, PathForExcel);
		Log.startTestCase(data.TestCaseDesc + " " + data.TestCaseID);
		OpenBrowser(data.Browser);
		Thread.sleep(2000);
		NavigateToURL(data.URL);

	}

	public void SetUp(String PathForExcel, ExcelFunctionality data, String methodName)
			throws InterruptedException, FileNotFoundException, IOException {
		DOMConfigurator.configure("log4j.xml");
		SetUpExtentReport("FlextrioSolutions", "Regression", methodName);
		System.out.println(methodName);
		data.getData(methodName, PathForExcel);
		Log.startTestCase(data.TestCaseDesc + " " + data.TestCaseID);
		OpenBrowser(data.Browser);
		Thread.sleep(2000);
		NavigateToURL(data.URL);

	}

	public  String GetMethodName() {
		MethodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		return MethodName;
	}

	public static String DateStringGenerator() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("MMddyy");
		String datetime = ft.format(dNow);
		return datetime;
	}

	public static String TimeStringGenerator() {
		String timeStamp = new SimpleDateFormat("HHmm").format(new Date());
		return timeStamp;
	}

	public void SetUpExtentReport(String Author, String Category, String MethodName) {
		
		extent = new ExtentReports(
				PATH_TO_PACKAGE + "\\Resources\\Reports\\" + Date + "\\Reports" + TimeStamp + ".html", false);
		extent.loadConfig(new File(PATH_TO_PACKAGE + "\\Resources\\extent-config.xml"));

		logger = extent.startTest(MethodName);
		logger.assignAuthor(Author);
		logger.assignCategory(Date);
	}

	public static String capture(String screenShotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String dest = PATH_TO_PACKAGE + "\\Resources\\Reports\\" + Date + "\\" + MethodName + "\\" + screenShotName
				+ ".png";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		return dest;
	}

	public static void UploadImage() throws InterruptedException, IOException {
		Runtime.getRuntime().exec("wscript " + PATH_TO_PACKAGE + "/Resources/Images/Upload.vbs");
	}

	public static void TakeLog(String Descprition, String ScreenshotName) throws IOException {
		if (ScreenshotName != "") {
			logger.log(LogStatus.PASS, Descprition + logger.addScreenCapture(capture(ScreenshotName)));
		} else {
			logger.log(LogStatus.PASS, Descprition);
		}
	}

	public void OpenBrowser(String browserName) throws InterruptedException, IOException {
		String driverPath = PATH_TO_PACKAGE + "\\Resources\\drivers\\";
		Log.info("Launching " + browserName + " Browser");
		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
			ChromeOptions o = new ChromeOptions();
			o.addArguments("disable-extensions");
			o.addArguments("--start-maximized");
			o.addArguments("disable-infobars");
			driver = new ChromeDriver(o);

		}
		if (browserName.equals("ie")) {
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		}
		if (browserName.equals("edge")) {
			System.out.println("EdgeTriggered");
			System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}
		if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
			driver = new FirefoxDriver();
		}
		// driver.manage().window().maximize();
		Thread.sleep(2000);
		TakeLog("Browser Launched", "Browser Launched");
	}

	public void NavigateToURL(String URL) throws InterruptedException {
		Log.info("Navigating to URL: " + URL);

		driver.navigate().to(URL);
		String strPageTitle = driver.getTitle();
		Log.info("Page title: - " + strPageTitle);

		Thread.sleep(5000);
		try {
			TakeLog("Navigating to URL: " + URL, "NavigatedURL");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void Highlight(WebElement element) {
		
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid black;');", element);
		}
   
	}

	public static void ClearHighlight(WebElement element) {

		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].setAttribute('style', 'background: none; border: none;');", element);
		}

	}
	
	public void Click(By locator) throws InterruptedException {
		Highlight(driver.findElement(locator));
		Thread.sleep(1000);
		driver.findElement(locator).click();
	}

	public void CloseCurrentWindow() {
		driver.close();
	}

	public void CloseAllWindows() {
		driver.quit();
	}

	public String StoreWindowHandle() {
		String windowHandle = driver.getWindowHandle();
		return windowHandle;
	}

	public static void SwitchToNewTab(String mainWindow1,String main) {
		
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(mainWindow1)) {
				driver.switchTo().window(handle);
				String page = driver.getCurrentUrl();
				if (main.equals(page)) {
					Log.error("Page not found");
					return;
				}
				Log.info("Current Page URL : "+page);
			}
		}
	}
	public static void SwitchToMainwindow(String mainWindow1) {
		driver.close();
		driver.switchTo().window(mainWindow1);
	}
	
	public static void SelectYear(String syear) {
		
		List<WebElement> allYears = driver.findElements(By.xpath("//select[@title='Select a year']//option"));
		for (WebElement eley : allYears) {
			String year = eley.getText();
			if (year.equalsIgnoreCase(syear)) {
				eley.click();
				break;
			}
		}		
	}
	
	public static void SelectMonth(String smonth) {

		List<WebElement> allMonths = driver.findElements(By.xpath("//select[@title='Select a month']//option"));
		for (WebElement elem : allMonths) {
			String mon = elem.getText();
			if (mon.equalsIgnoreCase(smonth)) {
				elem.click();
				break;
			}
		}
	}
	
	public static void SelectDate(String sdate) {

		List<WebElement> allDates = driver.findElements(By.xpath("//table[@class='picker__table']//td"));
		
		for (WebElement eled : allDates) {
			String date = eled.getText();
			if (date.equalsIgnoreCase(sdate)) {
				eled.click();
				break;
			}
		}
		
	}
	
	public static void SwitchToWindow(String Title) {
		String NewWindowHandle = "";
		// java.util.List<String> no_of_windows = new ArrayList<String>();
		Set<String> no_of_windows = driver.getWindowHandles();
		for (String eachWindow : no_of_windows) {
			if (driver.switchTo().window(eachWindow).getTitle().contains(Title)) {
				NewWindowHandle = eachWindow;
				break;
			}
		}
		driver.switchTo().window(NewWindowHandle);
	}

	public void SelectDropdownByText(By locator, String text) {
		Select selectElement = new Select(driver.findElement(locator));
		selectElement.selectByVisibleText(text);
	}

	public void SelectDropdownByValue(By locator, String value) {
		Select selectElement = new Select(driver.findElement(locator));
		selectElement.selectByValue(value);
	}

	public void SelectDropdownByIndex(By locator, int index) {
		Select selectElement = new Select(driver.findElement(locator));
		selectElement.selectByIndex(index);
	}

	public void HandleAlert(String msg) {
		Alert objAlert = driver.switchTo().alert();
		if (msg.equalsIgnoreCase("Accept")) {
			objAlert.accept();
		} else if (msg.equalsIgnoreCase("Dismiss")) {
			objAlert.accept();
		}
	}

	public String AlertText() {
		Alert objAlert = driver.switchTo().alert();
		objAlert = driver.switchTo().alert();
		return objAlert.getText();
	}

	public void MouseHover(By locator) {
		Actions builder = new Actions(driver);
		Action mouseOverElement = builder.moveToElement(driver.findElement(locator)).build();
		mouseOverElement.perform();
	}

	public void DragAndDrop(By locatorSource, By locatorDestinaion) {
		Actions builder = new Actions(driver);
		Action dragAndDropElement = builder
				.dragAndDrop(driver.findElement(locatorSource), driver.findElement(locatorDestinaion)).build();
		dragAndDropElement.perform();
	}

	public void DoubleClick(By locator) {
		Actions builder = new Actions(driver);
		Action doubleClickElement = builder.doubleClick(driver.findElement(locator)).build();
		doubleClickElement.perform();
	}

	public void ScrollDownToPage() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollIntoView(By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(locator));
	}

	public void MouseHoverJavaScript() {

	}

	public static WebElement WaitforElementVisible(By locator, String stepDescription, String ScreenShot,
			int timeoutinSec) throws InterruptedException, IOException {
		elementProcess = driver.findElement(locator);
		for (int i = 0; i < timeoutinSec; i++) {
			if (elementProcess.isDisplayed()) {
				break;
			} else {
				Thread.sleep(1000);
			}
		}
		Highlight(elementProcess);
		Log.info(stepDescription);
		TakeLog(stepDescription, ScreenShot);
		ClearHighlight(elementProcess);
		return elementProcess;
	}

	public static WebElement WaitforElementsVisible(By locator, String stepDescription, int timeoutinSec)
			throws InterruptedException, IOException {
		elementProcess = driver.findElement(locator);
		for (int i = 0; i < timeoutinSec; i++) {
			if (elementProcess.isDisplayed()) {
				break;
			} else {
				Thread.sleep(1000);
			}

		}
		Highlight(elementProcess);
		Log.info(stepDescription);
		ClearHighlight(elementProcess);
		return elementProcess;
	}

	public static WebElement WaitforElementVisibles(By locator, String stepDescription, int timeoutinSec)
			throws InterruptedException, IOException {
		elementProcess = driver.findElement(locator);
		for (int i = 0; i < timeoutinSec; i++) {
			if (elementProcess.isDisplayed()) {
				break;
			} else {
				Thread.sleep(1000);
			}
		}
		Log.info(stepDescription);
		return elementProcess;
	}

	public static void captureScreenShot(String path, String imgName) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", driver.findElement(By.xpath("//html")),
				"color: black; border: 3px solid yellow;");
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			if (imgName != "") {
				FileUtils.copyFile(src, new File("C:/Selenium_Net/" + path + "/" + imgName + ".png"));
			} else {
				FileUtils.copyFile(src,
						new File("C:/Selenium_Net/" + path + "/" + System.currentTimeMillis() + ".png"));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", driver.findElement(By.xpath("//html")),
				"");
	}

	public void TakeLogFail(String Descprition, String ScreenshotName) throws IOException {
		logger.log(LogStatus.FAIL, Descprition + logger.addScreenCapture(capture(ScreenshotName)));
	}

	public void CloseReport() throws IOException {
		extent.endTest(logger);
		extent.flush();
	}

	public void CloseReportFile() {
		extent.close();
	}

	public void CloseBrowser() {
		logger.log(LogStatus.INFO, "Browser Closed");
		Log.info("browser closing");
		driver.quit();
	}

	public void CloseBrowser(ExcelFunctionality data) {
		Log.endTestCase(data.TestCaseDesc + " " + data.TestCaseID);
		driver.quit();
	}
}

