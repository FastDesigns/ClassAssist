import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel with a custom background
 * @author djust
 *
 */
public class PanelBackground extends JPanel
{

	/**
	 * PanelBackground version 1
	 */
	private static final long serialVersionUID = 1L;
	
	private Image img;
	private boolean missingBackground = false;
	
	public PanelBackground(String name)
	{
		super();
		loadBackground(name);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		if(!missingBackground)
			g2.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
	}
	
	private void loadBackground(String name)
	{
		try
		{
			img = ImageIO.read(this.getClass().getResource("res/" + name));
		}
		catch (IOException | IllegalArgumentException e)
		{
			missingBackground = true;
		}
	}
}