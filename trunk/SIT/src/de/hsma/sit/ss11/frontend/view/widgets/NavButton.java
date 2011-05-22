package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;

public class NavButton extends JLabel {

	public NavButton(Icon icon, String tooltip) {
		super(icon);
		setPreferredSize(new Dimension(50, 50));
		setToolTipText(tooltip);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}
