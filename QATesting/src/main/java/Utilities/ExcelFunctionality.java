package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFunctionality {

	public String TestCaseID;
	public String TestCaseDesc;
	public String Environment;
	public String Browser = null;
	public String URL = null;
	public String Title;
	public String FirstName;
	public String LastName;
	public String PhoneNumber;
	public String ZipCode;
	public String PhysicalCond;
	public String MechCond;
	public String TireCond;
	public String Vin;
	public String Mileage;
	public String FinishTxt;
	public String Text;
	public String VinII;
	
	XSSFWorkbook wb;
	XSSFSheet sheet;

	public void getData(String TestCaseId,String file) throws FileNotFoundException, IOException
	{
		File src=new File(file);
		 FileInputStream fis=new FileInputStream(src);
		  wb=new XSSFWorkbook(fis);
		  int currentSheetNo=0;
		 XSSFSheet currentSheet= wb.getSheetAt(currentSheetNo);
		 int rowNo =-1;
		 int lastRowNo = GetRowCount(currentSheetNo);
		 String rowData;
		try {
			 
			 for(int i=0; i<lastRowNo;i++)
			 {
				 rowData = currentSheet.getRow(i).getCell(0).getStringCellValue();
				 if(rowData.equals(TestCaseId))
				 {
					 rowNo =i;
					 break;
				 }
			 }
			 
			 	TestCaseID = GetCellData(currentSheetNo,rowNo,0);
			    TestCaseDesc = GetCellData(currentSheetNo,rowNo,1);
				Environment = GetCellData(currentSheetNo,rowNo,2);
				Browser = GetCellData(currentSheetNo,rowNo,3);
				URL = GetCellData(currentSheetNo,rowNo,4);
				Title = GetCellData(currentSheetNo,rowNo,5);
				FirstName = GetCellData(currentSheetNo,rowNo,6);
				LastName = GetCellData(currentSheetNo,rowNo,7);
				PhoneNumber = GetCellData(currentSheetNo,rowNo,8);
				ZipCode = GetCellData(currentSheetNo,rowNo,9);
				
									
				Log.info("TestCaseID:"+TestCaseID);
				Log.info("TestCaseDesc: "+TestCaseDesc);
				Log.info("Environment: "+Environment);
				Log.info("Browser: "+Browser);
				Log.info("URL: "+URL);
				Log.info("Title: "+Title);
				Log.info("FirstName: "+FirstName);
				Log.info("LastName: "+LastName);
				Log.info("Phone number: "+PhoneNumber);
				Log.info("ZipCode: "+ZipCode);
				
				Vin = GetCellData(currentSheetNo,rowNo,12);
				Mileage = GetCellData(currentSheetNo,rowNo,13);
				Log.info("Vin: "+Vin);
				Log.info("Mileage: "+Mileage);
				FinishTxt = GetCellData(currentSheetNo, rowNo, 14);
				Text = GetCellData(currentSheetNo,rowNo,15);
				VinII = GetCellData(currentSheetNo,rowNo,16);
				
			  }
		
		catch (NullPointerException e)
		{
			System.out.println(" ");
		}
	}

	public String GetCellData(int sheetNo, int row, int column) {

		try {

			sheet = wb.getSheetAt(sheetNo);
			XSSFRow currentrow = sheet.getRow(row);
			if (currentrow == null)
				return "";

			XSSFCell Cell = currentrow.getCell(column);
			if (Cell == null)
				return "";
			 CellType cellType = currentrow.getCell(column).getCellTypeEnum();
	            switch (cellType)
	            {
	            case  STRING:
	            return Cell.getStringCellValue();               

	            case  BOOLEAN:
	            return String.valueOf(Cell.getBooleanCellValue());          

	            case  BLANK:
	            return "";      

	            case  ERROR:
	            return Cell.getStringCellValue();           

	            case  NUMERIC:
	            return String.valueOf(Cell.getNumericCellValue());          

	            default:
	            return "Cell not found";        

			}
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + row + " or column " + column + " does not exist in xls";
		}

	}

	public ExcelFunctionality() {

	}

	public ExcelFunctionality(String path) {
		try {
			File src = new File(path);
			FileInputStream fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public int GetRowCount(int sheetNo) {
		int rowCount = wb.getSheetAt(sheetNo).getLastRowNum();
		rowCount = rowCount + 1;
		return rowCount;
	}

	public int GetColumnCount(int sheetNo) {
		return wb.getSheetAt(sheetNo).getRow(0).getLastCellNum();
	}

}
