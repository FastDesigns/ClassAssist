package com.classassist.fastdesigns.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.Messaging.SyncScopeHelper;
// Help from: http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
/**
 * 
 * @author Chase Abe
 * 
 */


/*
 * For both formats, middle initials are checked for.
 * If a person has a double last name separated by space a database error will 
 * occur. The code checks for spaces and replaces with a '-' dash. Not going to 
 * accommendate everyone so if a person 3 or more last names just put in dashes.
 *
 * eg. Last1 Last2, First    will be changed to      Last1-Last2, First 
 * 
 * ----------------------------------------------------------------------------
 *                                   XLS/XLSX
 * XLS/XLSX format please follow example   | denotes new cell
 * Software Engineering
 * Spring 2016   | CRN: 99999
 * 
 * Name          |Email
 * Last, First M.|user@radford.edu   
 * Last1, First  |user1@radford.edu    
 * 
 * ----------------------------------------------------------------------------
 *                                      CSV
 * CSV format please follow example
 * Software Engineering,,
 * Spring 2016,CRN: 99999,
 * ,,
 * Name,Email,
 * "Last, First M.", user@radford.edu, 
 * "Last1, First", user1@radford.edu,  
 *  
 * ----------------------------------------------------------------------------    
 */
public class ReadFile {
	private String inputFile;
	private String extension;
	private File input;
	private String classname;
	
	public ReadFile(File file, String filename, String className){
		this.input = file;
		this.inputFile = filename;
		this.extension = filename.substring(filename.length()-3, filename.length());
		this.classname = className;
	}
	
	public void setInputFile(String filename) {
		this.inputFile = filename;
		this.extension = filename.substring(filename.length()-3, filename.length());
	}

	public void read() throws IOException {
		Boolean working = false;
		FileInputStream file = new FileInputStream(input);
		// Attempt to read XLSX format
						//System.out.println(extension);
		if (extension.equals("lsx"))
			XLSX(file);
		else if (extension.equals("xls"))
			XLS(file);
		else
			CSV(file);
		
		file.close();
		
	}
	
	public void XLSX(FileInputStream file){
		try {
			XSSFWorkbook book = new XSSFWorkbook(file);
			XSSFSheet sheet = book.getSheetAt(0); // selects page to get data from
			Iterator<Row> rowIterator = sheet.iterator();
			dataHandler(rowIterator);
		}
		catch (Exception e){
			System.out.println("XLSX failed");
			e.printStackTrace();
		}
		
	}
	
	public void XLS(FileInputStream file) {
		try {
			HSSFWorkbook book = new HSSFWorkbook(file);
			HSSFSheet sheet = book.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			dataHandler(rowIterator);
		}
		catch (IOException e){
			System.out.println("XLS failed");
			e.printStackTrace();
		
		}
	}

	public void CSV(FileInputStream file) {
		int split, initial;
		String line, first, last, username;
		File csv = input;
		Scanner scan;
		SubmitClassList classlist = new SubmitClassList();
		try {
			scan = new Scanner(csv);
			//for (int i = 0; i < 4; i++){
				//scan.nextLine();
			//}
			boolean nameField = false;
			while (nameField == false){
				line = scan.nextLine();
				line = line.toLowerCase();
				split = line.indexOf("name");
				if (split != -1)
					nameField = true;
				
			}
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				
				// last name
				split = line.indexOf(',');
				last = line.substring(1, split);
				line = line.substring(split + 2, line.length());
				
				/* Check for jerks with two last names separated with a space and prevents our database 
				   from breaking because of said jerk. I am talking about you Hudson. Thanks a lot. */
				split = last.indexOf(' ');
				if (split != -1){
					// http://stackoverflow.com/questions/6952363/replace-a-character-at-a-specific-index-in-a-string
					StringBuilder fixName = new StringBuilder(last);
					fixName.setCharAt(split, '-');
					last = String.valueOf(fixName);
				}
				
				// first name
				split = line.indexOf('"');
				first = line.substring(0,  split);
				initial = first.indexOf(' '); // Check for middle initial
				if (initial != -1)
					first = first.substring(0,  initial);
				line = line.substring(split + 2, line.length());
				
				// user name
				split = line.indexOf("@");
				username = line.substring(0,  split);
			
				// print check
				System.out.println(first + " " + last + " " + username);

				// Send to database
				//AddStudent student = new AddStudent(first, last, username, classname);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	public void dataHandler(Iterator<Row> rowIterator) {
		int id;
		int split;
		String name,email;
		String first, last, username;
		SubmitClassList classlist = new SubmitClassList();
		Row row;
		//for (int i = 0; i < 4; i++)// Loops 4 times because... Look, don't argue with me about this
			//row = rowIterator.next();
		boolean nameField = false;
		while(nameField == false){
			row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell cell = cellIterator.next();
			name = cell.getStringCellValue();
			name = name.toLowerCase();
			//System.out.println(name);
			if(name.equals("name"))
				nameField = true;
		}
		
		while (rowIterator.hasNext()) {
			row =rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell cell = cellIterator.next();
		
			// First and Last Name
			name = cell.getStringCellValue();
			split = name.indexOf(',');
			last = name.substring(0,  split);
			name = name.substring(split+2, name.length());
			
			/* Check for that one jerk with two last names as mentioned above*/
			split = last.indexOf(' ');
			if (split != -1){
				// http://stackoverflow.com/questions/6952363/replace-a-character-at-a-specific-index-in-a-string
				StringBuilder fixName = new StringBuilder(last);
				fixName.setCharAt(split, '-');
				last = String.valueOf(fixName);
			}
			split = name.indexOf('.');
			
			// Special Condition no Middle Initial
			if (split == -1)
				first = name.substring(0, name.length());	
			else{
				split = name.indexOf(' ');
				first = name.substring(0, split);
			}
			
			//RU ID
			//cell = cellIterator.next();
			//ruID = cell.getStringCellValue();
			
			// Username
			cell = cellIterator.next();
			email = cell.getStringCellValue();
			split = email.indexOf('@');
			username = email.substring(0,  split);
			
			// print check
			System.out.println(first + " " + last + " " + username);

			// Send to database
			//AddStudent student = new AddStudent(first, last, username, classname);
		}	
	}
}
