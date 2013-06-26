package fr.pip.jmuse.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.pip.jmuse.gui.bundles.JMuseBundle;

public class Menus extends JMenuBar{
	
	public Menus() {
		JMenu menu = new JMenu(JMuseBundle.get("File"));
		JMenuItem quit = new JMenuItem(JMuseBundle.get("Quit"));
		menu.add(quit);
		this.add(menu);
		
		quit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}

}
