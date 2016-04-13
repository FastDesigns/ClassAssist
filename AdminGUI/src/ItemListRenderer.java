import java.awt.Component;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ItemListRenderer extends DefaultListCellRenderer
	{
		/**
		 * List Rendered Version 1 - shows images(icons) along with text
		 */
		private static final long serialVersionUID = 1L;
		private Image img;
		private boolean renderImg = true;
		
		public ItemListRenderer()
		{
			super();
			try
			{
				img = ImageIO.read(this.getClass().getResource("res/nothere.png"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public ItemListRenderer(boolean b)
		{
			super();
			renderImg = b;
		}
		
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, false, cellHasFocus);
			if(renderImg)
				label.setIcon(new ImageIcon(img));
//			label.setIcon(getScaledImage(imageMap.get((String)value).getIcon(), listIconWidth, listIconHeight));
//			label.setHorizontalTextPosition(JLabel.RIGHT);
			label.setFont(label.getFont().deriveFont(24.0f));
			label.setOpaque(false);
			label.setBorder(new EmptyBorder(1, 1, 1, 1));
			JPanel p = new JPanel();
			return p;
		}
	}