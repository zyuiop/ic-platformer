package platform.util.sounds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
	}

	@Override
	public Sound getSound(String name) {
		Sound sound = null;

		File f = new File(prefix + name + ".wav");
		if (f.exists()) {
			sound = new JavaSound(f);
		}

		// On failure, use fallback loader
		if (sound == null) { sound = fallback.getSound(name); }
		return sound;
	}

}
