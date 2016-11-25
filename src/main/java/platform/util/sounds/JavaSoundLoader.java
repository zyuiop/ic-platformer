package platform.util.sounds;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import kuusisto.tinysound.TinySound;

/**
 * Loads sprites from specified folder, guessing necessary file extensions.
 */
public class JavaSoundLoader implements SoundLoader {
	private String prefix;
	private SoundLoader fallback;

	/**
	 * Creates a new file loader, with a default always-null fallback.
	 *
	 * @param prefix path prefix used to form absolute or relative path when concatenated with an identifier, not null
	 */
	public JavaSoundLoader(String prefix) {this(prefix, SoundLoader.DUMMY_LOADER);}

	/**
	 * Creates a new file loader.
	 *
	 * @param prefix path prefix used to form absolute or relative path when concatenated with an identifier, not null
	 * @param fallback loader used in case of error, not null
	 */
	public JavaSoundLoader(String prefix, SoundLoader fallback) {
		if (prefix == null || fallback == null) { throw new NullPointerException(); }
		this.prefix = prefix;
		this.fallback = fallback;
		TinySound.init();
	}

	@Override
	public Sound getSound(String name) {
		Sound sound = null;

		// Try each extension, until we are able to successfully read the file
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(prefix + name + ".wav"));
			if (stream != null)
				sound = new JavaSound(stream);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}

		// On failure, use fallback loader
		if (sound == null) { sound = fallback.getSound(name); }
		return sound;
	}

}
