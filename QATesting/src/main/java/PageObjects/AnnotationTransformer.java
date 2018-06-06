package PageObjects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import Utilities.ExcelFunctionality;
import Utilities.Log;

public class AnnotationTransformer implements IAnnotationTransformer {

	String TM;
	String TM1;
	String RS;
	
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
//		System.out.println("Transformer running for " + testMethod.getName());
    	TM = testMethod.getName();
    	Log.info("Transformer running for " + TM);
		
		ExcelFunctionality util = new ExcelFunctionality("Resources\\ExcelData\\RunTestcase.xlsx");
		int rows = util.GetRowCount(0);
		int cols = util.GetColumnCount(0);
		
		for(int i=1; i<rows; i++ )
		{
			String methodName = util.GetCellData(0, i, 1);
			String runstatus = util.GetCellData(0, i, 2);
				
				if (testMethod.getName().equals(methodName.trim()) ){
					if( runstatus.equals("0.0"))
					{
						annotation.setEnabled(false);
//						System.out.println("Transformer running for " + testMethod.getName() + " Testcase Disabled : " + runstatus.replace("0.0", "0") );
						TM1 = testMethod.getName();
						Log.info("Transformer running for " + TM1);
						RS = runstatus.replace("0.0", "0");
						Log.info(" Testcase Disabled : " + RS );
					}
				}
			
		}
		
		
    }
}
