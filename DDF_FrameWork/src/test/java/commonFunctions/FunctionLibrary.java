package commonFunctions;



import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.Reporter;

import config.AppUtils;


public class FunctionLibrary extends AppUtils
{
	public static  boolean adminLogin(String username,String password)
	{
		driver.get(conpro.getProperty("Url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath(conpro.getProperty("ObjReset"))).click();
		driver.findElement(By.xpath(conpro.getProperty("Objuser"))).sendKeys(username);
		driver.findElement(By.xpath(conpro.getProperty("Objpass"))).sendKeys(password);
		driver.findElement(By.xpath(conpro.getProperty("Objlogin"))).click();
		String Expexted="dashboard";
		String Actual= driver.getCurrentUrl();
		if(Actual.contains(Expexted))
		{
			Reporter.log("Username and password are Valid:: "+Expexted+"----"+Actual,true);
			// click  logout
			driver.findElement(By.xpath(conpro.getProperty("Objlogoutlink"))).click();
		   return true;
		}
		else 
		{
			String Errormessage=driver.findElement(By.xpath(conpro.getProperty("ObjError_Message"))).getText();
			driver.findElement(By.xpath(conpro.getProperty("ObjOkbutton"))).click();
		  Reporter.log(Errormessage+"------"+Expexted+"------"+Actual,true);
		  return false;
		}
		
	}
	
}
