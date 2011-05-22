package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NavPanel extends JPanel {

	private static final Color NORTH_COLOR = Color.gray;
	private static final Color SOUTH_COLOR = Color.lightGray;

	private JPanel leftButtonPanel;
	private JPanel rightButtonPanel;
	
	public NavPanel() {
		init();
	}
	
	public void addLeft(JLabel button) {
		leftButtonPanel.add(button);
	}
	
	public void addRight(JLabel button) {
		rightButtonPanel.add(button);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paint gradient background
		int panelHeight = getHeight();
		int panelWidth = getWidth();
		GradientPaint gradientPaint = new GradientPaint(0, 25, NORTH_COLOR, 0,
				panelHeight, SOUTH_COLOR);
		if (g instanceof Graphics2D) {
			Graphics2D graphics2D = (Graphics2D) g;
			graphics2D.setPaint(gradientPaint);
			graphics2D.fillRect(0, 0, panelWidth, panelHeight);
		}
	}

	private void init() {
		setLayout(new BorderLayout());

		leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
		leftButtonPanel.setOpaque(false);

		rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
		rightButtonPanel.setOpaque(false);

		add(leftButtonPanel, BorderLayout.CENTER);
		add(rightButtonPanel, BorderLayout.EAST);
	}
	
}
