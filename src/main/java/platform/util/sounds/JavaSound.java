package platform.util.sounds;


import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

/**
 * @author zyuiop
 */
public class JavaSound implements Sound {
	private final AudioInputStream stream;

	public JavaSound(AudioInputStream stream) {
		this.stream = stream;
	}

	@Override
	public void play(double volume) {
		volume = Math.log10(volume) * 20; // convert from linear to decibels
		if (volume > 6)
			volume = 6;
		if (volume < -80D)
			volume = -80D;

		try {
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue((float) volume);
			clip.start();
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}
}
