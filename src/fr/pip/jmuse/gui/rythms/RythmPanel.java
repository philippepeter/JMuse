/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pip.jmuse.gui.rythms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import fr.pip.jmuse.model.Model;
import fr.pip.jmuse.model.notes.Rythm;

/**
 * Panel that allows to select rythms and their probabilities.
 * @author philippepeter
 */
public class RythmPanel extends JPanel {
	
	private List<RythmComponent> components;
	private Model model;
	
	public RythmPanel(Model model) {
		this.model = model;
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(10, 10, 10, 10);
		components = new ArrayList<RythmComponent>();
		components.add(new RythmComponent(Rythm.NOIRE, model));
		components.add(new RythmComponent(Rythm.CROCHE, model));
		components.add(new RythmComponent(Rythm.DOUBLE_CROCHE, model));
		
		
		for(RythmComponent component : components) {
			this.add(component, constraints);
		}
		
	}
	
}
