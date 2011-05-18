package de.hsma.sit.ss11.frontend.view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.SwingConstants;

public class MainWindow {
	
	public interface Delegate {
		
	}

	private final Delegate delegate;
	
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public MainWindow(Delegate delegate, boolean successfulLogin) {
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
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnSomemenu = new JMenu("SomeMenu...");
		menuBar.add(mnSomemenu);
		
		JMenuItem mntmA = new JMenuItem("a");
		mnSomemenu.add(mntmA);
		
		JMenuItem mntmB = new JMenuItem("b");
		mnSomemenu.add(mntmB);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(mntmLogout);
	}
	
}
