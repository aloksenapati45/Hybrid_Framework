package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties property;

//start of startBrowser method
	public static WebDriver startBrowser() throws Exception {
		property = new Properties();
		property.load(new FileInputStream("PropertyFile/Hybrid.properties"));
		if (property.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (property.getProperty("Browser").equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
		} else if (property.getProperty("Browser").equalsIgnoreCase("Microsoftedge")) {
			driver = new EdgeDriver();
		} else {
			Reporter.log("please provide browser name in property file", true);
		}
		return driver;
	}

	// end of startBrowser method
//start of openUrl method
	public static void openUrl() {
		driver.get(property.getProperty("url"));
	}

	// end of openUrl method
	// start of waitForElement method
	public static void waitForElement(String LType, String LValue, String TestData) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if (LType.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LValue)));
		}
		if (LType.equalsIgnoreCase("id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LValue)));
		}
		if (LType.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LValue)));
		}
	}

	// end of waitForElement method
	// start of typeAction method
	public static void typeAction(String LType, String LValue, String TestData) {
		if (LType.equalsIgnoreCase("Xpath")) {
			driver.findElement(By.xpath(LValue)).clear();
			driver.findElement(By.xpath(LValue)).sendKeys(TestData);
		}
		if (LType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LValue)).clear();
			driver.findElement(By.name(LValue)).sendKeys(TestData);
		}
		if (LType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LValue)).clear();
			driver.findElement(By.id(LValue)).sendKeys(TestData);
		}
	}

	// end of typeAction method
	// start of clickAction method
	public static void clickAction(String LType, String LValue) {
		if (LType.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LValue)).click();
		}
		if (LType.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LValue)).click();
		}
		if (LType.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LValue)).sendKeys(Keys.ENTER);
		}
	}

	// end of clickAction method
	// start of validateUrl method
	public static void validateUrl(String TestData) {
		if (driver.getTitle().equalsIgnoreCase(TestData)) {
			Reporter.log("You are now in currect page..........." + driver.getTitle(), true);
		} else
			Reporter.log("you are in another url", true);
	}

	// end of validateUrl method
	// start of closeBrowser method
	public static void closeBrowser() {
		driver.quit();
	}

	// end of closeBrowser method
	public static void dropdownList(String LType, String LValue, String TestData) {
		int indexno = Integer.parseInt(TestData);
		if (LType.equalsIgnoreCase("Xpath")) {
			Select element = new Select(driver.findElement(By.xpath(LValue)));
			element.selectByIndex(indexno);
		}
		if (LType.equalsIgnoreCase("id")) {
			Select element = new Select(driver.findElement(By.id(LValue)));
			element.selectByIndex(indexno);
		}
		if (LType.equalsIgnoreCase("name")) {
			Select element = new Select(driver.findElement(By.name(LValue)));
			element.selectByIndex(indexno);
		}
	}

	public static void printStockNumber(String LType, String LValue) throws Throwable {
		String writer1 = "";
		if (LType.equalsIgnoreCase("Xpath")) {
			writer1 = driver.findElement(By.xpath(LValue)).getAttribute("Value");
		}
		if (LType.equalsIgnoreCase("id")) {
			writer1 = driver.findElement(By.id(LValue)).getAttribute("Value");
		}
		if (LType.equalsIgnoreCase("name")) {
			writer1 = driver.findElement(By.name(LValue)).getAttribute("Value");
		}
		FileWriter writer = new FileWriter("./CaptureData/StockNum.txt");
		BufferedWriter bw = new BufferedWriter(writer);
		bw.write(writer1);
		bw.flush();
		bw.close();
	}

	public static void stockTable() throws Throwable {
		FileReader fr = new FileReader("./CaptureData/ResultData.txt");
		BufferedReader br = new BufferedReader(fr);
		String exp_Value = br.readLine();
		if (!driver.findElement(By.xpath(property.getProperty("Enter_value"))).isDisplayed())
			driver.findElement(By.xpath(property.getProperty("search_Button"))).click();
		driver.findElement(By.xpath(property.getProperty("Enter_value"))).clear();
		driver.findElement(By.xpath(property.getProperty("Enter_value"))).sendKeys(exp_Value);
		driver.findElement(By.xpath(property.getProperty("enter_search_Button"))).click();
		Thread.sleep(5000);
		String act_Value = driver
				.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(act_Value + "      " + exp_Value, true);
		try {
			Assert.assertEquals(act_Value, exp_Value, "Stock number's are not matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
		br.close();
	}

	public static void printSuplierNum(String LType, String LValue) throws Exception {
		String suplierNum = "";
		if (LType.equalsIgnoreCase("Xpath")) {
			suplierNum = driver.findElement(By.xpath(LValue)).getAttribute("Value");
		}
		if (LType.equalsIgnoreCase("id")) {
			suplierNum = driver.findElement(By.id(LValue)).getAttribute("Value");
		}
		if (LType.equalsIgnoreCase("name")) {
			suplierNum = driver.findElement(By.name(LValue)).getAttribute("Value");
		}
		FileWriter fw = new FileWriter("./CaptureData/SuplierNum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(suplierNum);
		bw.flush();
		bw.close();
	}
	public static void supliersTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/SuplierNum.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Res = br.readLine();
		if(!driver.findElement(By.xpath(property.getProperty("Enter_value"))).isDisplayed())
			driver.findElement(By.xpath(property.getProperty("search_Button"))).click();
		driver.findElement(By.xpath(property.getProperty("Enter_value"))).clear();
		driver.findElement(By.xpath(property.getProperty("Enter_value"))).sendKeys(Exp_Res);
		driver.findElement(By.xpath(property.getProperty("enter_search_Button"))).click();
		Thread.sleep(3000);
		String Act_Res = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Act_Res+"     "+Exp_Res,true);
		try {
			Assert.assertEquals(Act_Res, Exp_Res, "both result's are not matched");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
		br.close();
	}
	public static void printCustomerNum(String LType,String LValue) throws Throwable
	{
		String CustomerNum = "";
		if (LType.equalsIgnoreCase("Xpath")) {
			CustomerNum = driver.findElement(By.xpath(LValue)).getAttribute("Value");
		}
		if (LType.equalsIgnoreCase("id")) {
			CustomerNum = driver.findElement(By.id(LValue)).getAttribute("Value");
		}
		if (LType.equalsIgnoreCase("name")) {
			CustomerNum = driver.findElement(By.name(LValue)).getAttribute("Value");
		}
		FileWriter fw = new FileWriter("./CaptureData/CustomerNum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(CustomerNum);
		bw.flush();
		bw.close();
	}
	public static void customerTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/CustomerNum.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Res = br.readLine();
		if(!driver.findElement(By.xpath(property.getProperty("Enter_value"))).isDisplayed())
			driver.findElement(By.xpath(property.getProperty("search_Button"))).click();
		driver.findElement(By.xpath(property.getProperty("Enter_value"))).clear();
		driver.findElement(By.xpath(property.getProperty("Enter_value"))).sendKeys(Exp_Res);
		driver.findElement(By.xpath(property.getProperty("enter_search_Button"))).click();
		Thread.sleep(3000);
		String Act_Res = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Act_Res+"     "+Exp_Res,true);
		try {
			Assert.assertEquals(Act_Res, Exp_Res, "both result's are not matched");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
		br.close();
	}
}
