package fr.pip.jmuse.model;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import fr.pip.jmuse.model.notes.Note;
import fr.pip.jmuse.model.notes.Rythm;

/**
 * A Model that contains notes and rythms, each with a probability.
 * @author philippepeter
 *
 */
public class Model {

	private ConcurrentHashMap<Note, Integer> notes = new ConcurrentHashMap<Note, Integer>();
	private ConcurrentHashMap<Rythm, Integer> rythms = new ConcurrentHashMap<Rythm, Integer>();
	private int tempoInBPM = 120;

	public void setRythm(Rythm rythm, int value) {
		rythms.put(rythm, value);
	}

	public void setNote(Note note, int value) {
		notes.put(note, value);
	}

	public boolean isEmpty() {
		return isEmptyNotes() || isEmptyRythm();
	}

	private boolean isEmptyRythm() {
		if(!rythms.isEmpty()) {
			for(Rythm rythm : rythms.keySet()) {
				if(rythms.get(rythm) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isEmptyNotes() {
		if(!notes.isEmpty()) {
			for(Note note : notes.keySet()) {
				if(notes.get(note) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	protected int getNoteValuesCount() {
		int count = 0;
		for (Note note : notes.keySet()) {
			if (notes.get(note) != 0) {
				count += notes.get(note);
			}
		}
		return count;
	}

	protected int getRythmValuesCount() {
		int count = 0;
		for (Rythm rythm : rythms.keySet()) {
			if (rythms.get(rythm) != 0) {
				count += rythms.get(rythm);
			}
		}
		return count;
	}

	public int getRandomNote(Random random) {
		int count = getNoteValuesCount();
		int randomValue = random.nextInt(count) + 1;
		return getRandomNoteValue(randomValue);

	}

	protected int getRandomNoteValue(int randomValue) {
		int count = 0;
		for (Note note : notes.keySet()) {
			if (notes.get(note) != 0) {
				if (randomValue <= count + notes.get(note)) {
					return note.getIndex();
				}
				count += notes.get(note);
			}
		}

		return -1;
	}

	public double getTempo() {
		return tempoInBPM;
	}

	public int getRandomRythmSleepTime(Random random) {
		int count = getRythmValuesCount();
		int randomValue = random.nextInt(count) + 1;
		return getRandomRythmValue(randomValue);

	}

	protected int getRandomRythmValue(int randomValue) {
		int count = 0;
		for (Rythm rythm : rythms.keySet()) {
			if (randomValue <= count + rythms.get(rythm)) {
				return (int) Rythm.getSleepTime(rythm, tempoInBPM);
			}
			count += rythms.get(rythm);

		}

		return -1;
	}

}
