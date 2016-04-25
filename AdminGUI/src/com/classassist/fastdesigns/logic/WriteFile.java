package com.classassist.fastdesigns.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


// http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
/**
 * 
 * @author Chase Abe
 *
 */
public class WriteFile {
	private String className;
	private String date;
	private String file;
	private String searchDate;
	private String[] studentList;
	private boolean xls;
	private boolean xlsx;
	private FileOutputStream fileOut;

	
	public WriteFile(String classname, String exportDate, String f, String search,String[] students){
		this.file = f;
		this.className = classname;
		this.date = exportDate;
		this.searchDate = search;
		this.studentList = students;
		//test();
	}
	private void test(){
		for (int i =0; i < studentList.length;i++)
			System.out.println(studentList[i]);
	}

	// ask for filetype or just create xlsx????? just creating xlsx

	@SuppressWarnings("deprecation")
	public void print(){
		File temp;
		temp = new File(file);
		boolean exists = temp.exists();
		if(exists == true)
			updateExisting();
		else
			createNewFile();
	}


	/**
	 * createNewFile()
	 * makes new files
	 */
	private void createNewFile(){
		try{
			boolean hasType = false;
			FileOutputStream fileOut;
			String extension = file.substring(file.length()-3,file.length());
			System.out.println(extension);
			// Check if user enter filename with extension
			if (extension.equals("lsx"))
				hasType = true;
			else if (extension.equals("xls"))
				hasType = true;
			else
				extension = ".xls";
			
			// Check how to name file
			if (hasType == true)
				fileOut = new FileOutputStream(file);
			else
				fileOut = new FileOutputStream(file + extension);
			
			// Check extension type if present and call method to create first column
			if (extension.equals("xls"))
				createXLS(fileOut);
			else if (extension.equals("lsx"))
				createXLSX(fileOut);
			else
				createXLS(fileOut); // default file export type is XLS 
			
			// Check extension and call method to add data to rows
			if (hasType == true)
				file = file;
			else
				file = file+".xls";
			this.updateExisting();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void createXLSX(FileOutputStream fileOut){
		XSSFWorkbook work = new XSSFWorkbook ();
		XSSFSheet sheet = work.createSheet(className);
		
		String[] data = { className, searchDate};
		String[] export = GetAttendance.getAttendance(data);
		
		XSSFRow row1 = sheet.createRow((short) 0);
		XSSFCell cellA1 = row1.createCell((short) 0);
		cellA1.setCellValue("Student Name");

		for (int i = 0; i< studentList.length;i++){
			row1 = sheet.createRow((short) i+1);
			cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue(studentList[i]);
		}
		try {
			work.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void createXLS(FileOutputStream fileOut){
		HSSFWorkbook work = new HSSFWorkbook();
		HSSFSheet sheet =  work.createSheet(className);

		String[] data = { className, searchDate};
		String[] export = GetAttendance.getAttendance(data);
		
		HSSFRow row1 = sheet.createRow((short) 0);
		HSSFCell cellA1 = row1.createCell((short) 0);
		cellA1.setCellValue("Student Name");

		for (int i = 0; i< studentList.length;i++){
			row1 = sheet.createRow((short) i+1);
			cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue(studentList[i]);
		}
		try {
			work.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * updateExisting()
	 * 
	 */
	private void updateExisting(){
		String extension;
		try {
			FileInputStream fileIn = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(fileIn);
			Sheet sheet = wb.getSheetAt(0);

			// Find out how many existing columns are in the file
			int numColumns = sheet.getRow(0).getPhysicalNumberOfCells();
			Row row = sheet.getRow(0);
			Cell cell = row.getCell(numColumns); // rows act like arrays and start at 0 

			/*__________________________________________________________________*/
			// Experimental code to prevent multiple same day columns
			Row check = sheet.getRow(0);
			if (numColumns >= 3){
				Cell previous = row.getCell(numColumns-1);
				String header = previous.getStringCellValue();
				String headDate = getDate();

				if (headDate.equals(header))
					numColumns--;
			}
			/*__________________________________________________________________*/
			//http://poi.apache.org/spreadsheet/quick-guide.html
			if (cell == null)
				cell = row.createCell(numColumns);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(getDate());

			String[] data = { className, searchDate};
			String[] export = GetAttendance.getAttendance(data);

			/* This is where the information is added to the excel file */
			for (int i = 0; i < studentList.length;i++){
				row = sheet.getRow((short) i+1); // Starts at row 2 in excel 
				cell = row.createCell((short) numColumns);
				int entry = 0;
				String first = studentList[i].toLowerCase().substring(0,1);
				int split = studentList[i].indexOf(' ');
				String rest = studentList[i].toLowerCase().substring(split+1, studentList[i].length());
				String username = first + rest;
				
				// compare student list against students found
				for (int j = 0; j < export.length; j++){
					//try{
					if(username.equals(export[j].substring(0, export[j].length())))
						entry = 1;
					if (username.equals(export[j].substring(0, export[j].length()-1)))
						entry = 1;
					if(username.equals(export[j].substring(0,export[j].length()-2)))
						entry = 1;
						
				}
				cell.setCellValue(entry); // Puts data into cell
			}
			//https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/Sheet.html
			sheet.autoSizeColumn(numColumns);

			// Write File and close out stream
			fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();


		}
		catch (FileNotFoundException | EncryptedDocumentException | InvalidFormatException e ){
			e.printStackTrace();
		} 
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Export Error: File may be in use by another program","Alert!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}


	private String getDate(){
		// https://examples.javacodegeeks.com/core-java/text/java-dateformat-example/
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		String today = format.format(curDate);
		return today;

	}
}
