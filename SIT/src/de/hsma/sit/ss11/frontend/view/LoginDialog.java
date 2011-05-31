package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.frontend.controller.LoginController;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.scaffold.UIHandler;
import de.hsma.sit.ss11.frontend.view.widgets.DialogHeaderPanel;

public class LoginDialog extends JDialog implements LoginController.LoginView {

	public interface MyUIHandler extends UIHandler {
		
		void onCancelClicked();

		void onLoginClicked(JDialog parent, String username, char[] password);
		
		void onRegisterClicked();
	}

	private final JPanel contentPanel;
	private final MyUIHandler uiHandler;
	private JTextField usernameTextfield;
	private JPasswordField passwordField;

	public LoginDialog(LoginDialog.MyUIHandler uiHandler) {
		this.contentPanel = new JPanel();
		this.uiHandler = uiHandler;
		init();
		this.pack();
		setLocationRelativeTo(null);
	}

	private void init() {
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 15, 0, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		Resources resources = Resources.getInstance();
		initHeaderPanel(resources);
		initContentPanel(resources);
		initButtonPane(resources);
	}

	private void initHeaderPanel(Resources resources) {
		JPanel dialogHeaderPanel = new DialogHeaderPanel(resources
				.images().lock(), resources.messages().login(), resources
				.messages().loginDescription(), this.getWidth());
		getContentPane().add(dialogHeaderPanel, BorderLayout.NORTH);
	}

	private void initContentPanel(Resources resources) {
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		final JLabel lblRegisterNewAccount = new JLabel("Registrieren");
		lblRegisterNewAccount.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				uiHandler.onRegisterClicked();
			}
		});
		lblRegisterNewAccount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegisterNewAccount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPanel.add(lblRegisterNewAccount, "cell 1 0,alignx right");
		
		JLabel lblUsername = new JLabel("Benutzername:");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblUsername, "cell 0 1,alignx trailing");
		
		usernameTextfield = new JTextField();
		contentPanel.add(usernameTextfield, "cell 1 1,growx");
		usernameTextfield.setColumns(10);
		
		JLabel lblPassword = new JLabel("Passwort:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblPassword, "cell 0 2,alignx trailing");
		
		passwordField = new JPasswordField();
		contentPanel.add(passwordField, "cell 1 2,growx");
		passwordField.setColumns(10);
		
	}

	private void initButtonPane(Resources resources) {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton loginBtn = new JButton(resources.messages().login());
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				uiHandler.onLoginClicked(LoginDialog.this,
						usernameTextfield.getText(),
						passwordField.getPassword());
			}
		});
		buttonPane.add(loginBtn);
		getRootPane().setDefaultButton(loginBtn);

		JButton cancelBtn = new JButton(resources.messages().cancel());
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				uiHandler.onCancelClicked();
			}
		});
		buttonPane.add(cancelBtn);
	}

}
