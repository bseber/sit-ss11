package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import de.hsma.sit.ss11.frontend.resources.Images;
import de.hsma.sit.ss11.frontend.resources.Resources;

@SuppressWarnings("serial")
public class UserCellPanel extends JPanel {

	public UserCellPanel(String userName, boolean selected) {
		setBackground(selected ? UIManager.getColor("List.selectionBackground")
				: Color.WHITE);
		setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		setLayout(new BorderLayout(0, 0));

		JPanel icnContainer = new JPanel();
		icnContainer.setPreferredSize(new Dimension(24, 18));
		icnContainer.setOpaque(false);
		add(icnContainer, BorderLayout.WEST);

		Images images = Resources.getInstance().images();
		JLabel icn = new JLabel(images.userSmall());
		icnContainer.add(icn);

		JPanel userNameContainer = new JPanel();
		FlowLayout flowLayout = (FlowLayout) userNameContainer.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		userNameContainer.setOpaque(false);
		add(userNameContainer, BorderLayout.CENTER);

		JLabel lblUserName = new JLabel(userName);
		userNameContainer.add(lblUserName);
	}

}
