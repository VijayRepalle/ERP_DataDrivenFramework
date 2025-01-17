package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import config.AppUtils;
import utilities.ExcelFileUtil;

public class AppTest extends AppUtils
{
	String inputpath="./FileInput/LoginData.xlsx";
	String outputpath="./FileOutput/DataDrivenResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	@Test
	public void startTest() throws Throwable
	{
		// define path of html
		report= new ExtentReports("./target/Reports/DataDriven.html");
		
	 //create object for excelfile util class
		ExcelFileUtil xl= new ExcelFileUtil(inputpath);
		// count no of rows 
	 int rc =xl.rowCount("Login");
	  Reporter.log("No Of Rows are ::"+rc,true);
	  for( int i=1;i<=rc;i++)
	  {
		logger = report.startTest("Validate Login");
		  String user= xl.getCellData("Login", i, 0);
		  String pass= xl.getCellData("Login", i, 1);
		  // call admin login method from function library class
	 boolean res = FunctionLibrary.adminLogin(user, pass);
		  if(res)
		  {
			//write as login success in result cell
			  xl.setCellData("Login", i, 2, "Login Success", outputpath);
			  // write as pass into status cell
			  xl.setCellData("Login", i, 3, "pass", outputpath);
			  logger.log(LogStatus.PASS, "valid username and password");
		  }
		  else
		  {
			  // take screenshot and store
			  File screen= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			 // cpoy screenshot in local system
			  FileUtils.copyFile(screen, new File("./Screenshot/Iteration/"+i+"Loginpage.png"));
			  		
			  //write as login fail in result cell
			  xl.setCellData("Login", i, 2, "Login Fail", outputpath);
			  // write as fail into status cell
			  xl.setCellData("Login", i, 3, "fail", outputpath);
			  logger.log(LogStatus.FAIL, "Invalid username and password");
		  }
	  }
	  report.endTest(logger);
	  report.flush();
	 
	}
	

}
