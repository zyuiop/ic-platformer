package platform.util.sounds;

/**
 * A loaded sound ready to be played
 */
public interface Sound {
	default void play() {
		play(1D);
	}

	/**
	 * Play a sound at a given volume
	 * @param volume
	 */
	void play(double volume);
}
