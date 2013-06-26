package fr.pip.jmuse;

import java.util.Random;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import fr.pip.jmuse.model.Model;
import fr.pip.jmuse.model.NotePlayer;

public class RandomPlayer implements Runnable, NotePlayer {

	private Synthesizer synthesizer;
	private Instrument[] instruments;
	private MidiChannel[] midiChannels;
	private Random random = new Random();
	private int velocity = 120;
	private Model model;

	public RandomPlayer(Model model) {
		this.model = model;
	}

	public void open() {
		try {
			if (synthesizer == null) {
				if ((synthesizer = MidiSystem.getSynthesizer()) == null) {
					System.out.println("getSynthesizer() failed!");
					return;
				}
			}
			synthesizer.open();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		Soundbank sb = synthesizer.getDefaultSoundbank();
		if (sb != null) {
			instruments = synthesizer.getDefaultSoundbank().getInstruments();
			synthesizer.loadInstrument(instruments[0]);
		}
		midiChannels = synthesizer.getChannels();
		new Thread(this).start();

	}

	public void run() {
		while(true) {
			if(model.isEmpty()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				int note = model.getRandomNote(random);
				int sleep = model.getRandomRythmSleepTime(random);
				midiChannels[0].noteOn(note, velocity);
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				midiChannels[0].noteOff(note, velocity);
				
			}
		}

	}

	public void startPlayingNote(final int note, final int velocity) {
//		new Thread(new Runnable() {
//
//			public void run() {
//				midiChannels[0].noteOn(note, velocity);
//			}
//		}).start();

	}

	public void stopPlayingNote(final int note, final int velocity) {
//		new Thread(new Runnable() {
//
//			public void run() {
//				midiChannels[0].noteOff(note, velocity);
//			}
//		}).start();

	}

	public void playNote(final int note, final int velocity) {
//		new Thread(new Runnable() {
//
//			public void run() {
//				midiChannels[0].noteOn(note, velocity);
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				midiChannels[0].noteOff(note, velocity);
//			}
//		}).start();
	}
}
