package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.frontend.view.resources.Resources;
import de.hsma.sit.ss11.frontend.view.widgets.NavPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainWindow {

	public interface Delegate {

		void addFile();
		
		void downloadFile();
		
		void removeFile();
		
		void logoutAndExit();
		
	}

	private final Delegate delegate;
	private final Resources resources;

	private JFrame frame;
	private JTable fileTable;

	/**
	 * Create the application.
	 */
	public MainWindow(Resources resources, Delegate delegate,
			boolean successfulLogin) {
		this.resources = resources;
		this.delegate = delegate;
		initialize();
	}

	public void setVisible(boolean b) {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(600, 400));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		NavPanel navPanel = new NavPanel(resources, delegate);
		frame.getContentPane().add(navPanel, BorderLayout.NORTH);

		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 1, 5, 5));

		JPanel fileContainerPanel = new JPanel();
		fileContainerPanel.setOpaque(false);
		fileContainerPanel.setBorder(null);
		contentPanel.add(fileContainerPanel);
		fileContainerPanel.setLayout(new MigLayout("", "[422px,grow]",
				"[92px,grow]"));

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(new LineBorder(Color.white, 1, true),
				resources.messages().files(), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.white));
		fileContainerPanel.add(panel, "cell 0 0,grow");
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane fileScrollPane = new JScrollPane();
		fileScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fileScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(fileScrollPane);
		
		fileTable = new JTable();
		fileTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		fileScrollPane.setViewportView(fileTable);

		JPanel userContainerPanel = new JPanel();
		userContainerPanel.setOpaque(false);
		userContainerPanel.setBorder(null);
		contentPanel.add(userContainerPanel);
		userContainerPanel.setLayout(new MigLayout("",
				"[144px,grow][center][144px,grow]", "[119px,grow]"));

		JPanel allUsersContainer = new JPanel();
		allUsersContainer.setOpaque(false);
		allUsersContainer.setBorder(new TitledBorder(new LineBorder(
				Color.white, 1, true), resources.messages().user(),
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.white));
		userContainerPanel.add(allUsersContainer, "cell 0 0,grow");
		allUsersContainer.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		allUsersContainer.add(scrollPane);

		JPanel buttonContainer = new JPanel();
		buttonContainer.setOpaque(false);
		userContainerPanel.add(buttonContainer,
				"cell 1 0,alignx center,aligny center");
		buttonContainer.setLayout(new GridLayout(2, 1, 0, 10));

		JLabel lblNewLabel = new JLabel(resources.images().arrowForward());
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonContainer.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel(resources.images().arrowBack());
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonContainer.add(lblNewLabel_1);

		JPanel assignedUsersContainer = new JPanel();
		assignedUsersContainer.setOpaque(false);
		assignedUsersContainer.setBorder(new TitledBorder(new LineBorder(
				Color.white, 1, true), resources.messages().assignedUsers(),
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.white));
		userContainerPanel.add(assignedUsersContainer, "cell 2 0,grow");
		assignedUsersContainer.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		assignedUsersContainer.add(scrollPane_1);
	}

}
