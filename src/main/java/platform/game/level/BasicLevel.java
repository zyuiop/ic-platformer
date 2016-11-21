package platform.game.level;

import platform.game.Signal;
import platform.game.actors.environment.AlwaysMovingPlatform;
import platform.game.actors.environment.Block;
import platform.game.actors.environment.Door;
import platform.game.actors.environment.Heart;
import platform.game.actors.environment.Jumper;
import platform.game.actors.environment.Key;
import platform.game.actors.environment.Lever;
import platform.game.actors.environment.Limits;
import platform.game.actors.animations.Overlay;
import platform.game.actors.entities.Player;
import platform.game.World;
import platform.game.actors.environment.Spikes;
import platform.game.actors.environment.Torch;
import platform.game.actors.basic.OrientedActor;
import platform.game.logic.And;
import platform.game.logic.Not;
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
		Key key = new Key("red", .3, new Vector(2.5, 3.5));
		world.register(key);
		world.register(new Spikes(new Vector(2.5, .749), 1, OrientedActor.Direction.UP, 2D));
		world.register(new Jumper(new Vector(2.5, -.749), 1));
		world.register(new Heart(new Vector(2.5, 2.5), 1));
		Lever torch = new Lever(new Vector(-.5, 1.25), .5, 30);
		world.register(torch);
		Player player = new Player(new Vector(0, 6), new Vector(0, -1));
		world.register(player);
		world.register(new Overlay(player));
		world.register(new Limits(new Box(Vector.ZERO, 40, 30)));

		world.register(new AlwaysMovingPlatform(new Box(new Vector(0, 4), 1, 1), "box.single", new Vector(0, 4), new Vector(0, 0), new Not(torch), .5, 1D));

		world.register(new Door(new Box(new Vector(-1.5, 1.5), 1, 1), "lock.red", new And(key, torch)));
	}

}
