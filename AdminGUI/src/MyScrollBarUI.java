import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MyScrollBarUI extends BasicScrollBarUI
{
	protected int scrollBarWidth = 500;
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r)
	{
		g.setColor(new Color(47, 196, 205));
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r)
	{
		g.setColor(Color.darkGray);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	@Override
	public Dimension getPreferredSize(JComponent c)
	{
		return new Dimension(10, 0);
	}
}