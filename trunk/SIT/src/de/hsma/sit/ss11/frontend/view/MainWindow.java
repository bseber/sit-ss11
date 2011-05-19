package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.frontend.view.resources.Resources;
import de.hsma.sit.ss11.frontend.view.widgets.NavPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.UIManager;

public class MainWindow {

	public interface Delegate {

		/**
		 * @return <code>true</code> if a file was added successfully, otherwise
		 *         <code>false</code>
		 */
		boolean addNewFile();

		void downloadFile();

		void logoutAndExit();

		/**
		 * @return <code>true</code> if a file was removed successfully,
		 *         otherwise <code>false</code>
		 */
		boolean removeFile();

	}

	private final Delegate delegate;
	private final Resources resources;

	private JFrame frame;
	private JTable fileTable;
	private JLabel lblInformation;

	/**
	 * Create the application.
	 */
	public MainWindow(Resources resources, Delegate delegate,
			boolean successfulLogin) {
		this.resources = resources;
		this.delegate = delegate;
		initialize();
		frame.pack();
	}

	public void setInformationText(final String text) {
		setInformationText(text, 0);
	}

	/**
	 * @param text
	 * @param millis
	 *            time after which the information text will vanish
	 */
	public void setInformationText(final String text, final int millis) {
		lblInformation.setText(text);
		if (millis > 0) {
			// show info text only for the given time
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						Thread.sleep(millis);
						if (lblInformation.getText().equals(text)) {
							lblInformation.setText("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			new Thread(new FutureTask<Object>(runnable, null)).start();
		}
	}

	public void setVisible(boolean b) {
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(600, 500));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		NavPanel navPanel = new NavPanel(resources, this, delegate);
		frame.getContentPane().add(navPanel, BorderLayout.NORTH);

		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[584px,grow]",
				"[grow][120px]"));

		JPanel fileContainerPanel = new JPanel();
		fileContainerPanel.setOpaque(false);
		fileContainerPanel.setBorder(null);
		contentPanel.add(fileContainerPanel, "cell 0 0,grow");
		fileContainerPanel.setLayout(new MigLayout("", "[422px,grow]", "[][200px,grow]"));

		JPanel fileTableFilterPanel = new JPanel();
		fileTableFilterPanel.setOpaque(false);
		fileContainerPanel.add(fileTableFilterPanel, "cell 0 0,grow");
		fileTableFilterPanel.setLayout(new BorderLayout(5, 5));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setOpaque(false);
		fileTableFilterPanel.add(panel, BorderLayout.CENTER);
		
		JLabel lblFiles = new JLabel(resources.messages().files());
		lblFiles.setForeground(Color.WHITE);
		panel.add(lblFiles);

		JComboBox fileTableFilterComboBox = new JComboBox();
		fileTableFilterComboBox.setForeground(Color.WHITE);
		fileTableFilterComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Alle Dateien", "Eigene Dateien", "Nur lesbare Dateien" }));
		fileTableFilterPanel.add(fileTableFilterComboBox, BorderLayout.EAST);
		
		JScrollPane fileScrollPane = new JScrollPane();
		fileScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fileScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		fileContainerPanel.add(fileScrollPane, "cell 0 1,grow");

		fileTable = new JTable();
		fileTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "New column", "New column", "New column" }));
		fileScrollPane.setViewportView(fileTable);

		JPanel userContainerPanel = new JPanel();
		userContainerPanel.setOpaque(false);
		userContainerPanel.setBorder(null);
		contentPanel.add(userContainerPanel, "cell 0 1,grow");
		userContainerPanel.setLayout(new MigLayout("", "[144px,grow][center][144px,grow]", "[grow]"));

		JPanel allUsersContainer = new JPanel();
		allUsersContainer.setOpaque(false);
		allUsersContainer.setBorder(new TitledBorder(BorderFactory
				.createEmptyBorder(), resources.messages().allUsers(),
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.white));
		userContainerPanel.add(allUsersContainer, "cell 0 0,grow");
		allUsersContainer.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane allUsersScrollPane = new JScrollPane();
		allUsersScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		allUsersScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		allUsersContainer.add(allUsersScrollPane);

		JPanel buttonContainer = new JPanel();
		buttonContainer.setOpaque(false);
		userContainerPanel.add(buttonContainer,
				"cell 1 0,alignx center,aligny center");
		buttonContainer.setLayout(new GridLayout(2, 1, 0, 10));

		JLabel btnAddUserToFile = new JLabel(resources.images().arrowForward());
		btnAddUserToFile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonContainer.add(btnAddUserToFile);

		JLabel btnRemUserFromFile = new JLabel(resources.images().arrowBack());
		btnRemUserFromFile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonContainer.add(btnRemUserFromFile);

		JPanel assignedUsersContainer = new JPanel();
		assignedUsersContainer.setOpaque(false);
		assignedUsersContainer.setBorder(new TitledBorder(BorderFactory
				.createEmptyBorder(), resources.messages().assignedUsers(),
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.white));
		userContainerPanel.add(assignedUsersContainer, "cell 2 0,grow");
		assignedUsersContainer.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane assignedUsersScrollPane = new JScrollPane();
		assignedUsersScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		assignedUsersScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		assignedUsersContainer.add(assignedUsersScrollPane);

		JPanel footerPanel = new JPanel();
		footerPanel.setForeground(Color.WHITE);
		footerPanel.setPreferredSize(new Dimension(10, 25));
		footerPanel.setBackground(new Color(220, 220, 220));
		frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
		footerPanel.setLayout(new MigLayout("", "[grow]", "[]"));

		lblInformation = new JLabel("");
		footerPanel.add(lblInformation, "cell 0 0,alignx left,aligny top");
	}

}
