package platform.game.level;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyuiop
 */
public final class LevelGroup {
	private final List<Class<? extends PlayableLevel>> levels = new ArrayList<>();

	LevelGroup addLevel(Class<? extends PlayableLevel> level) {
		levels.add(level);
		return this;
	}

	public int countLevels() {
		return levels.size();
	}

	public PlayableLevel getLevel(int i) {
		if (levels.size() > i) {
			try {
				return levels.get(i).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int getLevelNumber(PlayableLevel level) {
		return levels.indexOf(level.getClass());
	}

	public PlayableLevel getNextLevel(PlayableLevel current) {
		int index = current == null ? -1 : getLevelNumber(current);
		return getLevel(index + 1);
	}

	public PlayableLevel getNextLevel(int current) {
		return getLevel(current + 1);
	}

	public PlayableLevel getFirstLevel() {
		return getLevel(0);
	}

	public PlayableLevel getLastLevel() {
		return getLevel(countLevels() - 1);
	}
}
