import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * 
 * @author Chase Abe
 * 
 * _________ .__                            _____                .__          __   
 * \_   ___ \|  | _____    ______ ______   /  _  \   ______ _____|__| _______/  |_ 
 * /    \  \/|  | \__  \  /  ___//  ___/  /  /_\  \ /  ___//  ___/  |/  ___/\   __\
 * \     \___|  |__/ __ \_\___ \ \___ \  /    |    \\___ \ \___ \|  |\___ \  |  |  
 *  \______  /____(____  /____  >____  > \____|__  /____  >____  >__/____  > |__|  
 *         \/          \/     \/     \/          \/     \/     \/        \/        
 *
 */
public class AttendanceDisplay extends JPanel{
	private String[] students;
	private ArrayList<StatusIndicator> present;
	private ArrayList<JLabel> names;
	private ArrayList<JLabel> status;
	private JPanel content = new JPanel();
	
	public AttendanceDisplay(String[] s){
		present = new ArrayList<StatusIndicator>();
		names = new ArrayList<JLabel>();
		status = new ArrayList<JLabel>();
		//fdsa.setSize(400,400);
		//this.setSize(400,400);
		
		students = s;
		//JRadioButton test = new JRadioButton();
		//test
		int length = students.length;
		// i need code for the total amount of students
		this.setLayout(new GridLayout(length, 3));
//		this.setPreferredSize(new Dimension(800, 500));
		
		for (int i = 0; i<students.length;i++){
			final int temp = i;
			names.add(new JLabel(students[temp]));
			
			present.add(new StatusIndicator());
			status.add(new JLabel("<html><span style='font-size:20px'>"+"No Record"));
			// some kind of check for present or not
			//change radio button to reflect status
			
			
			//give each button an action listener
			present.get(temp).addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        StatusIndicator button = (StatusIndicator) e.getSource();
			       // button.setFocusPainted(false);
			        String text = names.get(temp).getText();
			        int cut = text.lastIndexOf('>')+1;
			        String name = text.substring(cut, text.length());
			        
			        boolean aqui = button.checkStatus();
			        System.out.println(aqui);
			        if (aqui == false){
			        	button.setStatus(true);                       // reversed because state is changing
			        	status.get(temp).setText("<html><span style='font-size:20px'>"+"Present");
			        	System.out.println(name + " is not present");
			        }
			        else{
			        	button.setStatus(false);
			        	status.get(temp).setText("<html><span style='font-size:20px'>"+"Absent");
			        	System.out.println(name + " is present");
			        }
			        
			    }
			});
			
			content.add(names.get(i));
			content.add(present.get(i));
			content.add(status.get(i));
		}
		
		//this.add(test);
//		scroll.add(this);
//		scroll.setSize(800,800);
//		fdsa.getContentPane().add(scroll);
		//fdsa.add(scroll);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(content);
//		fdsa.setSize(1150,600);
//		this.setLayout(new BorderLayout());
		
		this.add(scroll, BorderLayout.CENTER);
		//fdsa.pack();
		//this.requestFocus();
//		fdsa.setVisible(true);
		
	}
	
	public void setStudents(String[] l)
	{
		students = l;
	}
	
	
//	//http://www.coderanch.com/t/456966/GUI/java/give-color-JRadioButtons
//	public void paintIcon(Component c, Graphics g, int x, int y){
//		
//		g.setColor(Color.red);
//		g.fillOval(x, y, 10, 10); // 10 is the dot diameter
//	}
}
