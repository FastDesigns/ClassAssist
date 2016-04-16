package com.classassist.fastdesigns.teacher.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import com.classassist.fastdesigns.gui.MyButton;
import com.classassist.fastdesigns.gui.MyScrollBarUI;
import com.classassist.fastdesigns.logic.GetAttendance;
import com.classassist.fastdesigns.logic.ItemListRenderer;
import com.classassist.fastdesigns.logic.WriteFile;
import com.sun.net.ssl.internal.www.protocol.https.Handler;
/**
 * Layout and operations for exporting attendance
 * @author Eddie Justice
 *
 */
public class ExportAttendance extends JPanel
{

	/**
	 * ExportAttendance version 1
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private JPanel content = new JPanel();
	private JPanel actionsPanel = new JPanel();
	private JButton export = new MyButton("Export");
	private String className;
	private String[] attendanceList = {"Select a date"};
	private JList<String> attendance = new JList<String>(attendanceList);
	private JScrollPane attendancePane = new JScrollPane(attendance);
	private final JXDatePicker picker = new JXDatePicker();
	
	public ExportAttendance(String cl)
	{
		className = cl;
		setLayout(new BorderLayout());
		setup();
		exportContent();
		add(contentPanel, BorderLayout.CENTER);
		setPreferredSize(new Dimension(500, 800));
		setVisible(true);
	}
	
	private void setup()
	{
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(content, BorderLayout.CENTER);
		contentPanel.add(actionsPanel, BorderLayout.PAGE_END);
		content.setBackground(Color.darkGray);
		attendance.setCellRenderer(new ItemListRenderer(false));
		
		actionsPanel.add(export);
		actionsPanel.setBackground(Color.darkGray);
		attendancePane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		attendance.setBorder(BorderFactory.createLineBorder(Color.black));
		addActionListeners();
	}
	
	private void addActionListeners()
	{
		export.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final JFileChooser fc = new JFileChooser();
				JFrame mainFrame = new JFrame("Export");
				String filename = null;
				File fileToSave;
				DateFormat dateFormat = new SimpleDateFormat("hh_mm_ss");
				Date date = new Date();
				String searchDate = picker.getEditor().getText().toString();
				// http://www.codejava.net/java-se/swing/show-save-file-dialog-using-jfilechooser
				fc.setDialogTitle("Specify a file to save"); 
				int userSelection = fc.showSaveDialog(mainFrame);
				 
				if (userSelection == fc.APPROVE_OPTION) {
				    fileToSave = fc.getSelectedFile();
				    filename = fileToSave.getAbsolutePath();
				    //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				}
				WriteFile write = new WriteFile(className, dateFormat.format(date), filename, searchDate);
				write.print();
			}
		});
	}
	
	private void exportContent()
	{
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date today = Calendar.getInstance().getTime();
		content.setLayout(new BorderLayout());
		JPanel temp = new JPanel();
		picker.setDate(new Date());
		picker.setFormats(new SimpleDateFormat("yyyy-MM-dd"));
		temp.add(picker);
		temp.setBackground(Color.darkGray);
		content.add(temp, BorderLayout.PAGE_START);
		content.setBorder(new EmptyBorder(20, 20, 20, 20));
		content.add(attendancePane, BorderLayout.CENTER);
		picker.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String[] list = {"Loading..."};
				setAttendance(list);
				new Attend(className, picker.getEditor().getText().toString());
			}
		});
	}
	
	private void setAttendance(String[] l)
	{
		attendance.setListData(l);
		refresh();
	}
	
	private class Attend implements Runnable
	{
		private String clas, date;
		
		public Attend(String cl, String d)
		{
			clas = cl;
			date = d;
			run();
		}
		
		@Override
		public void run()
		{
			String[] l = {clas, date};
			setAttendance(GetAttendance.getAttendance(l));
		}
	}
	
	private void refresh()
	{
		repaint();
	}
}