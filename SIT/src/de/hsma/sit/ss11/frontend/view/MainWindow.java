package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.entities.AnyUser;
import de.hsma.sit.ss11.entities.FileInfo;
import de.hsma.sit.ss11.frontend.Command;
import de.hsma.sit.ss11.frontend.controller.MainWindowController;
import de.hsma.sit.ss11.frontend.resources.Images;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.scaffold.UIHandler;
import de.hsma.sit.ss11.frontend.view.listener.ClickListener;
import de.hsma.sit.ss11.frontend.view.listener.NavBtnMouseListener;
import de.hsma.sit.ss11.frontend.view.widgets.FileListCellPanel;
import de.hsma.sit.ss11.frontend.view.widgets.NavButton;
import de.hsma.sit.ss11.frontend.view.widgets.NavPanel;
import de.hsma.sit.ss11.frontend.view.widgets.PasswordDialog;
import de.hsma.sit.ss11.frontend.view.widgets.SortedListModel;
import de.hsma.sit.ss11.frontend.view.widgets.UserCellPanel;

public class MainWindow implements MainWindowController.MainWindowView {

	public interface MyUIHandler extends UIHandler<MainWindow> {

		void onAddFileClicked(MainWindow mainWindow);

		void onDownloadFileClicked(FileInfo file, String password);

		void onFileSelected(FileInfo file);

		void onInfoClicked(JFrame parent);

		void onLogoutClicked();

		boolean onRemoveFileClicked(FileInfo file);

		boolean onSaveClicked(FileInfo file, List<AnyUser> notAssigned,
				List<AnyUser> assigned, String password);

	}

	private final JFrame frame;
	private final JLabel lblInformation;
	private final MainWindow.MyUIHandler uiHandler;

	private JList allUsersList;
	private SortedListModel allUsersListModel;
	private JList assignedUsersList;
	private SortedListModel assignedUsersListModel;
	private JLabel btnSaveChanges;
	private JList fileList;
	private SortedListModel fileListModel;
	private Resources resources;
	private JSplitPane splitPane;
	private JLabel btnAssignUser;
	private JLabel btnRemoveUser;

	/**
	 * Create the application.
	 */
	public MainWindow(MainWindow.MyUIHandler uiHandler) {
		this.uiHandler = uiHandler;
		resources = Resources.getInstance();
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		lblInformation = new JLabel();
		initialize();
		frame.pack();
	}

	@Override
	public void addFileToList(FileInfo file) {
		fileListModel.add(file);
	}

