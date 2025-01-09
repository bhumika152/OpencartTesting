package Utilities;
import java.io.IOException;
import org.testng.annotations.DataProvider;
public class DataProviderDemo {
	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {
	    String path = ".\\testData\\data.xlsx"; // Path to Excel file
	    Excel_utils xlutil = new Excel_utils(path); // Creating an object of the utility class

	    int totalRows = xlutil.getRowCount("Sheet1"); // Total rows in the Excel sheet
	    int totalCols = xlutil.getCellCount("Sheet1", 1); // Total columns in the Excel sheet

	    String[][] loginData = new String[totalRows][totalCols]; // 2D array to store data

	    // Loop through rows and columns to read data
	    for (int i = 1; i <= totalRows; i++) { // Start from 1 to skip the header row
	        for (int j = 0; j < totalCols; j++) {
	            loginData[i - 1][j] = xlutil.getCellData("Sheet1", i, j); // Populate the array
	        }
	    }

	    return loginData; // Return the 2D array
	}
}
