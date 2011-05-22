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

import de.hsma.sit.ss11.frontend.resources.Resources;
import de.hsma.sit.ss11.frontend.view.widgets.DialogHeaderPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class InfoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public InfoDialog(JFrame parent) {
		super(parent, "", true);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(new BorderLayout());
		{
			Resources resources = Resources.getInstance();
			DialogHeaderPanel headerPanel = new DialogHeaderPanel(resources
					.images().infoBig(), "Info", "", this.getWidth());
			getContentPane().add(headerPanel, BorderLayout.NORTH);
		}
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow,center]", "[][][][][][][]"));
		{
			JLabel lblProgrammedBy = new JLabel("Programmers (alphabetical order)");
			lblProgrammedBy.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblProgrammedBy, "cell 0 0");
		}
		{
			JLabel lblAndreasHofer = new JLabel("Andreas Hofer");
			contentPanel.add(lblAndreasHofer, "cell 0 1");
		}
		{
			JLabel lblJeffreyKovacz = new JLabel("Jeffrey Kovacs");
			contentPanel.add(lblJeffreyKovacz, "cell 0 2");
		}
		{
			JLabel lblTimMaas = new JLabel("Tim Maas");
			contentPanel.add(lblTimMaas, "cell 0 3");
		}
		{
			JLabel lblJuanJimenezNacimiento = new JLabel("Juan Jimenez Nacimiento");
			contentPanel.add(lblJuanJimenezNacimiento, "cell 0 4");
		}
		{
			JLabel lblBenjaminSeber = new JLabel("Benjamin Seber");
			contentPanel.add(lblBenjaminSeber, "cell 0 5");
		}
		{
			JLabel lblMehmetnltepe = new JLabel("Mehmet \u00DCnl\u00FCtepe");
			contentPanel.add(lblMehmetnltepe, "cell 0 6");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Gut gemacht!");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						InfoDialog.this.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
