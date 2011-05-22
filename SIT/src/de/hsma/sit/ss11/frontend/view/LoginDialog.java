package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.frontend.controller.LoginController;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.widgets.DialogHeaderPanel;

public class LoginDialog extends JDialog implements LoginController.LoginView {

	public interface UIHandler {

		void onLoginClicked(JDialog parent, String username, char[] password);

		void onCancelClicked();
	}

	private final JPanel contentPanel;
	private final UIHandler delegate;

	private JTextField usernameTextfield;
	private JPasswordField passwordField;

	public LoginDialog(LoginDialog.UIHandler delegate) {
		this.contentPanel = new JPanel();
		this.delegate = delegate;
		init();
	}

	private void init() {
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		Resources resources = Resources.getInstance();
		initHeaderPanel(resources);
		initFormPanel(resources);
		initButtonPane(resources);
	}

	private void initHeaderPanel(Resources resources) {
		String heading = resources.messages().login();
		String description = resources.messages().loginDescription();
		ImageIcon icon = resources.images().lock();
		DialogHeaderPanel dialogHeaderPanel = new DialogHeaderPanel(icon,
				heading, description, this.getWidth());
		contentPanel.add(dialogHeaderPanel, BorderLayout.NORTH);
	}

	private void initFormPanel(Resources resources) {
		JPanel formPanel = new JPanel();
		contentPanel.add(formPanel, BorderLayout.CENTER);
		formPanel.setLayout(new MigLayout("", "[15.00][][278.00,grow][15.00]",
				"[01.00,grow][25.00][25.00][01.00,grow]"));

		JLabel lblUsername = new JLabel(resources.messages().username() + ":");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(lblUsername, "cell 1 1,alignx trailing");

		usernameTextfield = new JTextField();
		usernameTextfield.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(usernameTextfield, "cell 2 1,grow");
		usernameTextfield.setColumns(10);

		JLabel lblPassword = new JLabel(resources.messages().password() + ":");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(lblPassword, "cell 1 2,alignx trailing");

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formPanel.add(passwordField, "cell 2 2,grow");
	}

	private void initButtonPane(Resources resources) {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton loginBtn = new JButton(resources.messages().login());
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delegate.onLoginClicked(LoginDialog.this, usernameTextfield.getText(),
						passwordField.getPassword());
			}
		});
		buttonPane.add(loginBtn);
		getRootPane().setDefaultButton(loginBtn);

		JButton cancelBtn = new JButton(resources.messages().cancel());
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delegate.onCancelClicked();
			}
		});
		buttonPane.add(cancelBtn);
	}

}
