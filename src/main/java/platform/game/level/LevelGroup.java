package platform.game.level;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyuiop
 *         <p>
 *         A class representing a group of level. Just like in Mario Bros, when you die in a level group you
 *         have to restart the whole group from the beggining.
 */
public final class LevelGroup {
	private final List<Class<? extends PlayableLevel>> levels = new ArrayList<>();

	LevelGroup addLevel(Class<? extends PlayableLevel> level) {
		levels.add(level);
		return this;
	}

	/**
	 * Count the levels of this group
	 * @return the number of levels
	 */
	public int countLevels() {
		return levels.size();
	}

	/**
	 * Get the i-th level of this group
	 * @param i the position of the level to get, where the first one is at position 0
	 * @return a playable level, or null if there is no level with this position
	 */
	public PlayableLevel getLevel(int i) {
		if (levels.size() > i && i >= 0) {
			try {
				return levels.get(i).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Get the index of this level in this group of levels
	 * @param level the level to check
	 * @return the position, or -1 if this level is not in this group
	 */
	public int getLevelNumber(PlayableLevel level) {
		return levels.indexOf(level.getClass());
	}

	/**
	 * Get the level following the given level
	 * @param current the current level
	 * @return the following level, or null if the level was the last one of this group
	 */
	public PlayableLevel getNextLevel(PlayableLevel current) {
		int index = current == null ? -1 : getLevelNumber(current);
		return getLevel(index + 1);
	}

	/**
	 * Get the first level of this group
	 * @return a level, the first one of the group
	 */
	public PlayableLevel getFirstLevel() {
		return getLevel(0);
	}

	/**
	 * Get the last level of this group
	 * @return a level, the last one of the group
	 */
	public PlayableLevel getLastLevel() {
		return getLevel(countLevels() - 1);
	}
}
