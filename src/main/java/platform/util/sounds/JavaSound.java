package platform.util.sounds;


import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author zyuiop
 */
public class JavaSound implements Sound {
	private final File file;

	public JavaSound(File file) {
		this.file = file;
	}

	@Override
	public void play(double volume) {
		volume = Math.log10(volume) * 20; // convert from linear to decibels
		if (volume > 6) { volume = 6; }
		if (volume < -80D) { volume = -80D; }

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue((float) volume);
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
}
