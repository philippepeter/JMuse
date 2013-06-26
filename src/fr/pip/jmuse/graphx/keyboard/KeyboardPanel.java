package fr.pip.jmuse.graphx.keyboard;

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
import fr.pip.jmuse.model.NotePlayer;
import fr.pip.jmuse.model.notes.AllNotes;
import fr.pip.jmuse.model.notes.Note;

public class KeyboardPanel extends JScrollPane {

	public enum KeyboardMode {
		NORMAL, SELECT_MIN, SELECT_MAX, NOTES_SELECTION;
	}

	private Vector<Key> keys = new Vector<Key>();
	private int panelHeight;
	private int panelWidth;
	private JPanel panel;
	private NotePlayer notePlayer;
	private Key selectedKey = null;
	private Key minKey = null;
	private Key maxKey = null;
	private KeyboardMode mode = KeyboardMode.NORMAL;
	private Vector<NoteSelectionListener> listeners = new Vector<NoteSelectionListener>();
	private boolean dragg;
	private Model model;
	
	public KeyboardPanel(int width, int height, final NotePlayer notePlayer, Model model) {
		this.model = model;
		this.notePlayer = notePlayer;
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
					notePlayer.stopPlayingNote(
							selectedKey.getNote().getIndex(), 80);
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
							notePlayer.startPlayingNote(key.getNote()
									.getIndex(), 80);
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
						if (mode == KeyboardMode.SELECT_MIN) {
							minKey = key;
							notePlayer.playNote(key.getNote().getIndex(), 80);
							repaint();
						} else if (mode == KeyboardMode.SELECT_MAX) {
							maxKey = key;
							notePlayer.playNote(key.getNote().getIndex(), 80);
							repaint();
						}
						if (e.getButton() == MouseEvent.BUTTON1) {
							key.levelUp();
						} else if (e.getButton() == MouseEvent.BUTTON3) {
							key.levelDown();
						}
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
	
	public KeyboardPanel(int width, int height, final NotePlayer notePlayer) {
		this(width, height, notePlayer, null);
	}

	protected void fireNoteSelectionEvent(Note note) {
		for (NoteSelectionListener listener : listeners) {
			listener.noteSelected(note);
		}
	}

	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int x = 20;
		for (Key key : keys) {
			if (!key.isSharp()) {
				x += key.WIDTH;
			}
			key.draw(x, 20, (Graphics2D) g);
			// if (key.equals(minKey)) {
			// minKey.drawMinCircle(x, 20, g);
			// }
			// if (key.equals(maxKey)) {
			// maxKey.drawMaxCircle(x, 20, g);
			// }
		}
	}

	public void notePlayed(int note, int velocity, int time) {
	}

	public void reset() {
	}

	public void addNoteSelectionListener(
			NoteSelectionListener noteSelectionListener) {
		listeners.add(noteSelectionListener);
	}

	public void removeNoteSelectionListener(
			NoteSelectionListener noteSelectionListener) {
		listeners.remove(noteSelectionListener);
	}

	public void setMinNote(int selectedIndex) {
		minKey = keys.get(selectedIndex);
		repaint();
	}

	public void setMaxNote(int selectedIndex) {
		maxKey = keys.get(selectedIndex);
		repaint();

	}

}
