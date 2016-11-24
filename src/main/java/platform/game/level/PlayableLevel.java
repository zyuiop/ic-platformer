package platform.game.level;

import platform.game.World;
import platform.game.actors.entities.Player;
import platform.game.KeyBindings;
import platform.game.actors.environment.Limits;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 * A level that can be played by an human character.
 */
public abstract class PlayableLevel extends Level {
	/**
	 * Get the start position for this level. The player will spawn here when the level will start.
	 * @return the start position of this level
	 */
	protected abstract Vector startPosition();

	/**
	 * Get the limits of this level. If the player goes out of these limits, he will die immediately.
	 * @return the bounding box of this level.
	 */
	protected abstract Box getLimits();

	@Override
	public void register(World world) {
		super.register(world);

		LevelManager manager = world.getLevelManager();

		Player player = new Player(startPosition(), new Vector(0, -1), 10D, manager.getPlayerLife(), KeyBindings.getInstance());
		world.register(player);

		world.register(new Limits(getLimits()));
	}
}