	/**
	 * @param text
	 *            text which will be displayed for 4 seconds
	 */
	public void setInformationText(final String text) {
		setInformationText(text, 4000);
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

	@Override
	public void setFileList(List<FileInfo> fileList) {
		fileListModel.addAll(fileList.toArray());
	}

	@Override
	public void setAssignedUsers(List<AnyUser> assignedUsers) {
		assignedUsersListModel.clear();
		assignedUsersListModel.addAll(assignedUsers.toArray());
	}

	@Override
	public void setInfoText(String text) {
		setInformationText(text, 3000);
	}

	@Override
	public void setUserList(List<AnyUser> userList) {
		allUsersListModel.clear();
		allUsersListModel.addAll(userList.toArray());
	}

	private void initialize() {
		frame.setMinimumSize(new Dimension(600, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setLocationRelativeTo(null);

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
	}

	private Command getRemoveFileCommand() {
		return new Command() {
			@Override
			public void execute() {
				FileInfo file = (FileInfo) fileList.getSelectedValue();
				if (file != null) {
					boolean success = uiHandler.onRemoveFileClicked(file);
					if (success) {
						fileListModel.removeElement(file);
						setInformationText("Datei wurde erfolgreich gelöscht.");
					} else {
						setInformationText("Datei konnte nicht gelöscht werden.");
					}
				} else {
					setInformationText("Keine Datei ausgewählt.");
				}
			}
		};
	}

	private Command getDownloadFileCommand() {
		return new Command() {
			private PasswordDialog dialog = null;

			@Override
			public void execute() {
				FileInfo selected = (FileInfo) fileList.getSelectedValue();
				if (selected == null) {
					setInformationText("Sie haben keine Datei ausgewählt");
				} else {
					Callback<String> callback = new Callback<String>() {

						@Override
						public void onCallback(String result) {
							FileInfo file = (FileInfo) fileList
									.getSelectedValue();
							uiHandler.onDownloadFileClicked(file, result);
							dialog.dispose();
						}
					};
					dialog = new PasswordDialog(frame, callback);
					dialog.setVisible(true);
				}
			}
		};
	}

	private Command getAddFileCommand() {
		return new Command() {
			@Override
			public void execute() {
				uiHandler.onAddFileClicked(MainWindow.this);
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

		JScrollPane fileScrollPane = new JScrollPane();
		panel.add(fileScrollPane);
		fileListModel = new SortedListModel();
		fileList = new JList(fileListModel);
		fileList.setCellRenderer(new FileListCellRenderer());
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileList.getSelectionModel().addListSelectionListener(
				new FileListSelectionListener());
		fileScrollPane.setViewportView(fileList);
	}

	private void initAssignUserPanel(JSplitPane splitPane) {
		// user overview
		//
		JPanel userContainerPanel = new JPanel();
		userContainerPanel.setMinimumSize(new Dimension(10, 180));
		splitPane.setRightComponent(userContainerPanel);
		userContainerPanel.setOpaque(false);
		userContainerPanel.setBorder(null);
		userContainerPanel.setLayout(new MigLayout("", "[grow 45][center][grow 45]", "[119px,grow][5px]"));

		JPanel allUsersContainer = new JPanel();
		allUsersContainer.setOpaque(false);
		allUsersContainer.setBorder(new TitledBorder(
				new EmptyBorder(0, 0, 0, 0), "Alle Benutzer (System)",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255,
						255, 255)));
		userContainerPanel.add(allUsersContainer, "cell 0 0,grow");
		allUsersContainer.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane allUsersScrollPane = new JScrollPane();
		allUsersScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		allUsersScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		allUsersContainer.add(allUsersScrollPane);
		allUsersListModel = new SortedListModel();
		allUsersList = new JList(allUsersListModel);
		allUsersList.setCellRenderer(new UserListCellRenderer());
		allUsersScrollPane.setViewportView(allUsersList);

		// buttons to assign user
		//
		JPanel btnAssignPanel = new JPanel();
		btnAssignPanel.setOpaque(false);
		userContainerPanel.add(btnAssignPanel,
				"flowx,cell 1 0,alignx center,aligny center");
		btnAssignPanel.setLayout(new GridLayout(3, 0, 0, 10));

		Images images = Resources.getInstance().images();
		btnAssignUser = new JLabel(images.arrowForward());
		btnAssignUser.setEnabled(false);
		btnAssignUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAssignPanel.add(btnAssignUser);

		btnRemoveUser = new JLabel(images.arrowBack());
		btnRemoveUser.setEnabled(false);
		btnRemoveUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAssignPanel.add(btnRemoveUser);

		btnSaveChanges = new JLabel(images.save());
		btnSaveChanges.setVisible(false);
		btnSaveChanges
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSaveChanges.addMouseListener(new SaveListener());
		btnAssignPanel.add(btnSaveChanges);

		// assigned users
		//
		JPanel assignedUsersContainer = new JPanel();
		assignedUsersContainer.setOpaque(false);
		assignedUsersContainer.setBorder(new TitledBorder(new EmptyBorder(0, 0,
				0, 0), "Zugewiesene Benutzer (Datei)", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(255, 255, 255)));
		userContainerPanel.add(assignedUsersContainer, "cell 2 0,grow");
		assignedUsersContainer.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane assignedUsersScrollPane = new JScrollPane();
		assignedUsersScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		assignedUsersScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		assignedUsersContainer.add(assignedUsersScrollPane);

		assignedUsersListModel = new SortedListModel();
		assignedUsersList = new JList(assignedUsersListModel);
		assignedUsersList.setCellRenderer(new UserListCellRenderer());
		assignedUsersScrollPane.setViewportView(assignedUsersList);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getAllElementsOfJList(ListModel model) {
		int size = model.getSize();
		ArrayList list = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			list.add(model.getElementAt(i));
		}
		return list;
	}

	private void setUserAssignBtnsEnabled(boolean enabled) {
		btnAssignUser.setEnabled(enabled);
		btnRemoveUser.setEnabled(enabled);
		if (enabled) {
			btnAssignUser.addMouseListener(assignUserListener);
			btnRemoveUser.addMouseListener(notAssignUserListener);
		} else {
			btnAssignUser.removeMouseListener(assignUserListener);
			btnRemoveUser.removeMouseListener(notAssignUserListener);
		}
	}

	private ClickListener assignUserListener = new ClickListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			Object[] values = allUsersList.getSelectedValues();
			assignedUsersListModel.addAll(values);
			// refresh allUsersList
			Object[] selected = allUsersList.getSelectedValues();
			for (int i = selected.length - 1; i >= 0; i--) {
				allUsersListModel.removeElement(selected[i]);
			}
			allUsersList.getSelectionModel().clearSelection();
			btnSaveChanges.setVisible(true);
		}
	};

	private ClickListener notAssignUserListener = new ClickListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			Object[] values = assignedUsersList.getSelectedValues();
			allUsersListModel.addAll(values);
			// refresh assignedUsersList
			Object[] selected = assignedUsersList.getSelectedValues();
			for (int i = selected.length - 1; i >= 0; i--) {
				assignedUsersListModel.removeElement(selected[i]);
			}
			assignedUsersList.getSelectionModel().clearSelection();
			btnSaveChanges.setVisible(true);
		}
	};

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private class SaveListener extends ClickListener {
		private PasswordDialog dialog;
		@Override
		public void mouseClicked(MouseEvent e) {
			Callback<String> callback = new Callback<String>() {
				@Override
				public void onCallback(String password) {
					// save changes (user assignments)
					List notAssigned = getAllElementsOfJList(allUsersListModel);
					List assigned = getAllElementsOfJList(assignedUsersListModel);
					FileInfo file = (FileInfo) fileList.getSelectedValue();
					boolean success = uiHandler.onSaveClicked(file,
							notAssigned, assigned, password);
					if (success) {
						setInformationText("Änderungen wurden erfolgreich gespeichert.");
						btnSaveChanges.setVisible(false);
					} else {
						setInformationText("Änderungen konnten nicht gespeichert werden.");
					}
					dialog.dispose();
				}
			};
			dialog = new PasswordDialog(frame, callback);
			dialog.setVisible(true);
		}
	}

