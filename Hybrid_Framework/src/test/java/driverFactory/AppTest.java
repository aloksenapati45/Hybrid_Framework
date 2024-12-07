package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import commonFunctions.FunctionLibrary;
import utilites.ExcelFileUtil;

public class AppTest {// starting of class
	String fileinput = "./FileInput/DataEngine.xlsx";
	String fileoutput = "./FileOutput/Hybrid_Result.xlsx";
	String msheet = "MasterSheet";
	WebDriver driver;

	@Test
	public void startTest() throws Throwable {// starting of startTest method
		String module_Status = "";
		String module_new = "";
		ExcelFileUtil xl = new ExcelFileUtil(fileinput);
		// count all row of masterSheet
		int rc = xl.rowCount(msheet);
		// read all cell in masterSheet
		for (int i = 1; i <= rc; i++) {// starting of for loop
			String ModuleName = xl.getCellData(msheet, i, 1);
			String ExecutionStatus = xl.getCellData(msheet, i, 2);
			if (ExecutionStatus.equalsIgnoreCase("Y")) {// starting of if block
				int irc = xl.rowCount(ModuleName);
				for (int j = 1; j <= irc; j++) {// starting of inner for loop
					String ObjectType = xl.getCellData(ModuleName, j, 1);
					String LocaterType = xl.getCellData(ModuleName, j, 2);
					String LocaterVaue = xl.getCellData(ModuleName, j, 3);
					String TestData = xl.getCellData(ModuleName, j, 4);
					try {
						if (ObjectType.equalsIgnoreCase("startBrowser")) {
							driver = FunctionLibrary.startBrowser();
						}
						if (ObjectType.equalsIgnoreCase("openurl")) {
							FunctionLibrary.openUrl();
						}
						if (ObjectType.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(LocaterType, LocaterVaue, TestData);
						}
						if (ObjectType.equalsIgnoreCase("typeaction")) {
							FunctionLibrary.typeAction(LocaterType, LocaterVaue, TestData);
						}
						if (ObjectType.equalsIgnoreCase("clickaction")) {
							FunctionLibrary.clickAction(LocaterType, LocaterVaue);
						}
						if (ObjectType.equalsIgnoreCase("validateUrl")) {
							FunctionLibrary.validateUrl(TestData);
						}
						if (ObjectType.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
						}
						if (ObjectType.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(LocaterType, LocaterVaue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("dropdownList")) {
							FunctionLibrary.dropdownList(LocaterType, LocaterVaue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("printStockNumber"))
						{
							FunctionLibrary.printStockNumber(LocaterType, LocaterVaue);
						}
						if(ObjectType.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
						}
						if(ObjectType.equalsIgnoreCase("printSuplierNum"))
						{
							FunctionLibrary.printSuplierNum(LocaterType, LocaterVaue);
						}
						if(ObjectType.equalsIgnoreCase("supliersTable"))
						{
							FunctionLibrary.supliersTable();
						}
						if(ObjectType.equalsIgnoreCase("printCustomerNum"))
						{
							FunctionLibrary.printCustomerNum(LocaterType, LocaterVaue);
						}
						if(ObjectType.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable();
						}
						// write as pass in sheet
						xl.setCellData(ModuleName, j, 5, "PASS", fileoutput);
						module_Status = "True";
					} catch (Exception e) {
						System.out.println(e.getMessage());
						// write as fail in sheet
						xl.setCellData(ModuleName, j, 5, "FAIL", fileoutput);
						module_new = "False";
					}
					if (module_Status.equalsIgnoreCase("True")) {
						xl.setCellData(msheet, i, 3, "Pass", fileoutput);
					}
					if(module_new.equalsIgnoreCase("False"))
					{
						xl.setCellData(msheet, i, 3, "Fail", fileoutput);
					}
				} // end of inner for loop
			} // end of if block
			else {// start of else block
				xl.setCellData(msheet, i, 3, "Blocked", fileoutput);
			} // end of else block
		} // end of for loop
	}// end of startTest method
}// ending of class
