package platform.game.level;

import platform.game.World;
import platform.game.actors.entities.Player;
import platform.game.settings.KeyBindings;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class PlayableLevel extends Level {
	protected abstract Vector startPosition();

	protected double startLife() {
		return 10D;
	}

	@Override
	public void register(World world) {
		super.register(world);

		Player player = new Player(startPosition(), new Vector(0, -1), startLife(), startLife(), KeyBindings.getInstance());
		world.register(player);
	}
}
