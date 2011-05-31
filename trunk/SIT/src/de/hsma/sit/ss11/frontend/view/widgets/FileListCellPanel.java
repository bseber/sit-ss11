package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

public class FileListCellPanel extends JPanel {

	public FileListCellPanel(String text, ImageIcon icon, boolean selected) {
		setBackground(selected ? UIManager.getColor("List.selectionBackground")
				: Color.WHITE);
		setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		setLayout(new BorderLayout(0, 0));
		
		JPanel txtContainer = new JPanel();
		txtContainer.setOpaque(false);
		add(txtContainer, BorderLayout.CENTER);
		
		JLabel lblText = new JLabel(text);
		txtContainer.add(lblText);
		
		JPanel icnContainer = new JPanel();
		icnContainer.setOpaque(false);
		add(icnContainer, BorderLayout.EAST);
		
		JLabel icn = new JLabel(icon);
		icnContainer.add(icn);
	}

}
