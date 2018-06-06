package PageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import Utilities.Log;
import Utilities.UtilCommon;

public class GoogleExtensionsPage extends UtilCommon {

	// Google Extensions Page
	public By SearchBox = By.xpath("//input[@id='searchInput']");
	public By Searchtxt = By.xpath("//*[@id=\"searchbox-input\"]");
	public By DetailsButton = By.xpath("//paper-button[@id='details-button']");
	public By DetailsBtn = By.xpath("/html/body/div[4]/div[4]/div[2]/div[1]/div/div[1]/div[2]/div[3]/a/div[3]");

	public By SettingsButton = By.xpath("//div[@title='Settings']");
	public By Touring = By.xpath("//div[@id='sidebar']//div[text()='Touring']");
	public By Other = By.xpath("//div[@id='sidebar']//div[text()='Other']");
	public By Sportbikes = By.xpath("//div[@id='sidebar']//div[text()='Sportbikes']");
	public By ThreeWheelCycle = By.xpath("//div[@id='sidebar']//div[text()='3 Wheel Cycle']");
	public By MotoCross = By.xpath("//div[@id='sidebar']//div[text()='Moto Cross']");
	public By RoadTrail = By.xpath("//div[@id='sidebar']//div[text()='Road/Trail']");
	public By UtilityATV = By.xpath("//div[@id='sidebar']//div[text()='Utility ATV']");
	public By SportSidebySide = By.xpath("//div[@id='sidebar']//div[text()='Sport Side by Side']");
	public By Scooter = By.xpath("//div[@id='sidebar']//div[text()='Scooter']");
	public By UtilitySidebySide = By.xpath("//div[@id='sidebar']//div[text()='Utility Side by Side']");
	public By SportTouring = By.xpath("//div[@id='sidebar']//div[text()='Sport Touring']");
	public By DirtnotMX = By.xpath("//div[@id='sidebar']//div[text()='Dirt (not MX)']");
	public By DualSport = By.xpath("//div[@id='sidebar']//div[text()='Dual Sport']");
	public By SportATV = By.xpath("//div[@id='sidebar']//div[text()='Sport ATV']");
	public By Custom = By.xpath("//div[@id='sidebar']//div[text()='Custom']");
	public By Enduro = By.xpath("//div[@id='sidebar']//div[text()='Enduro']");

	public boolean CheckSearchBox() throws InterruptedException, IOException {
		boolean IsValidDetails = true;
		try {
			Thread.sleep(4000);
			String src = driver.getPageSource();
			if (!src.contains("chrome web store")) {
				Log.error("The 'chrome web store' text not displayed");
				return false;
			}
			if (!src.contains("Search the store")) {
				Log.error("The 'Search the store' text not displayed");
				return false;
			}
			WaitforElementVisible(Searchtxt, "Check the data in search box", "CheckSearchBox", 30).isDisplayed();
			WaitforElementVisible(Searchtxt, "Enter the data in search box", "EnterSearchBox", 30).sendKeys("postman");
			WaitforElementVisible(Searchtxt, "Enter the data in search box", "EnterSearchBox", 30).sendKeys(Keys.ENTER);
			Thread.sleep(3000);
			String srce = driver.getPageSource();
			if (!srce.contains("Postman.am")) {
				Log.error("The 'Postman' Extension not displayed");
				return false;
			}
			Thread.sleep(1000);
			WaitforElementVisible(DetailsBtn, "Clicking on Details Button", "ClickDetails", 30).click();
			Thread.sleep(2000);
			WaitforElementsVisible(Searchtxt, "Click the data in search box", 30).click();
			Thread.sleep(2000);
			WaitforElementVisible(Searchtxt, "Clear the data in search box", "ClearSearchBox", 30).clear();
			WaitforElementVisible(Searchtxt, "Enter the data in search box", "EnterSearchBox", 30).sendKeys(Keys.ENTER);
			Thread.sleep(3000);

		} catch (Exception ex) {
			Log.error("Google Extension Search Test Failed" + ex);
			IsValidDetails = false;
		}
		return IsValidDetails;
	}

	public boolean CheckSettingDetails() throws InterruptedException, IOException {
		boolean IsValidDetails = true;
		try {
			Thread.sleep(2000);
			WaitforElementVisible(SettingsButton, "Check the details in Settings Button", "CheckSettingsButton", 30).isDisplayed();
			WaitforElementVisible(SettingsButton, "Click in SettingsButton", "ClickSettingsButton", 30).click();
			Thread.sleep(4000);
			String srce = driver.getPageSource();
			if (!srce.contains("My Extensions & Apps")) {
				Log.error("The 'My Extensions & Apps' button not displayed");
			}
			if (!srce.contains("Language: English") && !srce.contains("Location: India")) {
				Log.error("The 'Language: English'&'Location: India' button not displayed");
			}
			if (!srce.contains("Developer Dashboard") && !srce.contains("Privacy Policy")) {
				Log.error("The 'Developer Dashboard'&'Privacy Policy'  button not displayed");
			}
			if (!srce.contains("Terms of Service") && !srce.contains("What is the Chrome Web Store?")) {
				Log.error("The 'Terms of Service'&'What is the Chrome Web Store?'  button not displayed");
			}
			if (!srce.contains("Help") && !srce.contains("About Google")) {
				Log.error("The 'Help'&'About Google'  button not displayed");
			}
			
			Thread.sleep(4000);

		} catch (Exception ex) {
			Log.error("Google Extension Search Test Failed" + ex);
			IsValidDetails = false;
		}
		return IsValidDetails;
	}

	
}
