package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.widgets.DialogHeaderPanel;

public class AddUserDialog extends JDialog {

	public interface UIHandler {
		boolean addUser(String username, String password, String privatePassword);
	}

	private final JPanel contentPanel = new JPanel();
	private final MainWindow mainWindow;
	private final UIHandler uiHandler;

	private JTextField usernameTxtField;
	private JPasswordField loginPassField;
	private JPasswordField loginPassField2;
	private JPasswordField privatePassField;
	private JPasswordField privatePassField2;
	private JLabel icnLogPassError;
	private JLabel icnPrivPass2Error;
	private JLabel icnPrivPassError;

	public AddUserDialog(MainWindow mainWindow, JFrame parent,
			Resources resources, AddUserDialog.UIHandler uiHandler) {
		super(parent, "", true);
		this.mainWindow = mainWindow;
		this.uiHandler = uiHandler;
		init(parent, resources);
	}

	private void init(JFrame parent, Resources resources) {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(new BorderLayout());

		initHeaderPanel(resources);
		initContentPanel();
		initButtonPane();
	}

	private void initHeaderPanel(Resources resources) {
		DialogHeaderPanel headerPanel = new DialogHeaderPanel(resources
				.images().user(), resources.messages().addUser(), "",
				this.getWidth());
		getContentPane().add(headerPanel, BorderLayout.NORTH);
	}

	private void initContentPanel() {
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("",
				"[20px:n:20px][][grow][20px:n:20px]", "[][][][][]"));

		JLabel lblUsername = new JLabel("Benutzername:");
		contentPanel.add(lblUsername, "cell 1 0,alignx trailing");

		usernameTxtField = new JTextField();
		contentPanel.add(usernameTxtField, "cell 2 0,growx");
		usernameTxtField.setColumns(10);

		JLabel lblLoginPass = new JLabel("Login-Passwort:");
		contentPanel.add(lblLoginPass, "cell 1 1,alignx trailing");

		loginPassField = new JPasswordField();
		contentPanel.add(loginPassField, "cell 2 1,growx");

		JLabel lblLoginPass2 = new JLabel("Login-Passwort wdh:");
		contentPanel.add(lblLoginPass2, "cell 1 2,alignx trailing");

		loginPassField2 = new JPasswordField();
		loginPassField2.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				char[] a = loginPassField.getPassword();
				char[] b = loginPassField2.getPassword();
				if (validatePasswords(a, b)) {
					loginPassField2.setBorder(UIManager
							.getBorder("TextField.border"));
					loginPassField2.setBackground(Color.white);
					icnLogPassError.setVisible(false);
				} else {
					loginPassField2.setBorder(BorderFactory
							.createLineBorder(Color.red));
					loginPassField2.setBackground(Color.pink);
					icnLogPassError.setVisible(true);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}

		});
		contentPanel.add(loginPassField2, "cell 2 2,growx");

		icnLogPassError = new JLabel(Resources.getInstance().images()
				.inputError());
		icnLogPassError.setToolTipText("Passwörter stimmen nicht überein.");
		icnLogPassError.setVisible(false);
		contentPanel.add(icnLogPassError, "cell 3 2");

		JLabel lblPrivatePass = new JLabel("Private-Passwort:");
		contentPanel.add(lblPrivatePass, "cell 1 3,alignx trailing");

		privatePassField = new JPasswordField();
		privatePassField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				char[] password = privatePassField.getPassword();
				if (password.length != 8) {
					privatePassField.setBorder(BorderFactory
							.createLineBorder(Color.red));
					privatePassField.setBackground(Color.pink);
					icnPrivPassError.setVisible(true);
				} else {
					privatePassField.setBorder(UIManager
							.getBorder("TextField.border"));
					privatePassField.setBackground(Color.white);
					icnPrivPassError.setVisible(false);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		contentPanel.add(privatePassField, "cell 2 3,growx");

		icnPrivPassError = new JLabel(Resources.getInstance().images()
				.inputError());
		icnPrivPassError
				.setToolTipText("Passwort muss aus genau 8 Zeichen bestehen.");
		icnPrivPassError.setVisible(false);
		contentPanel.add(icnPrivPassError, "cell 3 3");

		JLabel lblPrivatePass2 = new JLabel("Private-Passwort wdh:");
		contentPanel.add(lblPrivatePass2, "cell 1 4,alignx trailing");

		privatePassField2 = new JPasswordField();
		privatePassField2.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				char[] a = privatePassField.getPassword();
				char[] b = privatePassField2.getPassword();
				if (validatePasswords(a, b)) {
					privatePassField2.setBorder(UIManager
							.getBorder("TextField.border"));
					privatePassField2.setBackground(Color.white);
					icnPrivPass2Error.setVisible(false);
				} else {
					privatePassField2.setBorder(BorderFactory
							.createLineBorder(Color.red));
					privatePassField2.setBackground(Color.pink);
					icnPrivPass2Error.setVisible(true);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		contentPanel.add(privatePassField2, "cell 2 4,growx");

		icnPrivPass2Error = new JLabel(Resources.getInstance().images()
				.inputError());
		icnPrivPass2Error.setToolTipText("Passwörter stimmen nicht überein.");
		icnPrivPass2Error.setVisible(false);
		contentPanel.add(icnPrivPass2Error, "cell 3 4");
	}

	private boolean validatePasswords(char[] a, char[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	private void initButtonPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton saveButton = initSaveButton();
		buttonPane.add(saveButton);
		getRootPane().setDefaultButton(saveButton);

		JButton cancelButton = initCancelButton();
		buttonPane.add(cancelButton);
	}

	private JButton initSaveButton() {
		JButton okButton = new JButton("Speichern");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean success = uiHandler.addUser(usernameTxtField.getText(),
						String.valueOf(loginPassField.getPassword()),
						String.valueOf(privatePassField.getPassword()));
				if (success) {
					resetInputFields();
					mainWindow.refreshUserList();
					AddUserDialog.this.dispose();
				} else {
					new ErrorDialog(AddUserDialog.this, "",
							"Benutzer konnte nicht hinzugefügt werden.")
							.setVisible(true);
				}
			}
		});
		return okButton;
	}

	private JButton initCancelButton() {
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetInputFields();
				AddUserDialog.this.dispose();
			}
		});
		return cancelButton;
	}

	private void resetInputFields() {
		usernameTxtField.setText("");
		loginPassField.setText("");
		loginPassField2.setText("");
		privatePassField.setText("");
		privatePassField2.setText("");
	}

}
