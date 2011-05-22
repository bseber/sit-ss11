package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.concurrent.FutureTask;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.frontend.Command;
import de.hsma.sit.ss11.frontend.controller.MainWindowController;
import de.hsma.sit.ss11.frontend.resources.Images;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.listener.NavBtnMouseListener;
import de.hsma.sit.ss11.frontend.view.widgets.NavButton;
import de.hsma.sit.ss11.frontend.view.widgets.NavPanel;

public class MainWindow implements MainWindowController.MainWindowView {

	public interface UIHandler {

		/**
		 * @return <code>true</code> if a file was added successfully, otherwise
		 *         <code>false</code>
		 */
		boolean onAddFileClicked();

		void onAddUserClicked(MainWindow mainWindow, JFrame parent);

		void onDownloadFileClicked();

		void onFileSelected(FileInfo file);

		void onInfoClicked(JFrame parent);

		void onLogoutClicked();

		/**
		 * @return <code>true</code> if a file was removed successfully,
		 *         otherwise <code>false</code>
		 */
		boolean onRemoveFileClicked();
		
		void refreshUserList();

	}

	private final MainWindow.UIHandler uiHandler;
	private final Resources resources;

	private final JFrame frame;
	private final JLabel lblInformation;
	private JTable fileTable;
	private JSplitPane splitPane;

	/**
	 * Create the application.
	 */
	public MainWindow(Resources resources, MainWindow.UIHandler uiHandler) {
		this.resources = resources;
		this.uiHandler = uiHandler;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		lblInformation = new JLabel();
		initialize();
		frame.pack();
	}
	
	public void refreshUserList() {
		uiHandler.refreshUserList();
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
		splitPane.setDividerLocation(splitPane.getParent().getHeight());
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

	private void initialize() {
		frame.setMinimumSize(new Dimension(600, 500));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		initNavigation();
		initContent();
		initFooter();
	}

	private void initNavigation() {
		NavPanel navPanel = new NavPanel();
		frame.getContentPane().add(navPanel, BorderLayout.NORTH);
		initLeftNav(navPanel);
		initRightNav(navPanel);
	}

	private void initLeftNav(NavPanel navPanel) {
		// add file
		navPanel.addLeft(createButton(resources.images().addFile(), resources
				.images().addFileGlow(), resources.messages().tooltipAddFile(),
				getAddFileCommand()));
		// remove file
		navPanel.addLeft(createButton(resources.images().removeFile(),
				resources.images().removeFileGlow(), resources.messages()
						.tooltipRemoveFile(), getRemoveFileCommand()));
		// download file
		navPanel.addLeft(createButton(resources.images().downloadFile(),
				resources.images().downloadFileGlow(), resources.messages()
						.tooltipDownloadFile(), getDownloadFileCommand()));
		// TODO display following only if user is admin
		// separator
		navPanel.addLeft(new JLabel(resources.images().navSeparator()));
		// add new user
		navPanel.addLeft(createButton(resources.images().addUser(), resources
				.images().addUserGlow(), resources.messages().tooltipAddUser(),
				getAddUserCommand()));
	}

	private Command getAddUserCommand() {
		return new Command() {
			@Override
			public void execute() {
				uiHandler.onAddUserClicked(MainWindow.this, frame);
			}
		};
	}

	private Command getRemoveFileCommand() {
		return new Command() {
			@Override
			public void execute() {
				uiHandler.onRemoveFileClicked();
			}
		};
	}

	private Command getDownloadFileCommand() {
		return new Command() {
			@Override
			public void execute() {
				uiHandler.onDownloadFileClicked();
			}
		};
	}

	private Command getAddFileCommand() {
		return new Command() {
			@Override
			public void execute() {
				uiHandler.onAddFileClicked();
			}
		};
	}

	private void initRightNav(NavPanel navPanel) {
		// info
		navPanel.addRight(createButton(resources.images().info(), resources
				.images().infoGlow(),
				resources.messages().tooltipInformation(), getInfoCommand()));
		// logout
		navPanel.addRight(createButton(resources.images().logout(), resources
				.images().logoutGlow(), resources.messages().tooltipLogout(),
				getLogoutCommand()));
	}

	private Command getInfoCommand() {
		return new Command() {
			@Override
			public void execute() {
				uiHandler.onInfoClicked(frame);
			}
		};
	}

	private Command getLogoutCommand() {
		return new Command() {
			@Override
			public void execute() {
				uiHandler.onLogoutClicked();
			}
		};
	}

	/**
	 * @param icn
	 *            icon of the button
	 * @param icnMouseOver
	 *            mouseOver-icon of the button
	 * @param tooltip
	 *            tooltip of the button
	 * @param clickCommand
	 *            the {@link Command} which will be executed when the button is
	 *            clicked
	 * @return
	 */
	private NavButton createButton(Icon icn, Icon icnMouseOver, String tooltip,
			Command clickCommand) {
		NavButton btn = new NavButton(icn, tooltip);
		NavBtnMouseListener mlAddFile = new NavBtnMouseListener(btn,
				clickCommand, icn, icnMouseOver);
		btn.addMouseListener(mlAddFile);
		return btn;
	}

	private void initContent() {
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(1.0);
		splitPane.setContinuousLayout(true);
		splitPane.setBorder(null);
		splitPane.setOneTouchExpandable(true);
		splitPane.setOpaque(false);
		splitPane.setDividerSize(10);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		initFilePanel(splitPane);
		initAssignUserPanel(splitPane);
	}

	private void initFilePanel(JSplitPane splitPane) {
		// file overview
		//
		JPanel fileContainerPanel = new JPanel();
		splitPane.setLeftComponent(fileContainerPanel);
		fileContainerPanel.setOpaque(false);
		fileContainerPanel.setBorder(null);
		fileContainerPanel.setLayout(new MigLayout("", "[584px,grow]",
				"[300px,grow]"));

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(),
				resources.messages().files(), TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.white));
		fileContainerPanel.add(panel, "cell 0 0,grow");
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane fileTableScrollPane = new JScrollPane();
		fileTableScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fileTableScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(fileTableScrollPane);

