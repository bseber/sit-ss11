package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.widgets.DialogHeaderPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class AddUserDialog extends JDialog {

	public interface UIHandler {
		void addUser(String username, String password);
	}

	private final JPanel contentPanel = new JPanel();
	private JTextField usernameTxtField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	private final UIHandler delegate;

	public AddUserDialog(JFrame parent, Resources resources, UIHandler delegate) {
		super(parent, "", true);
		this.delegate = delegate;
		init(parent, resources);
	}

	private void init(JFrame parent, Resources resources) {
		setResizable(false);
		setBounds(100, 100, 450, 250);
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
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][]"));

		JLabel lblUsername = new JLabel("Benutzername:");
		contentPanel.add(lblUsername, "cell 0 0,alignx trailing");

		usernameTxtField = new JTextField();
		contentPanel.add(usernameTxtField, "cell 1 0,growx");
		usernameTxtField.setColumns(10);

		JLabel lblPassword = new JLabel("Passwort:");
		contentPanel.add(lblPassword, "cell 0 1,alignx trailing");

		passwordField = new JPasswordField();
		contentPanel.add(passwordField, "cell 1 1,growx");

		JLabel lblPassword2 = new JLabel("Passwort wiederholen:");
		contentPanel.add(lblPassword2, "cell 0 2,alignx trailing");

		passwordField2 = new JPasswordField();
		contentPanel.add(passwordField2, "cell 1 2,growx");
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
				delegate.addUser(usernameTxtField.getText(), passwordField
						.getPassword().toString());
			}
		});
		return okButton;
	}

	private JButton initCancelButton() {
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				usernameTxtField.setText("");
				passwordField.setText("");
				passwordField2.setText("");
				AddUserDialog.this.dispose();
			}
		});
		return cancelButton;
	}

}
