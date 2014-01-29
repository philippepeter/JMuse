package fr.pip.jmuse;

import java.util.Random;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import fr.pip.jmuse.model.Model;

/**
 * Open a defaukt synthetizer and play random notes according to the {@link Model}
 * @author philippepeter
 *
 */
public class RandomPlayer implements Runnable {

	private Synthesizer synthetizer;
	private Instrument[] instruments;
	private MidiChannel[] midiChannels;
	private Random random = new Random();
	private int velocity = 120;
	private Model model;

	public RandomPlayer(Model model) {
		this.model = model;
	}

	public void open() {
		// Open a default synthetizer
		try {
			if (synthetizer == null) {
				if ((synthetizer = MidiSystem.getSynthesizer()) == null) {
					System.out.println("getSynthesizer() failed!");
					return;
				}
			}
			synthetizer.open();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		// Load the first SoundBank
		Soundbank sb = synthetizer.getDefaultSoundbank();
		if (sb != null) {
			instruments = synthetizer.getDefaultSoundbank().getInstruments();
			synthetizer.loadInstrument(instruments[0]);
		}
		midiChannels = synthetizer.getChannels();
		new Thread(this).start();

	}

	public void run() {
		while(true) {
			// If model is empty stop playing.
			if(model.isEmpty()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				// Play the note.
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

}