		fileTable = new JTable();
		fileTableScrollPane.setViewportView(fileTable);
	}

	private void initAssignUserPanel(JSplitPane splitPane) {
		// user overview
		//
		JPanel userContainerPanel = new JPanel();
		userContainerPanel.setMinimumSize(new Dimension(10, 180));
		splitPane.setRightComponent(userContainerPanel);
		userContainerPanel.setOpaque(false);
		userContainerPanel.setBorder(null);
		userContainerPanel.setLayout(new MigLayout("", "[grow][center][grow]",
				"[119px,grow][5px]"));

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

		// buttons to assign user
		//
		JPanel btnAssignPanel = new JPanel();
		btnAssignPanel.setOpaque(false);
		userContainerPanel.add(btnAssignPanel,
				"flowx,cell 1 0,alignx center,aligny center");
		btnAssignPanel.setLayout(new GridLayout(3, 0, 0, 10));

		Images images = Resources.getInstance().images();
		JLabel btnAssignUser = new JLabel(images.arrowForward());
		btnAssignUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAssignPanel.add(btnAssignUser);

		JLabel btnRemoveUser = new JLabel(images.arrowBack());
		btnRemoveUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAssignPanel.add(btnRemoveUser);

		// TODO display when user do changes
		JLabel btnSaveChanges = new JLabel(images.save());
		btnSaveChanges
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAssignPanel.add(btnSaveChanges);

		// assigned users
		//
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
	}

	private void initFooter() {
		JPanel footerPanel = new JPanel();
		footerPanel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) Color.WHITE));
		footerPanel.setForeground(new Color(245, 245, 245));
		footerPanel.setPreferredSize(new Dimension(10, 25));
		footerPanel.setBackground(Color.GRAY);
		frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);
		footerPanel.setLayout(new MigLayout("", "[grow]", "[]"));

		lblInformation.setForeground(Color.WHITE);
		footerPanel.add(lblInformation, "cell 0 0,alignx left,aligny top");
	}

}
