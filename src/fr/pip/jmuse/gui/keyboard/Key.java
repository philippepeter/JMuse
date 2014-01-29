package fr.pip.jmuse.gui.keyboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import fr.pip.jmuse.model.Model;
import fr.pip.jmuse.model.notes.Note;

/**
 * Draw a key of the {@link KeyboardPanel} with is probability level.
 * 
 * @author philippepeter
 * 
 */
public class Key {

	public final static int WIDTH = 20;
	public final static int HEIGHT = 150;

	// Colors for probability levels.
	private final static Color colorLevel1 = new Color(254, 6, 0);
	private final static Color colorLevel2 = new Color(254, 139, 0);
	private final static Color colorLevel3 = new Color(254, 244, 2);
	private final static Color colorLevel4 = new Color(166, 254, 2);
	private final static Color colorLevel5 = new Color(6, 254, 0);

	private Note note;
	private int blackWidth;
	private int blackHeight;
	private int littleDelta;
	private Shape shape;
	private boolean pressed;
	private int level;
	private boolean rollOvered;
	private Model model;

	public Key(Note note, Model model) {
		this.note = note;
		this.model = model;
		this.level = 0;
		double blackWidthd = WIDTH * 60 / 100;
		blackWidth = (int) Math.round(blackWidthd);
		double blackHeightd = HEIGHT * 60 / 100;
		blackHeight = (int) Math.round(blackHeightd);
		littleDelta = 2 * blackWidth - WIDTH;
	}

	public void draw(int x, int y, Graphics2D g) {
		g.setColor(Color.BLACK);
		y = y + HEIGHT;
		Color bcolor = Color.BLACK;
		Color wcolor = Color.WHITE;
		if (rollOvered) {
			bcolor = Color.LIGHT_GRAY;
			wcolor = Color.LIGHT_GRAY;
		}
		if (pressed) {
			bcolor = Color.GRAY;
			wcolor = Color.GRAY;
		}
		// C
		if (note.getKey() == 0 || note.getKey() == 5) {
			int[] xPoints = { x, x, x + blackWidth, x + blackWidth, x + WIDTH,
					x + WIDTH };
			int[] yPoints = { y, y - HEIGHT, y - HEIGHT,
					y - HEIGHT + blackHeight, y - HEIGHT + blackHeight, y };
			g.setColor(wcolor);
			shape = new Polygon(xPoints, yPoints, 6);
			g.fill(shape);
			g.setColor(bcolor);
			g.draw(shape);
			if (note.getKey() == 0) {
				drawlabel(x, y, g);
			}
		} else if (note.getKey() == 1 || note.getKey() == 6
				|| note.getKey() == 8) {
			int[] xPoints = { x + blackWidth, x + blackWidth,
					x + 2 * blackWidth, x + 2 * blackWidth };
			int[] yPoints = { y - HEIGHT + blackHeight, y - HEIGHT, y - HEIGHT,
					y - HEIGHT + blackHeight };
			g.setColor(bcolor);
			shape = new Polygon(xPoints, yPoints, 4);
			g.fill(shape);
		} else if (note.getKey() == 2 || note.getKey() == 9) {
			int x1 = x + littleDelta;
			int x2 = x + WIDTH - littleDelta;
			int[] xPoints = { x, x, x1, x1, x2, x2, x + WIDTH, x + WIDTH };
			int y1 = y - HEIGHT + blackHeight;
			int y2 = y - HEIGHT;
			int[] yPoints = { y, y1, y1, y2, y2, y1, y1, y };
			g.setColor(wcolor);
			shape = new Polygon(xPoints, yPoints, 8);
			g.fill(shape);
			g.setColor(bcolor);
			g.draw(shape);
		} else if (note.getKey() == 3 || note.getKey() == 10) {
			int[] xPoints = { x + WIDTH - littleDelta, x + WIDTH - littleDelta,
					x + WIDTH - littleDelta + blackWidth,
					x + WIDTH - littleDelta + blackWidth };
			int[] yPoints = { y - HEIGHT + blackHeight, y - HEIGHT, y - HEIGHT,
					y - HEIGHT + blackHeight };
			g.setColor(bcolor);
			shape = new Polygon(xPoints, yPoints, 4);
			g.fill(shape);
		} else if (note.getKey() == 4 || note.getKey() == 11) {
			int[] xPoints = { x, x, x + blackWidth - littleDelta,
					x + blackWidth - littleDelta, x + WIDTH, x + WIDTH };
			int[] yPoints = { y, y - HEIGHT + blackHeight,
					y - HEIGHT + blackHeight, y - HEIGHT, y - HEIGHT, y };
			g.setColor(wcolor);
			shape = new Polygon(xPoints, yPoints, 6);
			g.fill(shape);
			g.setColor(bcolor);
			g.draw(shape);
		} else if (note.getKey() == 7) {
			int x1 = x + littleDelta;
			int[] xPoints = { x, x, x1, x1, x + blackWidth, x + blackWidth,
					x + WIDTH, x + WIDTH };
			int y1 = y - HEIGHT + blackHeight;
			int y2 = y - HEIGHT;
			int[] yPoints = { y, y1, y1, y2, y2, y1, y1, y };
			g.setColor(wcolor);
			shape = new Polygon(xPoints, yPoints, 8);
			g.fill(shape);
			g.setColor(bcolor);
			g.draw(shape);
		}

		// Draw the probability circles.
		drawCircles(x, y - HEIGHT, g);
	}

