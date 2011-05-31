package de.hsma.sit.ss11.frontend.view.widgets;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.frontend.resources.Images;
import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.Callback;

@SuppressWarnings("serial")
public class PasswordDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JPasswordField passwordField;

	public PasswordDialog(JFrame parent, final Callback<String> callback) {
		super(parent, "", true);
		getContentPane().setLayout(new BorderLayout());
		Images img = Resources.getInstance().images();

		JPanel panel = new DialogHeaderPanel(img.lock(), "Passwort",
				"Bitte geben Sie Ihr Passwort ein", this.getWidth());
		getContentPane().add(panel, BorderLayout.NORTH);

		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[]"));

		JLabel lblPassword = new JLabel("Passwort:");
		contentPanel.add(lblPassword, "cell 0 0,alignx trailing");

		passwordField = new JPasswordField();
		contentPanel.add(passwordField, "cell 1 0,growx");

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				callback.onCallback(String.valueOf(passwordField.getPassword()));
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PasswordDialog.this.dispose();
			}
		});
		buttonPane.add(cancelButton);
		
		this.pack();
		setLocationRelativeTo(parent);
	}
	
}
