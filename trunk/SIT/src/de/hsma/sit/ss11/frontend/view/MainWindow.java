package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.concurrent.FutureTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.frontend.controller.MainWindowController;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.widgets.NavPanel;
import javax.swing.border.MatteBorder;

public class MainWindow implements MainWindowController.MainWindowView {

	public interface Delegate {

		void about(JFrame parent);

		/**
		 * @return <code>true</code> if a file was added successfully, otherwise
		 *         <code>false</code>
		 */
		boolean addNewFile();

		void addUser(JFrame parent);

		void clickedOnFile(FileInfo file);

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
	private JLabel lblInformation;
	private JTable fileTable;

	/**
	 * Create the application.
	 */
	public MainWindow(Resources resources, Delegate delegate) {
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

		NavPanel navPanel = new NavPanel(resources, frame, delegate);
		frame.getContentPane().add(navPanel, BorderLayout.NORTH);

		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel fileContainerPanel = new JPanel();
		fileContainerPanel.setOpaque(false);
		fileContainerPanel.setBorder(null);
		contentPanel.add(fileContainerPanel, BorderLayout.CENTER);
		fileContainerPanel.setLayout(new MigLayout("", "[584px,grow]", "[300px,grow]"));

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),
				resources.messages().files(), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.white));
		fileContainerPanel.add(panel, "cell 0 0,grow");
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane fileTableScrollPane = new JScrollPane();
		fileTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fileTableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(fileTableScrollPane);

		fileTable = new JTable();
		fileTableScrollPane.setViewportView(fileTable);

		JPanel userContainerPanel = new JPanel();
		userContainerPanel.setOpaque(false);
		userContainerPanel.setBorder(null);

		contentPanel.add(userContainerPanel, BorderLayout.SOUTH);
		userContainerPanel.setLayout(new MigLayout("", "[144px,grow][center][144px,grow]", "[119px,grow][5px]"));

		JPanel allUsersContainer = new JPanel();
		allUsersContainer.setOpaque(false);
		allUsersContainer.setBorder(new TitledBorder(BorderFactory
				.createEmptyBorder(), resources.messages().user(),
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
		btnAddUserToFile.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonContainer.add(btnAddUserToFile);

		JLabel btnRemUserFromFile = new JLabel(resources.images().arrowBack());
		btnRemUserFromFile.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
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
		footerPanel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) Color.WHITE));
		footerPanel.setForeground(new Color(245, 245, 245));
		footerPanel.setPreferredSize(new Dimension(10, 25));
		footerPanel.setBackground(Color.GRAY);
		frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
		footerPanel.setLayout(new MigLayout("", "[grow]", "[]"));

		lblInformation = new JLabel("");
		lblInformation.setForeground(Color.WHITE);
		footerPanel.add(lblInformation, "cell 0 0,alignx left,aligny top");
	}

	@Override
	public void setFileList(List<FileInfo> fileList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFileAssignedUsers(FileInfo file, List<AnyUser> assignedUsers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInfoText(String text) {
		lblInformation.setText(text);
	}

	@Override
	public void setUserList(List<AnyUser> userList) {
		// TODO Auto-generated method stub

	}

}
