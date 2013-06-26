package fr.pip.jmuse.model.notes;

import java.util.Vector;

/**
 * Utility class that contains all notes.
 * @author philippepeter
 *
 */
public final class AllNotes {
	public static Vector<String> KEYS = new Vector<String>();
	static{
		KEYS.add("C");
		KEYS.add("C#");
		KEYS.add("D");
		KEYS.add("D#");
		KEYS.add("E");
		KEYS.add("F");
		KEYS.add("F#");
		KEYS.add("G");
		KEYS.add("G#");
		KEYS.add("A");
		KEYS.add("A#");
		KEYS.add("B");
	}
	public final static Vector<Note> NOTES = new Vector<Note>();
	

	static {
		for (int i = 0; i <= 10; i++) {
			for (int j = 0; j < KEYS.size(); j++) {
				if (i != 10 || j < KEYS.size() - 4) {
					Note note = new Note(KEYS.size() * i + j);
					NOTES.add(note);
				}
			}
		}
	}
	
	/**
	 * Get the index on a keyboard of the given note according to a String of type [NoteName] [Octave index]
	 * @param note
	 * @return
	 */
	public static int getIndexOf(String note) {
		String[] split = note.split(" ");
		String key = split[0];
		String octave = split[1];
		return Integer.valueOf(octave) * 12 + Integer.valueOf(KEYS.indexOf(key));
		
	}
}