	private void drawCircles(int x, int y, Graphics2D g) {
		drawCircle(colorLevel1, x, y, g, level > 0);
		drawCircle(colorLevel2, x, y - 10, g, level > 1);
		drawCircle(colorLevel3, x, y - 20, g, level > 2);
		drawCircle(colorLevel4, x, y - 30, g, level > 3);
		drawCircle(colorLevel5, x, y - 40, g, level > 4);
	}

	private void drawlabel(int x, int y, Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawString(note.toString(), x, y + 20);
	}

	public Note getNote() {
		return note;
	}

	public boolean isSharp() {
		return note.isSharp();
	}

	public boolean contains(Point point) {
		if (shape != null) {
			return shape.contains(point);
		} else {
			return false;
		}
	}

	public void setPressed(boolean b) {
		this.pressed = b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		return true;
	}

	private void drawCircle(Color color, int x, int y, Graphics2D g,
			boolean fill) {
		if (note.isSharp()) {

			y = y + HEIGHT;
			double centerx = x + 3 * blackWidth / 2;
			if (note.getKey() != 1 && note.getKey() != 6 && note.getKey() != 8) {
				centerx = x + WIDTH - littleDelta + blackWidth / 2;
			}

			double centery = y - 10 - HEIGHT + blackHeight;
			double radius = 3;
			Shape circle = new Ellipse2D.Double(centerx - radius, centery
					- radius, 2 * radius, 2 * radius);
			g.setColor(color);
			if (fill) {
				g.setColor(color);
				g.fill(circle);
			}
			g.setColor(Color.WHITE);
			g.draw(circle);
		} else {

			y = y + HEIGHT;
			double centerx = x + WIDTH / 2;
			double centery = y - 10;
			double radius = 3;
			Shape circle = new Ellipse2D.Double(centerx - radius, centery
					- radius, 2 * radius, 2 * radius);
			g.setColor(color);
			if (fill) {
				g.setColor(color);
				g.fill(circle);
			}
			g.setColor(Color.BLACK);
			g.draw(circle);

		}
	}

	public void setRollOvered(boolean rollOvered) {
		this.rollOvered = rollOvered;
	}

	public void levelUp() {
		if (level != 5) {
			level++;
			model.setNote(note, level);
		}
	}

	public void levelDown() {
		if (level != 0) {
			level--;
			model.setNote(note, level);
		}
	}
}
