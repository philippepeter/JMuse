package fr.pip.jmuse;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.pip.jmuse.gui.keyboard.KeyboardPanel;
import fr.pip.jmuse.gui.menus.Menus;
import fr.pip.jmuse.gui.rythms.RythmPanel;
import fr.pip.jmuse.model.Model;

/**
 * Application class.
 * @author philippepeter
 *
 */
public class JMuse {
	public static void main(String[] args) {
		JFrame frame = new JFrame("JMUSE-By-Pip V0.0.1");
		Model model = new Model();
		RandomPlayer randomPlayer = new RandomPlayer(model);
		randomPlayer.open();
		frame.getContentPane().setLayout(new BorderLayout());
		Menus menu = new Menus();
		KeyboardPanel keyboardPanel = new KeyboardPanel(500,300, model);
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		constraints.gridheight = 2;
		panel.add(new RythmPanel(model), constraints);
		constraints.weightx = 3;
		constraints.gridheight = 1;
		constraints.gridx++;
		panel.add(keyboardPanel, constraints);
		frame.getContentPane().add(menu, BorderLayout.NORTH);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setBounds(width/2-400, height/2-150, 800, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
}
