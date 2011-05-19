package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import de.hsma.sit.ss11.frontend.Command;
import de.hsma.sit.ss11.frontend.view.MainWindow;
import de.hsma.sit.ss11.frontend.view.MainWindow.Delegate;
import de.hsma.sit.ss11.frontend.view.resources.Resources;

public class NavPanel extends JPanel {

	private static final Color NORTH_COLOR = Color.gray;
	private static final Color SOUTH_COLOR = Color.lightGray;

	private final Resources resources;
	private final Delegate delegate;
	private final MainWindow mainWindow;

	public NavPanel(Resources resources, MainWindow mainWindow, MainWindow.Delegate delegate) {
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
						String info;
						if (delegate.addNewFile()) {
							info = "Datei erfolgreich hinzugef�gt :-)";
							// TODO update file table and assigned users
						} else {
							info = "Datei konnte nicht hinzugef�gt werden :-(";
						}
						mainWindow.setInformationText(info, 5000);
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
				String info;
				if (delegate.removeFile()) {
					info = "Datei wurde erfolgreich gel�scht";
					// TODO update file table and assigned users
				} else {
					info = "Datei konnte nicht gel�scht werden :-(";
				}
				mainWindow.setInformationText(info, 5000);
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
	}
}
