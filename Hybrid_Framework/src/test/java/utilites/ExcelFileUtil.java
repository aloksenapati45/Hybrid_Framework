package utilites;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
	XSSFWorkbook wb;

	// get a file using constructor
	public ExcelFileUtil(String filePath) throws Exception {
		FileInputStream fi = new FileInputStream(filePath);
		wb = new XSSFWorkbook(fi);
	}

	// count all row in a sheet
	public int rowCount(String SheetName) {
		return wb.getSheet(SheetName).getLastRowNum();
	}

	// methods for reading data in cell
	public String getCellData(String sheetName, int row, int cell) {
		String data = "";
		if (wb.getSheet(sheetName).getRow(row).getCell(cell).getCellType() == CellType.NUMERIC) {
			int cellData = (int) wb.getSheet(sheetName).getRow(row).getCell(cell).getNumericCellValue();
			data = String.valueOf(cellData);
		} else {
			data = wb.getSheet(sheetName).getRow(row).getCell(cell).getStringCellValue();
		}
		return data;
	}

	//method for writing data
	public void setCellData(String sheetName,int row,int column,String status,String WriteExcel)throws Throwable
	{
		//get sheet from wb
		XSSFSheet ws =wb.getSheet(sheetName);
		//get row from sheet
		XSSFRow rowNum =ws.getRow(row);
		//create cell
		XSSFCell cell =rowNum.createCell(column);
		//write status
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("PASS"))
		{
			XSSFCellStyle style =wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("FAIL"))
		{
			XSSFCellStyle style =wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			XSSFCellStyle style =wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);
		}
}
