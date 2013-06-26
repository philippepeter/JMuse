package fr.pip.jmuse.graphx;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import fr.pip.jmuse.graphx.keyboard.NoteSelectionListener;
import fr.pip.jmuse.model.NotePlayedListener;
import fr.pip.jmuse.model.notes.Note;

public class GraphxPanel extends JScrollPane implements NotePlayedListener, NoteSelectionListener {

	private int width;
	private int height;
	private Vector<Note> notes = new Vector<Note>();
	private JPanel panel;
	private AffineTransform rotation;

	public GraphxPanel(int width, int height) {
		this.width = width;
		this.height = height;
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		panel.setSize(width, height);
		panel.setMinimumSize(new Dimension(width, height));
		panel.setPreferredSize(new Dimension(width, height));
		this.setViewportView(panel);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setSize(width, height);
		this.setMinimumSize(new Dimension(width,height));
		this.setPreferredSize(new Dimension(width,height));
		rotation = new AffineTransform();
		
	}

	public void draw(Graphics g) {
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		drawLines(g2D);
		drawNotes(g2D);
	}

	private void drawNotes(Graphics2D g2d) {
		// D5 = re
		// F6 = sol
		int i=0;
		for (Note note : notes) {
			int cy = 70;
			int y = cy - note.getScoreInterval()*5 - ((note.getIndex()/12)-5)*35;
			int x = 30 + i * 30;
			Ellipse2D shape = new Ellipse2D.Double(x - 5, y-4, 10, 8);
			
			rotation = new AffineTransform();
			rotation.rotate(-Math.PI/6, x, y);
			Shape toDraw = rotation.createTransformedShape(shape);
			g2d.fill(toDraw);
			if(note.isSharp()) {
				g2d.drawString("#", x+10,y+5);
			}
			i++;
		}
	}

	private void drawLines(Graphics g) {
		for (int i = 0; i < 5; i++) {
			g.drawLine(20, 20 + i * 10, width - 20, 20 + i * 10);
		}

	}

	public void notePlayed(int note, int velocity, int time) {
		notes.add(new Note(note));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				panel.repaint();
			}
		});
	}

	public void reset() {
		notes.clear();
	}

	public void noteSelected(Note note) {
		notes.add(note);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				panel.repaint();
			}
		});
	}
}