	private class FileListCellRenderer implements ListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			FileInfo file = (FileInfo) value;
			Images img = Resources.getInstance().images();
			// select editable or readable icon
			ImageIcon icon = file.getMaster() ? img.fileEditable() : img
					.fileReadable();
			return new FileListCellPanel(file.getFileName(), icon, isSelected);
		}
	}

	private class FileListSelectionListener implements ListSelectionListener {

		private FileInfo prevFile = null;

		@Override
		public void valueChanged(ListSelectionEvent e) {
			FileInfo file = (FileInfo) fileList.getSelectedValue();
			if (file == null || !file.getMaster()) {
				// nothing is selected anymore
				// or user is only able to read
				setUserAssignBtnsEnabled(false);
				btnSaveChanges.setVisible(false);
				assignedUsersListModel.clear();
				if (file != null && !file.getMaster()) {
					setInformationText("Ausgewählte Datei kann nur gelesen werden.");
				}
			} else if (!file.equals(prevFile)) {
				// another file has been selected
				setUserAssignBtnsEnabled(true);
				btnSaveChanges.setVisible(false);
				uiHandler.onFileSelected(file);
				setInformationText("");
			}
			prevFile = file;
		}
	}

	private class UserListCellRenderer implements ListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			AnyUser u = (AnyUser) value;
			return new UserCellPanel(u.getName(), isSelected);
		}
	}

}
