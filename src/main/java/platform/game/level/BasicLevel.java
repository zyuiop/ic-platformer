package platform.game.level;

import platform.game.Block;
import platform.game.Jumper;
import platform.game.Player;
import platform.game.World;
import platform.util.Box;
import platform.util.Vector;

public class BasicLevel extends Level {
	@Override
	public void register(World world) {
		super.register(world);

		// Register a new instance, to restart level automatically
		world.setNextLevel(new BasicLevel());

		// Create blocks
		world.register(new Block(new Box(new Vector(0, 0), 4, 2), world.getLoader().getSprite("stone.broken.2")));
		world.register(new Block(new Box(new Vector(-1.5, 1.5), 1, 1), world.getLoader().getSprite("stone.broken.1")));
		world.register(new Jumper(new Vector(1, 1), 1));
		world.register(new Player(new Vector(0, 6), new Vector(0, -1)));
	}

}
