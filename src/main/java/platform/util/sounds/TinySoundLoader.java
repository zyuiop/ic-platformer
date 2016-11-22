package platform.util.sounds;

import java.io.File;
import kuusisto.tinysound.TinySound;

/**
 * Loads sprites from specified folder, guessing necessary file extensions.
 */
public class TinySoundLoader implements SoundLoader {

	private static final String[] EXTENSIONS = {
			".ogg", ".wav"
	};

	private String prefix;
	private SoundLoader fallback;

	/**
	 * Creates a new file loader, with a default always-null fallback.
	 *
	 * @param prefix path prefix used to form absolute or relative path when concatenated with an identifier, not null
	 */
	public TinySoundLoader(String prefix) {this(prefix, SoundLoader.DUMMY_LOADER);}

	/**
	 * Creates a new file loader.
	 *
	 * @param prefix path prefix used to form absolute or relative path when concatenated with an identifier, not null
	 * @param fallback loader used in case of error, not null
	 */
	public TinySoundLoader(String prefix, SoundLoader fallback) {
		if (prefix == null || fallback == null) { throw new NullPointerException(); }
		this.prefix = prefix;
		this.fallback = fallback;
		TinySound.init();
	}

	@Override
	public Sound getSound(String name) {
		Sound sound = null;

		// Try each extension, until we are able to successfully read the file
		for (String extension : EXTENSIONS) {
			kuusisto.tinysound.Sound s = TinySound.loadSound(new File(prefix + name + extension));
			if (s != null) {
				sound = new TinySoundSound(s);
				break;
			}
		}

		// On failure, use fallback loader
		if (sound == null) { sound = fallback.getSound(name); }
		return sound;
	}

}
