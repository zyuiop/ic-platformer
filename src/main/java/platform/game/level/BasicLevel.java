package platform.game.level;

import platform.game.actors.environment.Block;
import platform.game.actors.environment.Heart;
import platform.game.actors.environment.Jumper;
import platform.game.actors.environment.Limits;
import platform.game.actors.animations.Overlay;
import platform.game.actors.entities.Player;
import platform.game.World;
import platform.game.actors.environment.Spikes;
import platform.game.actors.environment.Torch;
import platform.game.actors.basic.OrientedActor;
import platform.util.Box;
import platform.util.Vector;

public class BasicLevel extends Level {
	@Override
	public void register(World world) {
		super.register(world);

		// Register a new instance, to restart level automatically
		world.setNextLevel(new BasicLevel());

		// Create blocks
		world.register(new Block(new Box(new Vector(0, 0), 4, 2), "stone.broken.2"));
		world.register(new Block(new Box(new Vector(-1.5, 1.5), 1, 1), "stone.broken.1"));
		world.register(new Spikes(new Vector(2.5, .749), 1, OrientedActor.Direction.UP, 2D));
		world.register(new Jumper(new Vector(2.5, -.749), 1));
		world.register(new Heart(new Vector(2.5, 2.5), 1));
		world.register(new Torch(new Vector(-.5, 1.5)));
		Player player = new Player(new Vector(0, 6), new Vector(0, -1));
		world.register(player);
		world.register(new Overlay(player));
		world.register(new Limits(new Box(Vector.ZERO, 40, 30)));
	}

}
