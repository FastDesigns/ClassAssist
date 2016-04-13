import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;


/**
 * 
 * @author Chase Abe
 *
 */
public class StatusIndicator extends JButton {
	private boolean present;
	private ImageIcon here;
	private ImageIcon absent;
	private boolean status = false;
	
	public StatusIndicator(){
		// Code from Chase Abe's project 5 for ITEC 220
		present = false;
		this.setOpaque(true);
		//this.setBorder(null);
		//absent = new ImageIcon((new ImageIcon("res/bad.png").getImage().getScaledInstance(600,100,java.awt.Image.SCALE_SMOOTH)));
		//here  = new ImageIcon((new ImageIcon("res/good.png").getImage().getScaledInstance(600,100,java.awt.Image.SCALE_SMOOTH)));
		//this.setIcon(absent);
//		 try {
//			    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
//			   // this.setBackground(Color.RED);
//			 } catch (Exception e) {
//			            e.printStackTrace();
//			 }
		
	}
	public void setStatus(boolean newStatus){
		status = newStatus;
		if (newStatus == true)
			this.setBackground(Color.GREEN);
			//this.setIcon(here);
		else
			this.setBackground(Color.RED);
			//this.setIcon(absent);
			//this.setIcon(new javax.swing.ImageIcon(getClass().getResource("res/bad.png")));
		changeStatus();
		
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if(status)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);

		g.fillRect(0, 0, getWidth(), getHeight());
		
//		g.setColor(Color.black);
//		g.drawRect(1, 1, getWidth() - 1, getHeight() - 1);
	}
	public boolean checkStatus(){
		return present;
	}
	
	private void changeStatus(){
		present = !present;
	}
}
