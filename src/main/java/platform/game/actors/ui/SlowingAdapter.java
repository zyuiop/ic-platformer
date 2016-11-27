package platform.game.actors.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import platform.util.sounds.Sound;

/**
 * @author zyuiop
 *
 * An adapter for {@link TextBox} that shows the text letter by letter at a given speed.
 */
public class SlowingAdapter implements BiFunction<String[], Double, String[]> {
	private final double secondsPerCharacter;

	public SlowingAdapter(double secondsPerCharacter) {
		this.secondsPerCharacter = secondsPerCharacter;
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

		return stringList.toArray(new String[stringList.size()]);
	}
}
