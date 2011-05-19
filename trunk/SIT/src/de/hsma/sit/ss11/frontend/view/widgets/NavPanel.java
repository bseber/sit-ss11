package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.hsma.sit.ss11.frontend.view.resources.Resources;

public class NavPanel extends JPanel {

	private static final Color NORTH_COLOR = Color.gray;
	private static final Color SOUTH_COLOR = Color.lightGray;

	private final Resources resources;

	public NavPanel(Resources resources) {
		this.resources = resources;
		init();
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

		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
		center.setOpaque(false);
		addCenterButtons(center);

		JPanel east = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 1));
		east.setOpaque(false);
		addEastButtons(east);

		add(center, BorderLayout.CENTER);
		add(east, BorderLayout.EAST);
	}

	private void addCenterButtons(JPanel container) {
		// add file
		container
				.add(new NavButton(resources.images().addFile(), resources
						.images().addFileGlow(), resources.messages()
						.tooltipAddFile()));
		// remove file
		container.add(new NavButton(resources.images().removeFile(), resources
				.images().removeFileGlow(), resources.messages()
				.tooltipRemoveFile()));
	}

	private void addEastButtons(JPanel container) {
		// logout
		container.add(new NavButton(resources.images().logout(), resources
				.images().logoutGlow(), resources.messages().tooltipLogout()));
	}
}
