package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SideNavigation {

	WebDriver driver;

	@FindBy(how = How.LINK_TEXT, using = "Dashboard")
	WebElement dashboardPage;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Bank & Cash')]")
	WebElement bankCashModule;
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'New Account')]")
	WebElement newAccountPage;

	public SideNavigation(WebDriver driver) {
		this.driver = driver;

	}

	public void goToNewAccountPage() throws InterruptedException {
		bankCashModule.click();
		Thread.sleep(2000);
		newAccountPage.click();
		Thread.sleep(1000);

	}

	
		
	

}
