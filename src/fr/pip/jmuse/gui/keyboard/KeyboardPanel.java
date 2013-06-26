package fr.pip.jmuse.gui.keyboard;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.pip.jmuse.model.Model;
import fr.pip.jmuse.model.notes.AllNotes;
import fr.pip.jmuse.model.notes.Note;

/**
 * A Panel displaying a dynamic piano keyboard in a scrollPane.
 * @author philippepeter
 *
 */
public class KeyboardPanel extends JScrollPane {

	public enum KeyboardMode {
		NORMAL, SELECT_MIN, SELECT_MAX, NOTES_SELECTION;
	}

	private Vector<Key> keys = new Vector<Key>();
	private int panelHeight;
	private int panelWidth;
	private JPanel panel;
	private Key selectedKey = null;
	private KeyboardMode mode = KeyboardMode.NORMAL;
	private Vector<NoteSelectionListener> listeners = new Vector<NoteSelectionListener>();
	private boolean dragg;

	public KeyboardPanel(int width, int height, Model model) {
		for (Note note : AllNotes.NOTES) {
			keys.add(new Key(note, model));
		}
		this.panelWidth = (keys.size() / 12 + 1) * 7 * Key.WIDTH + 40;
		this.panelHeight = height + 40;

		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw((Graphics2D) g);
			}
		};
		panel.setSize(panelWidth, panelHeight);
		panel.setMinimumSize(new Dimension(panelWidth, panelHeight));
		panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
		this.setSize(width, height);
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.setViewportView(panel);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.getHorizontalScrollBar().setValues(500, 1000, 100, 200);
		panel.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				if (mode == KeyboardMode.NORMAL && selectedKey != null) {
					selectedKey.setPressed(false);
					panel.repaint();
					selectedKey = null;
				}
				if (dragg) {
					for (Key key : keys) {
						if (key.contains(e.getPoint())) {
							fireNoteSelectionEvent(key.getNote());
							if (e.getButton() == MouseEvent.BUTTON1) {
								key.levelUp();
							} else if (e.getButton() == MouseEvent.BUTTON3) {
								key.levelDown();
							}
							repaint();
						}
					}
				}

				dragg = false;

			}

			public void mousePressed(MouseEvent e) {
				for (Key key : keys) {
					if (key.contains(e.getPoint())) {
						if (mode == KeyboardMode.NORMAL) {
							selectedKey = key;
							key.setPressed(true);
							panel.repaint();
						}
					}
				}
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				for (Key key : keys) {
					if (key.contains(e.getPoint())) {
						fireNoteSelectionEvent(key.getNote());
						if (e.getButton() == MouseEvent.BUTTON1) {
							key.levelUp();
						} else if (e.getButton() == MouseEvent.BUTTON3) {
							key.levelDown();
						}
						repaint();
					}
				}
			}
		});

		panel.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent e) {
				for (Key key : keys) {
					key.setRollOvered(key.contains(e.getPoint()));
				}
				repaint();
			}

			public void mouseDragged(MouseEvent e) {
				dragg = true;
			}
		});
	}

	protected void fireNoteSelectionEvent(Note note) {
		for (NoteSelectionListener listener : listeners) {
			listener.noteSelected(note);
		}
	}

	public void draw(Graphics2D g) {
		// Draw all the keys.
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int x = 20;
		for (Key key : keys) {
			if (!key.isSharp()) {
				x += Key.WIDTH;
			}
			key.draw(x, 20, (Graphics2D) g);
		}
	}

}
