package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import de.hsma.sit.ss11.frontend.resources.Resources;

public class ErrorDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * @wbp.parser.constructor
	 */
	public ErrorDialog(JDialog parent, String title, String errorMsg) {
		super(parent, title, true);
		init(errorMsg);
	}

	public ErrorDialog(JFrame parent, String title, String errorMsg) {
		super(parent, title, true);
		init(errorMsg);
	}

	private void init(String errorMsg) {
		setMinimumSize(new Dimension(300, 140));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 300, 120);
		this.setLocationRelativeTo(this.getParent());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[70px,center][grow]",
				"[grow,center]"));
		{
			JLabel icon = new JLabel(Resources.getInstance().images().warning());
			contentPanel.add(icon, "cell 0 0");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("<html><p>" + errorMsg
					+ "</p></html>");
			contentPanel.add(lblNewLabel_1,
					"cell 1 0,alignx left,aligny center");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Schade");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ErrorDialog.this.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
