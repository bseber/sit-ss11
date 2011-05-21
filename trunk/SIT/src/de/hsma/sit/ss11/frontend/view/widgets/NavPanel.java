package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.hsma.sit.ss11.frontend.Command;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.MainWindow;
import de.hsma.sit.ss11.frontend.view.MainWindow.Delegate;

public class NavPanel extends JPanel {

	private static final Color NORTH_COLOR = Color.gray;
	private static final Color SOUTH_COLOR = Color.lightGray;

	private final Resources resources;
	private final Delegate delegate;
	private final JFrame mainWindow;

	public NavPanel(Resources resources, JFrame mainWindow,
			MainWindow.Delegate delegate) {
		this.resources = resources;
		this.mainWindow = mainWindow;
		this.delegate = delegate;
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
		container.add(new NavButton(resources.images().addFile(), resources
				.images().addFileGlow(), resources.messages().tooltipAddFile(),
				new Command() {
					@Override
					public void execute() {
						delegate.addNewFile();
					}
				}));
		// download file
		container.add(new NavButton(resources.images().downloadFile(),
				resources.images().downloadFileGlow(), resources.messages()
						.tooltipDownloadFile(), new Command() {
					@Override
					public void execute() {
						delegate.downloadFile();
					}
				}));
		// remove file
		container.add(new NavButton(resources.images().removeFile(), resources
				.images().removeFileGlow(), resources.messages()
				.tooltipRemoveFile(), new Command() {
			@Override
			public void execute() {
				delegate.removeFile();
			}
		}));
		// TODO display following only if user is admin
		// separator
		container.add(new JLabel(resources.images().navSeparator()));
		// add new user
		container.add(new NavButton(resources.images().addUser(), resources
				.images().addUserGlow(), resources.messages().addUser(),
				new Command() {
					@Override
					public void execute() {
						delegate.addUser(mainWindow);
					}
				}));
	}

	private void addEastButtons(JPanel container) {
		// logout
		container.add(new NavButton(resources.images().logout(), resources
				.images().logoutGlow(), resources.messages().tooltipLogout(),
				new Command() {
					@Override
					public void execute() {
						delegate.logoutAndExit();
					}
				}));
		// info
		container.add(new NavButton(resources.images().info(), resources
				.images().infoGlow(),
				resources.messages().tooltipInformation(), new Command() {
					@Override
					public void execute() {
						delegate.about(mainWindow);
					}
				}));
	}
}
