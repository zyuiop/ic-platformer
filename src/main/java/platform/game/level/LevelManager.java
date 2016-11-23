package platform.game.level;

import java.util.ArrayList;
import java.util.List;
import platform.game.level.castle.Castle1;
import platform.game.level.castle.Castle2;

/**
 * @author zyuiop
 */
public class LevelManager {
	private List<LevelGroup> levelGroups = new ArrayList<>();
	private LevelGroup currentGroup;
	private double playerLife;

	public static LevelManager init() {
		return new LevelManager().add(new LevelGroup().addLevel(Castle1.class).addLevel(Castle2.class));
	}

	private LevelManager() {
	}

	private LevelManager add(LevelGroup group) {
		levelGroups.add(group);
		return this;
	}

	public PlayableLevel restartGroup() {
		setPlayerLife(10D); // reset life

		if (currentGroup == null) {
			currentGroup = levelGroups.get(0);
		}

		return currentGroup.getFirstLevel();
	}

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
			currentGroup = levelGroups.get(index);
			return true;
		}
		return false;
	}

	public double getPlayerLife() {
		return playerLife;
	}

	public void setPlayerLife(double playerLife) {
		this.playerLife = playerLife;
	}

}
