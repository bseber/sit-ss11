package de.hsma.sit.ss11.frontend.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import de.hsma.sit.ss11.frontend.view.resources.Resources;
import de.hsma.sit.ss11.frontend.view.widgets.NavPanel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;

public class MainWindow {
	
	public interface Delegate {
		
	}

	private final Delegate delegate;
	private final Resources resources;
	
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public MainWindow(Resources resources, Delegate delegate, boolean successfulLogin) {
		this.resources = resources;
		this.delegate = delegate;
		initialize();
	}

	public void setVisible(boolean b) {
		frame.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		NavPanel navPanel = new NavPanel(resources);
		frame.getContentPane().add(navPanel, BorderLayout.NORTH);
		
		JPanel contentPanel = new JPanel();
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(2, 1, 5, 5));
		
		JPanel fileContainerPanel = new JPanel();
		fileContainerPanel.setBorder(new TitledBorder(null, "Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(fileContainerPanel);
		fileContainerPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane fileScrollPane = new JScrollPane();
		fileScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		fileScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fileContainerPanel.add(fileScrollPane);
		
		JPanel userContainerPanel = new JPanel();
		userContainerPanel.setBorder(null);
		contentPanel.add(userContainerPanel);
		userContainerPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Users", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		userContainerPanel.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		userContainerPanel.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[grow][144px][grow]", "[29px,grow][29px][29px][29px,grow]"));
		
		JButton button = new JButton(">>");
		panel_1.add(button, "cell 1 1,grow");
		
		JButton button_1 = new JButton("<<");
		panel_1.add(button_1, "cell 1 2,grow");
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Assigned", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		userContainerPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_2.add(scrollPane_1);
	}
	
}
