package platform.game.level;

import platform.game.Signal;
import platform.game.World;
import platform.game.actors.Background;
import platform.game.actors.Direction;
import platform.game.actors.blocks.AlwaysMovingPlatform;
import platform.game.actors.blocks.Block;
import platform.game.actors.blocks.Jumper;
import platform.game.actors.blocks.ProjectileLauncher;
import platform.game.actors.blocks.Spikes;
import platform.game.actors.environment.Door;
import platform.game.actors.environment.Heart;
import platform.game.actors.environment.Key;
import platform.game.actors.environment.Lever;
import platform.game.logic.And;
import platform.game.logic.Not;
import platform.util.Box;
import platform.util.Vector;

public class BasicLevel extends PlayableLevel {
	@Override
	protected Vector startPosition() {
		return new Vector(0, 6);
	}

	@Override
	protected Box getLimits() {
		return new Box(Vector.ZERO, 40, 30);
	}

	@Override
	public void register(World world) {
		super.register(world);

		// Register a new instance, to restart level automatically
		world.setNextLevel(world.getLevelManager().getNextLevel(this));

		world.register(new Background("background.hills", true, false));

		// Create blocks
		world.register(new Block(new Box(new Vector(0, 0), 4, 2), "stone.broken.2"));
		Key key = new Key("red", .3, new Vector(2.5, 3.5));
		world.register(key);
		world.register(new Spikes(new Vector(2.5, .749), 1, Direction.UP, 2D));
		// world.register(new Jumper(new Vector(2.5, -.749), 1));
		world.register(new Heart(new Vector(2.5, 2.5), 1));
		Lever torch = new Lever(new Vector(-.5, 1.25), .5, 30);
		world.register(torch);
		world.register(new ProjectileLauncher(new Vector(2.5, 2.5), 1, "box.double", Direction.LEFT, Signal.ENABLED, (sinceLastLaunch, oldSignal, newSignal) -> sinceLastLaunch >= 1.5, new ProjectileLauncher.FireballCreator().power(10)));

		world.register(new AlwaysMovingPlatform(new Box(new Vector(0, 4), 1, 1), "box.single", new Vector(0, 4), new Vector(0, 0), new Not(torch), .5, 1D));

		Lever lever = new Lever(new Vector(0, 3), .5, Double.POSITIVE_INFINITY, false, "lever.red.off", "lever.red.on", null);
		world.register(lever);
		// world.register(new LaserDoor(new Vector(3, 3), 5, Math.PI / 4, "red", lever));

		world.register(new Door(new Box(new Vector(-1.5, 1.5), 1, 1), "lock.red", new And(key, torch)));
	}

}
