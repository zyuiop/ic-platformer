package platform.game.level;

import java.util.ArrayList;
import java.util.List;
import platform.game.level.castle.Castle1;
import platform.game.level.castle.Castle2;

/**
 * @author zyuiop
 * This class manages the different levels of the game
 */
public class LevelManager {
	private List<LevelGroup> levelGroups = new ArrayList<>();
	private LevelGroup currentGroup;
	private double playerLife;

	/**
	 * Register all available levels and level groups
	 * @return a new Level Manager filled with all the levels
	 */
	public static LevelManager init() {
		return new LevelManager().add(new LevelGroup().addLevel(Castle1.class).addLevel(Castle2.class));
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
	 * Restart the whole level group, usually when the player dies or restarts the game
	 * @return the new level to start
	 */
	public PlayableLevel restartGroup() {
		setPlayerLife(10D); // reset life

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

		if (currentGroup == null) {
			currentGroup = levelGroups.get(0);
		}

		PlayableLevel next = currentGroup.getNextLevel(currentLevel);
		if (next == null)
			if (!nextGroup())
				return currentGroup.getLastLevel(); // no more levels : play in loop
			else
				next = currentGroup.getFirstLevel();

		return next;
	}

	private boolean nextGroup() {
		int index = levelGroups.indexOf(currentGroup) + 1;
		if (index < levelGroups.size()) {
			// remaining groups
			currentGroup = levelGroups.get(index);
			return true;
		}

		// no remaining group !
		return false;
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
