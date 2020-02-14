package test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import test.BaseTest;
import page.NewAccountPage;
import page.SideNavigation;
import page.LoginPage;
import util.BrowserFactory;

public class NewAccountTest extends BaseTest {

	WebDriver driver;

	@BeforeTest
	public void initialization() {
		driver = BrowserFactory.getBrowswer();
		driver.get("http://techfios.com/test/billing/?ng=admin");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void addNewAccount() throws InterruptedException {

		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		String expectedTitle = "Login - TechFios Test Application - Billing";
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, expectedTitle, "Login Page M!Smatched ..");
		loginPage.login("techfiosdemo@gmail.com", "abc123");
		waitForElememt(driver, 10, loginPage.getDashboardPageHeaderTitleElement());
		
		SideNavigation sideNav = PageFactory.initElements(driver, SideNavigation.class);
		sideNav.goToNewAccountPage();
		Thread.sleep(1000);
		
		NewAccountPage newAccountPage = PageFactory.initElements(driver, NewAccountPage.class);
		String expectedTitleNewAccountPage = "Accounts- TechFios Test Application - Billing";
		String actualTitleNewAccountPage = newAccountPage.getNewAccountPageTitle();
		Assert.assertEquals(actualTitleNewAccountPage, expectedTitleNewAccountPage, "New Account Page M!Smatched ..");

		Random rnd = new Random();
		int rndAmount = rnd.nextInt(99);
		String rndAccountNameExpected = "Test Account " + rndAmount;
		String rndDescriptionExpected = "Test Account By Fall19 Std " + rndAmount * 2;
		String rndInitialBalance = String.valueOf(rndAmount);

		Thread.sleep(1000);
		newAccountPage.setAccountTitle(rndAccountNameExpected);
		Thread.sleep(1000);
		newAccountPage.setDescription(rndDescriptionExpected);
		newAccountPage.setInitialBalance(rndInitialBalance);
		newAccountPage.clickSubmitButton();
		Thread.sleep(2000);

		// Account create validation
		Assert.assertTrue(newAccountPage.displayAcccountCreateSuccessMessageActual().isDisplayed(),
				"Account Created Successfully Not Displayed !!");

		// Scroll to bottom need to delete account
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // js.executeScript("window.scrollTo(0,
																			// 0)"); // Scroll top
		Thread.sleep(2000);

//		Page Object Page Factory design pattern - not the same way. Issue to define WebElement with Variable @FindBy. What to do ?
		newAccountPage.getDynamicValueForAccountDeleteButton(rndAccountNameExpected).click();

		// Without delay ElementNotInteractableException comes. Disable Implicit wait
		// and Explicit wait to see this.
		// Thread.sleep(2000);
		// BrowserFactory.waitForElememt(driver, 10,
		// dashboardPage.getAccountDeleteConfirmationOkButtonElement());
		waitForElememt(driver, 10, newAccountPage.getAccountDeleteConfirmationOkButtonElement());
		newAccountPage.clickOnAccountDeleteConfirmationOkButton();

		// Validation of Account Delete Successfull by Assert and visually
		Assert.assertTrue(newAccountPage.displayAcccountDeleteSuccessMessageActual().isDisplayed(),
				"Account Delete Successfully Not Displayed !!");
		Thread.sleep(3000);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);

	}

	@AfterTest
	public void exitBrowser() {

		driver.close();
		driver.quit();

	}

}
