import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXDatePicker;

import com.sun.net.ssl.internal.www.protocol.https.Handler;
/**
 * Layout and operations for exporting attendance
 * @author Eddie Justice
 *
 */
public class StudentsLayout extends JPanel
{

	/**
	 * StudentsLayout version 1
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	private JPanel content = new JPanel();
	private JPanel actionsPanel = new JPanel();
	private JButton attend = new JButton("Take Attendance");
	private String className;
	private String[] studentList = {"Loading..."};
	private JList<String> students = new JList<String>(studentList);
	private JScrollPane studentPane = new JScrollPane(students);
	private JPanel attendance;
	
	public StudentsLayout(String[] s, AttendanceDisplay a)
	{
		attendance = a;
		setLayout(new BorderLayout());
		setup();
		add(contentPanel, BorderLayout.CENTER);
		setPreferredSize(new Dimension(500, 800));
		setList(s);
	}
	
	private void setup()
	{
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(content, BorderLayout.CENTER);
		contentPanel.add(actionsPanel, BorderLayout.PAGE_START);
		content.setLayout(new BorderLayout());
		content.add(attendance);
//		content.add(studentPane, BorderLayout.CENTER);
		studentPane.setBorder(new EmptyBorder(10, 20, 20, 20));
		studentPane.setBackground(Color.darkGray);
		
		students.setCellRenderer(new ItemListRenderer());
		
		actionsPanel.add(attend);
		actionsPanel.setBackground(Color.darkGray);
		students.setBorder(BorderFactory.createLineBorder(Color.black));
		addActionListeners();
	}
	
	private void addActionListeners()
	{
		attend.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
//				TakeAttendance.recordAttendance(classCombo.geSelectedItem().toString(),dateFormat.format(date));
			}
		});
	}
	
	private void setList(String[] s)
	{
		studentList = s;
		students.setListData(studentList);
	}
}