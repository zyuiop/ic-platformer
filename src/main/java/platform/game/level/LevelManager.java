package platform.game.level;

import java.util.ArrayList;
import java.util.List;
import platform.game.level.castle.Castle1;
import platform.game.level.castle.Castle2;
import platform.game.level.castle.Castle3;

/**
 * @author zyuiop
 * This class manages the different levels of the game
 */
public class LevelManager {
	private List<LevelGroup> levelGroups = new ArrayList<>();
	private double playerLife;

	/**
	 * Register all available levels and level groups
	 * @return a new Level Manager filled with all the levels
	 */
	public static LevelManager init() {
		return new LevelManager().add(new LevelGroup()
				.addLevel(Castle1.class)
				.addLevel(Castle2.class)
				.addLevel(Castle3.class)
		);
	}

	private LevelManager() {
	}

	/**
	 * Add a level group at the end of the existing ones
	 * @param group the new group to add
	 * @return the current level manager, for chained calls
	 */
	private LevelManager add(LevelGroup group) {
		levelGroups.add(group);
		return this;
	}

	/**
	 * Get the group a level corresponds to
	 * @param level the level to check
	 * @return the group of this level or null
	 */
	private LevelGroup getLevelGroup(PlayableLevel level) {
		for (LevelGroup group : levelGroups)
			if (group.hasLevel(level))
				return group;
		return null;
	}

	/**
	 * Restart the whole level group, usually when the player dies or restarts the game
	 * @return the new level to start
	 */
	public PlayableLevel restartGroup(PlayableLevel currentLevel) {
		setPlayerLife(10D); // reset life
		LevelGroup currentGroup = currentLevel == null ? null : getLevelGroup(currentLevel);

		if (currentGroup == null) {
			currentGroup = levelGroups.get(0);
		}

		return currentGroup.getFirstLevel();
	}

	/**
	 * Get the level following the current level
	 * @param currentLevel the current played level
	 * @return the next level to play
	 */
	public PlayableLevel getNextLevel(PlayableLevel currentLevel) {
		if (levelGroups.size() < 1) {
			throw new IllegalStateException("The LevelManager is not initialized yet.");
		}

		LevelGroup currentGroup = getLevelGroup(currentLevel);
		if (currentGroup == null) {
			currentGroup = levelGroups.get(0);
		}

		PlayableLevel next = currentGroup.getNextLevel(currentLevel);
		if (next == null) {
			LevelGroup nextGroup = nextGroup(currentGroup);
			if (nextGroup == null)
				return currentGroup.getLastLevel(); // no more levels : play in loop
			else
				next = nextGroup.getFirstLevel();
		}

		return next;
	}

	private LevelGroup nextGroup(LevelGroup currentGroup) {
		int index = levelGroups.indexOf(currentGroup) + 1;
		if (index < levelGroups.size()) {
			// remaining groups
			return levelGroups.get(index);
		}

		// no remaining group !
		return null;
	}

	/**
	 * Get the current player life.
	 * Player life is kept between levels.
	 * @return the current player life
	 */
	public double getPlayerLife() {
		return playerLife;
	}

	/**
	 * Defines the current player life, to keep it to the next level
	 * @param playerLife the current player life
	 */
	public void setPlayerLife(double playerLife) {
		this.playerLife = playerLife;
	}

}
