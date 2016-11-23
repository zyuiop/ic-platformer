package platform.game.level;

import platform.game.World;
import platform.game.actors.entities.Player;
import platform.game.KeyBindings;
import platform.game.actors.environment.Limits;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class PlayableLevel extends Level {
	protected abstract Vector startPosition();

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
