package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;

public class NavButton extends JLabel {
	
	private final Icon icon;
	private final Icon mouseOverIcon;

	public NavButton(Icon icon, Icon mouseOverIcon, String tooltip) {
		super(icon);
		this.icon = icon;
		this.mouseOverIcon = mouseOverIcon;
		setPreferredSize(new Dimension(50, 50));
		setToolTipText(tooltip);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new MyMouseListener());
	}
	
	
	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			setIcon(mouseOverIcon);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setIcon(icon);
		}
		
	}
}
