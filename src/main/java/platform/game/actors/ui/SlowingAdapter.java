package platform.game.actors.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import platform.util.sounds.JavaSound;
import platform.util.sounds.Sound;

/**
 * @author zyuiop
 */
public class SlowingAdapter implements BiFunction<String[], Double, String[]> {
	private final double secondsPerCharacter;
	private int prevShown = 0;
	private Sound sound;

	public SlowingAdapter(double secondsPerCharacter) {this(secondsPerCharacter, null);}

	public SlowingAdapter(double secondsPerCharacter, Sound typingSound) {
		this.secondsPerCharacter = secondsPerCharacter;
		this.sound = typingSound;
	}

	@Override
	public String[] apply(String[] strings, Double seconds) {
		int charactersToShow = (int) (seconds / secondsPerCharacter);
		List<String> stringList = new ArrayList<>();

		int readChars = 0;
		for (String line : strings) {
			int remaining = charactersToShow - readChars;
			if (remaining >= line.length()) {
				readChars += line.length();
				stringList.add(line);
			} else if (remaining <= 0) {
				break;
			} else {
				readChars += remaining;
				stringList.add(line.substring(0, remaining));
				break;
			}
		}

		if (readChars > prevShown) {
			if (sound != null)
				sound.play();

			prevShown = charactersToShow;
		}

		return stringList.toArray(new String[stringList.size()]);
	}
}
