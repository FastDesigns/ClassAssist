package com.classassist.fastdesigns.logic;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

/**
 * Custom renderer for jlist
 * @author djust
 *
 */
public class ItemListRenderer extends DefaultListCellRenderer
{
	/**
	 * List Rendered Version 1 - shows images(icons) along with text
	 */
	private static final long serialVersionUID = 1L;
	private boolean render = false; //if selection should be shown
	
	public ItemListRenderer(boolean r)
	{
		super();
		render = r;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		final JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		label.setFont(label.getFont().deriveFont(24.0f));
		label.setOpaque(false);
		label.setBorder(new EmptyBorder(1, 1, 1, 1));
		label.setForeground(Color.black);
		if(isSelected && render)
			label.setForeground(new Color(47, 196, 205));
		return label;
	}
}