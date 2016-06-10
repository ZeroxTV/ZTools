package de.ZeroxTV.ZTools;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

public class Main {
	
	public static void main(String[] args) {
		new Main();
	}
	
	public JFrame frame;
	public JTextPane console;
	public JTextField input;
	public JScrollPane scrollpane;
	
	public StyledDocument document;
	
	boolean trace = false;
}
