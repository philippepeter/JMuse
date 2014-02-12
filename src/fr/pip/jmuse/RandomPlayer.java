package fr.pip.jmuse;

import java.util.Random;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import fr.pip.jmuse.model.Model;

/**
 * Open a defaukt synthesiser and play random notes according to the {@link Model}
 * @author philippepeter
 *
 */
public class RandomPlayer implements Runnable {

    private Synthesizer synthesiser;
    private Instrument[] instruments;
    private MidiChannel[] midiChannels;
    private Random random = new Random();
	private int velocity = 120;
	private Model model;
    private boolean isRunning;

    public RandomPlayer(Model model) {
        this.model = model;
    }

    public void open() {
        // Open a default synthesiser
        try {
            if (synthesiser == null) {
                if ((synthesiser = MidiSystem.getSynthesizer()) == null) {
                    System.out.println("getSynthesizer() failed!");
                    return;
                }
			}
            synthesiser.open();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
		}

		// Load the first SoundBank
        Soundbank sb = synthesiser.getDefaultSoundbank();
        if (sb != null) {
            instruments = synthesiser.getDefaultSoundbank().getInstruments();
            synthesiser.loadInstrument(instruments[0]);
        }
        midiChannels = synthesiser.getChannels();
        new Thread(this).start();

    }

    public void run() {
        isRunning = true;
        while (isRunning) {
            // If model is empty stop playing.
            if (model.isEmpty()) {
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
