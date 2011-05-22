package de.hsma.sit.ss11.frontend.view.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;

import de.hsma.sit.ss11.frontend.Command;
import de.hsma.sit.ss11.frontend.view.widgets.NavButton;

public class NavBtnMouseListener implements MouseListener {
	
	private final NavButton source;
	private final Command command;
	private final Icon icon;
	private final Icon mouseOverIcon;

	public NavBtnMouseListener(NavButton source, Command command, Icon icon, Icon mouseOverIcon) {
		this.source = source;
		this.command = command;
		this.icon = icon;
		this.mouseOverIcon = mouseOverIcon;
	}

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
		source.setIcon(mouseOverIcon);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		source.setIcon(icon);
	}

}
