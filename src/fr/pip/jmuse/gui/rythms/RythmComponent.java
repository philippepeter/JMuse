package fr.pip.jmuse.gui.rythms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

import fr.pip.jmuse.model.Model;
import fr.pip.jmuse.model.notes.Rythm;

/**
 * Component that display a rythm element and allow to select a probability.
 * @author philippepeter
 *
 */
public class RythmComponent extends JComponent{

	private final static int WIDTH = 50;
	private final static int HEIGHT = 50;
	private final static Dimension DIM = new Dimension(WIDTH,HEIGHT);
	private final static Color colorLevel1 = new Color(254,6,0);
	private final static Color colorLevel2 = new Color(254,139,0);
	private final static Color colorLevel3 = new Color(254,244,2);
	private final static Color colorLevel4 = new Color(166,254,2);
	private final static Color colorLevel5 = new Color(6,254,0);
	private int level = 0;
	private Rythm rythm;
	private BufferedImage image;
	private boolean dragg;
	private Model model;
	private boolean rollOvered;
	
	public RythmComponent(Rythm rythm, Model model) {
		this.rythm = rythm;
		this.model = model;
		try {
			switch (rythm) {
			case NOIRE:
				image = ImageIO.read(RythmComponent.class.getResourceAsStream("Noire.png"));
				break;
			case CROCHE:
				image = ImageIO.read(RythmComponent.class.getResourceAsStream("Croche.png"));
				break;
			case DOUBLE_CROCHE:
				image = ImageIO.read(RythmComponent.class.getResourceAsStream("DoubleCroche.png"));
				break;
			default:
				break;
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					RythmComponent.this.levelUp();
				} else if(e.getButton() == MouseEvent.BUTTON3) {
					RythmComponent.this.levelDown();
				}
				repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(dragg) {
					
					if(e.getButton() == MouseEvent.BUTTON1) {
						RythmComponent.this.levelUp();
					} else if(e.getButton() == MouseEvent.BUTTON3) {
						RythmComponent.this.levelDown();
					}
					repaint();
				}
				dragg = false;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				rollOvered = true;
				repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rollOvered = false;
				repaint();
			}
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				dragg = true;
				repaint(); 
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, 50, 50,null);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(rollOvered) {
			g.setColor(Color.GRAY);
		} else {
			g.setColor(Color.BLACK);
		}
		g.drawRect(0, 0, WIDTH-1,HEIGHT-1);
		g.setColor(Color.BLACK);

		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2.0f));
		

		g2d.setStroke(stroke);
		int x=WIDTH-6;
		int y = HEIGHT - 6;
		drawCircle(colorLevel1, x, y, g2d, level > 0);
		drawCircle(colorLevel2, x, y-6, g2d, level > 1);
		drawCircle(colorLevel3, x, y-12, g2d, level > 2);
		drawCircle(colorLevel4, x, y-18, g2d, level > 3);
		drawCircle(colorLevel5, x, y-24, g2d, level > 4);
	}
	
	@Override
	public Dimension getSize() {
		return DIM;
	}
	
	@Override
	@Transient
	public Dimension getPreferredSize() {
		return DIM;
	}
	
	@Override
	public Dimension getMinimumSize() {
		return DIM;
	}
	
	@Override
	public Dimension getMaximumSize() {
		return DIM;
	}
	
	public void levelUp() {
		if(level != 5) {
			level++;
			model.setRythm(rythm, level);
		}
	}

	public void levelDown() {
		if(level != 0) {
			level--;
			model.setRythm(rythm, level);
		}
	}
	
	private void drawCircle(Color color, int x, int y, Graphics2D g, boolean fill) {
			double radius = 2;
			Shape circle = new Ellipse2D.Double(x-radius, y-radius,2*radius, 2*radius);
			g.setColor(color);
			if(fill) {
				g.setColor(color);
				g.fill(circle);
			}
			g.setColor(Color.BLACK);
			g.draw(circle);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		frame.add(new RythmComponent(Rythm.CROCHE, new Model()), constraints);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
