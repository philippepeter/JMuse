package fr.pip.jmuse.model.notes;

/**
 * A musical note.
 * @author philippepeter
 *
 */
public class Note {

	private final static String WHITE_SPACE = " ";
	private int index;

	private String toString = WHITE_SPACE;

	public Note(int index) {
		this.index = index;
		update();
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
		update();
	}
	
	private void update() {
		int key = index % 12;
		toString = AllNotes.KEYS.get(key) + WHITE_SPACE + (index / 12); 
	}
	
	public int getKey() {
		return index % 12;
	}
	
	public String toString() {
		return toString;
	}
	
	public boolean isSharp() {
		int key = index %12;
		if(key==1 || key==3 || key==6 || key==8 || key==10) {
			return true;
		}
		return false;
	}
	
	public int getScoreInterval() {
		int scoreInterval = -1;
		int key = index %12;
		for(int i=0;i<=key;i++) {
			if(!AllNotes.NOTES.get(i).isSharp()) {
				scoreInterval++;
			}
		}
		return scoreInterval;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		Note other = (Note) obj;
		if (index != other.index)
			return false;
		return true;
	}
	
}
