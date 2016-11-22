package platform.util.sounds;

/**
 * A loaded sound ready to be played
 */
public interface Sound {
	default void play() {
		play(1D);
	}

	void play(double volume);
}
