package de.ZeroxTV.ZTools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
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
	
	ArrayList<String> recent = new ArrayList<String>();
	int recentID = 0;
	int recentMax = 10;
	
	public Main() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {}
		
		frame = new JFrame();
		frame.setTitle("Console");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		console = new JTextPane();
		console.setEditable(false);
		console.setFont(new Font("Courier New", Font.PLAIN, 12));
		console.setOpaque(false);
		
		document = console.getStyledDocument();
		
		input = new JTextField();
		input.setEditable(true);
		input.setBackground(new Color(200, 200, 200));
		input.setCaretColor(Color.BLACK);
		input.setForeground(Color.DARK_GRAY);
		input.setFont(new Font("Courier New", Font.PLAIN, 12));
		
		input.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = input.getText();
				
				if (text.length()>1) {
					recent.add(text);
					recentID = 0;
					
					
					doCommand(text);
					scrollBottom();
					input.setText("");
				}
			}
		});
		
		input.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (recentID < (recentMax - 1) && recentID < (recent.size() - 1)) {
						recentID++;
					}
					input.setText(recent.get(recent.size() - 1 - recentID));
					
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (recentID > 0) {
						recentID--;
					}
					input.setText(recent.get(recent.size() - 1 - recentID));
				}
			}
		});
		scrollpane = new JScrollPane(console);
		scrollpane.setOpaque(false);
		scrollpane.getViewport().setOpaque(false);
		scrollpane.setBorder(null);
		
		frame.add(input, BorderLayout.SOUTH);
		frame.add(scrollpane, BorderLayout.CENTER);
		
		frame.setSize(660, 350);
		frame.setLocationRelativeTo(null);
		
		frame.getContentPane().setBackground(new Color(230, 230, 230));
		
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public void doCommand(String s) {
		final String[] commands = s.split(" ");
		
		try {
			
			//clear
			if (commands[0].equalsIgnoreCase("clear")) {
				clear();
				
			//popup
			} else if (commands[0].equalsIgnoreCase("popup")) {
				String message = "";
				
				for (int i = 1; i < commands.length; i++) {
					message += commands[i];
					
					if (i != commands.length -1) {
						message += " ";
					}
				}
				
				JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
				
			//IP
			} else if (commands[0].equalsIgnoreCase("ip")) {
				
				
				
			} else {
				print(s, false);
			}
		} catch (Exception ex) {
			print("Error > " + ex.getMessage(), false, new Color(255, 0, 0));
		}
	}
	
	public void scrollTop() {
		console.setCaretPosition(0);
	}
	
	public void scrollBottom() {
		console.setCaretPosition(console.getDocument().getLength());
	}
	
	public void write(String s, boolean trace) {
		print(s, trace, new Color(50, 50, 50));
	}
	
	public void write(String s, boolean trace, Color c) {
		Style style = console.addStyle("Style", null);
		StyleConstants.setForeground(style, c);
		
		if (trace) {
			Throwable t = new Throwable();
			StackTraceElement[] elements = t.getStackTrace();
			String caller = elements[0].getClassName();
			
			s = caller + ">" + s;
			
		}
		
		try{
			document.insertString(document.getLength(), s, style);
		} catch (Exception ex) {
			
		}
	}
	
	public void print(String s, boolean trace) {
		print(s, trace, new Color(50, 50, 50));
	}
	
	public void print(String s, boolean trace, Color c) {
		write(s + "\n", trace, c);
	}
	
	public void clear() {
		try {
			document.remove(0, document.getLength());
		} catch (Exception ex) {
			
		}
	}
}
