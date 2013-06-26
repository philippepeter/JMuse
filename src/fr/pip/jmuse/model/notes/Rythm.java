package fr.pip.jmuse.model.notes;

/**
 * A musical note duration.
 * @author philippepeter
 *
 */
public enum Rythm {

	//TODO use english terms.
	NOIRE, CROCHE, DOUBLE_CROCHE;

	public static double getSleepTime(Rythm rythm, double bpm) {
		switch (rythm) {
		case NOIRE:
			return (1 /  (bpm / 60)) * 1000;

		case CROCHE:
			return (1 / ((bpm / 60)*2)) * 1000;
			
		case DOUBLE_CROCHE:
			return (1 / ((bpm / 60)*4)) * 1000;
			
		default:
			break;
		}
		return -1;
	}
	
}
