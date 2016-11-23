package platform.game.level;

import platform.game.Signal;
import platform.game.World;
import platform.game.actors.Background;
import platform.game.actors.basic.OrientedActor;
import platform.game.actors.blocks.AlwaysMovingPlatform;
import platform.game.actors.blocks.Block;
import platform.game.actors.blocks.Jumper;
import platform.game.actors.blocks.ProjectileLauncher;
import platform.game.actors.blocks.Spikes;
import platform.game.actors.environment.Door;
import platform.game.actors.environment.Heart;
import platform.game.actors.environment.Key;
import platform.game.actors.environment.Lever;
import platform.game.actors.environment.Limits;
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
	public void register(World world) {
		super.register(world);

		// Register a new instance, to restart level automatically
		world.setNextLevel(new BasicLevel());

		world.register(new Background("background", true, false));

		// Create blocks
		world.register(new Block(new Box(new Vector(0, 0), 4, 2), "stone.broken.2"));
		Key key = new Key("red", .3, new Vector(2.5, 3.5));
		world.register(key);
		world.register(new Spikes(new Vector(2.5, .749), 1, OrientedActor.Direction.UP, 2D));
		world.register(new Jumper(new Vector(2.5, -.749), 1));
		world.register(new Heart(new Vector(2.5, 2.5), 1));
		Lever torch = new Lever(new Vector(-.5, 1.25), .5, 30);
		world.register(torch);
		world.register(new Limits(new Box(Vector.ZERO, 40, 30)));
		world.register(new ProjectileLauncher("box.double", 1, new Vector(2.5, 2.5), OrientedActor.Direction.LEFT, Signal.ENABLED, (sinceLastLaunch, oldSignal, newSignal) -> sinceLastLaunch >= 1.5, new ProjectileLauncher.FireballCreator().power(10)));

		world.register(new AlwaysMovingPlatform(new Box(new Vector(0, 4), 1, 1), "box.single", new Vector(0, 4), new Vector(0, 0), new Not(torch), .5, 1D));

		world.register(new Door(new Box(new Vector(-1.5, 1.5), 1, 1), "lock.red", new And(key, torch)));
	}

}
