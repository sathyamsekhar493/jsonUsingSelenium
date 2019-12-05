package jsonurl;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class crmproLogin {
	WebDriver driver;

	@BeforeMethod
	public void launch() throws IOException, ParseException {
		JSONData e = new JSONData();

		driver = new ChromeDriver();
		driver.get(e.readJSONData("URL"));
		driver.manage().window().maximize();
	}

	
	public void login() throws IOException, ParseException {
		JSONData e = new JSONData();

		driver.findElement(By.name("username")).sendKeys(e.readJSONData("username"));
		driver.findElement(By.name("password")).sendKeys(e.readJSONData("password"));
		driver.findElement(By.xpath("//*[@value='Login']")).click();

	}

}
