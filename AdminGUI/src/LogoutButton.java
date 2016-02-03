import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LogoutButton extends JButton implements ActionListener
{

	/**
	 * HomeButton Version 1
	 */
	private static final long serialVersionUID = 1L;
	
	public LogoutButton(String text)
	{
		super(text);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
		frame.dispose();
		@SuppressWarnings("unused")
		desktopGUI frames = desktopGUI.getFrame();
		frames.setVisible(true);
	}
}