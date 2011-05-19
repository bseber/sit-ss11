package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;

import de.hsma.sit.ss11.frontend.Command;

public class NavButton extends JLabel {
	
	private final Icon icon;
	private final Icon mouseOverIcon;
	private final Command command;

	/**
	 * @param icon
	 * @param mouseOverIcon
	 * @param tooltip
	 * @param command
	 */
	public NavButton(Icon icon, Icon mouseOverIcon, String tooltip, Command command) {
		super(icon);
		this.icon = icon;
		this.mouseOverIcon = mouseOverIcon;
		this.command = command;
		setPreferredSize(new Dimension(50, 50));
		setToolTipText(tooltip);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new MyMouseListener());
	}
	
	
	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			command.execute();
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
