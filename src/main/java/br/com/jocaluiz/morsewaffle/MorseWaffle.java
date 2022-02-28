package br.com.jocaluiz.morsewaffle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import br.com.jocaluiz.morsewaffle.extenso.Extenso;
import br.com.jocaluiz.morsewaffle.Connection.Tables;
import br.com.jocaluiz.morsewaffle.Mensager.MorseMensager;
import br.com.jocaluiz.morsewaffle.Mensager.Recive;
import br.com.jocaluiz.morsewaffle.morse.Morse;

public class MorseWaffle {
	public static Dimension frameSize = new Dimension(500, 500);
	public static JFrame frame;
	public static String NEW_LINE = "\n";
	public static JTextArea outputArea;
	public static PlaceholderTextField inputArea;
	public static String inputResults;
	public static WaffleCommands mc = new WaffleCommands();
	
	public static void main(String[]args) {
		// Creating tables
		Tables.createTables();

		// Creating frame
		frame = new JFrame("Morse Waffle");
		Container c = frame.getContentPane();
		
		// Change background color
		c.setBackground(Color.black);
		
		// Creating Label
		JLabel title = appTitle(5, 10, 500, 50, "Morse Waffle", Color.green);
		c.add(title);
		
		// Creating console
		outputArea = outputAreaConfig(5, 60, 475, 350, Color.black, Color.green);
		c.add(outputArea);
		
		// Creating console input
		inputArea = mainTextField(5, 415, 475, 30, "Digite seu comando", Color.black, Color.green);
		enterTrigger();
		c.add(inputArea);
		
		// Setting frame
		frameConfig(frame, frameSize, true);
	}
	
	public static void commands() {
		// help command
		mc.help("help", inputResults, outputArea);

		// Morse command
		String[] morseParams = {"-t ", "-c "};
		Morse.morse("morse ", morseParams, inputResults, outputArea);

		// Full Number command
		Extenso.fullNumber("fullnum ", inputResults, outputArea);

		// Mensager
		MorseMensager.mensager("mensager ", inputResults, outputArea);
		
		// Command 01
		mc.echo("echo ", inputResults, outputArea);
		
		// Command 02
		mc.clearCommand("cls", inputResults, outputArea);
		
		// Command 03
		mc.browser("browser", inputResults, outputArea);
	}

	public static void frameConfig(JFrame f, Dimension frameDimension, boolean frameVisible) {
		f.setSize(frameDimension);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);	
		f.setVisible(frameVisible);
	}
	
	public static JLabel appTitle(int x, int y, int w, int h, String label, Color pColor) {
		JLabel l = new JLabel(label);
		l.setFont(new Font("Arial", Font.BOLD, 20));
		l.setBounds(x, y, w, h);
		l.setForeground(pColor);
		return l;
	}

	public static PlaceholderTextField mainTextField(int x, int y, int w, int h, String tPlaceholderText, Color pColor, Color sColor) {
		PlaceholderTextField t = new PlaceholderTextField();
		t.setBounds(x, y, w, h);
		t.setPlaceholder(tPlaceholderText);
		t.setBackground(pColor);
		t.setForeground(sColor);
		t.setCaretColor(sColor);
		t.setBorder(new LineBorder(sColor));
		return t;
	}
	
	public static JTextArea outputAreaConfig(int x, int y, int w, int h, Color pColor, Color sColor) {
		JTextArea o = new JTextArea("Type 'help' to see commands. Author: Joca" + NEW_LINE + NEW_LINE);
		o.setBounds(x, y, w, h);
		o.setForeground(sColor);
		o.setBackground(pColor);
		o.setCaretColor(sColor);
		o.setBorder(new LineBorder(sColor));
		o.setLineWrap(true); //Set line wrap
	    o.setWrapStyleWord(true); //set word wrap
		o.setEditable(false);
		return o;
	}
	
	public static void enterTrigger() {
		inputArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
               if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            	   inputResults = inputArea.getText();
				   if (MorseMensager.modoDoMensager) {
						MorseMensager.send(inputResults);
				   } else {
						commands();
				   }
            	   inputArea.setText(null);
               }
            }
        });
	}

}