package jsonurl;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class crmproregistration extends crmproLogin {
	
	
	@BeforeMethod
	public void launch() throws IOException, ParseException {
		JSONData e = new JSONData();

		driver = new ChromeDriver();
		driver.get(e.readJSONData("RegisterUrl"));
		driver.manage().window().maximize();
	}

	
	@Test
	public void Registration() throws IOException, ParseException {

		JSONData e = new JSONData();
		
		Select Edition= new Select(driver.findElement(By.name("payment_plan_id")));
		Edition.selectByVisibleText(e.readJSONData("Edition"));
		driver.findElement(By.name("first_name")).sendKeys(e.readJSONData("FirstName"));
		driver.findElement(By.name("surname")).sendKeys(e.readJSONData("LastName"));
		driver.findElement(By.name("email")).sendKeys(e.readJSONData("Email"));
		driver.findElement(By.name("email_confirm")).sendKeys(e.readJSONData("confirm_Email"));
		driver.findElement(By.name("username")).sendKeys(e.readJSONData("Register_Username"));
		driver.findElement(By.name("password")).sendKeys(e.readJSONData("Register_Password"));
		driver.findElement(By.name("passwordconfirm")).sendKeys(e.readJSONData("Register_confirm_Password"));
		driver.findElement(By.name("agreeTerms")).click();
		
		driver.findElement(By.name("submitButton")).click();
		

	}

}
