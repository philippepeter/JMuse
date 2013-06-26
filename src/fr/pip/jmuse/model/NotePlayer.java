package fr.pip.jmuse.model;

public interface NotePlayer {

	public void startPlayingNote(int note, int velocity);
	public void stopPlayingNote(int note, int velocity);
	public void playNote(int note, int velocity);
}
