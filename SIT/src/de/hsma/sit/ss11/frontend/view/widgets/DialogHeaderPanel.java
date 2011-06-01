package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DialogHeaderPanel extends JPanel {

	private Color westColor = Color.gray;
	private Color eastColor = Color.lightGray;

	/**
	 * Create the panel.
	 * 
	 * @param heading
	 * @param icon
	 * @param width
	 */
	public DialogHeaderPanel(ImageIcon icon, String heading, String description, int width) {
		setSize(514, 65);
		setLayout(new MigLayout("", "[267.00,grow,leading][]", "[50.00px,grow]"));

		JPanel txtPanel = new JPanel();
		txtPanel.setOpaque(false);
		add(txtPanel, "cell 0 0,grow");
		txtPanel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel headingLabel = new JLabel(heading);
		headingLabel.setForeground(Color.WHITE);
		txtPanel.add(headingLabel);
		headingLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel descriptionLabel = new JLabel(description);
		descriptionLabel.setForeground(Color.WHITE);
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPanel.add(descriptionLabel);
		
		JPanel iconPanel = new JPanel();
		iconPanel.setOpaque(false);
		add(iconPanel, "cell 1 0,grow");
		
		JLabel iconLabel = new JLabel(icon);
		iconPanel.add(iconLabel);
		

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paint gradient background
		int panelHeight = getHeight();
		int panelWidth = getWidth();
		GradientPaint gradientPaint = new GradientPaint(0, 0, westColor,
				panelWidth, panelHeight, eastColor);
		if (g instanceof Graphics2D) {
			Graphics2D graphics2D = (Graphics2D) g;
			graphics2D.setPaint(gradientPaint);
			graphics2D.fillRect(0, 0, panelWidth, panelHeight);
		}
	}

}
