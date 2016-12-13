package platform.game.level.cave;

import platform.game.Orientation;
import platform.game.RepeatBehaviour;
import platform.game.Signal;
import platform.game.World;
import platform.game.actors.blocks.Block;
import platform.game.actors.blocks.Door;
import platform.game.actors.blocks.Exit;
import platform.game.actors.blocks.LaserDoor;
import platform.game.actors.environment.Background;
import platform.game.actors.environment.Key;
import platform.game.actors.environment.Lever;
import platform.game.level.PlayableLevel;
import platform.game.logic.Not;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Cave2 extends PlayableLevel {
	@Override
	protected Vector startPosition() {
		return new Vector(1, 1);
	}

	@Override
	protected Box getLimits() {
		return new Box(new Vector(-20, -0), new Vector(20, 100));
	}

	@Override
	public void register(World world) {
		super.register(world);

		world.register(new Block(new Box(new Vector(0, -1), new Vector(16, 0)), "stone.2",
				new RepeatBehaviour(2, 1, true, false)));

		world.register(new Block(new Box(new Vector(4, 1), new Vector(16, 2)), "stone.2",
				new RepeatBehaviour(2, 1, true, false)));

		world.register(new Block(new Box(new Vector(0, 3), new Vector(16, 4)), "stone.2",
				new RepeatBehaviour(2, 1, true, false)));


		Lever topLever = new Lever(new Vector(5.25, 2.25), .5, Double.POSITIVE_INFINITY, false, "lever.yellow.off", "lever.yellow.on", null);
		Lever topLeverB = new Lever(new Vector(10.25, 2.25), .5, Double.POSITIVE_INFINITY, false, "lever.blue.off", "lever.blue.on", null);
		Lever bottomLever = new Lever(new Vector(11.25, 0.25), .5, Double.POSITIVE_INFINITY, false, "lever.green.off", "lever.green.on", null);
		Lever bottomLeverB = new Lever(new Vector(10.25, 0.25), .5, Double.POSITIVE_INFINITY, false, "lever.red.off", "lever.red.on", null);
		world.register(topLever);
		world.register(topLeverB);
		world.register(bottomLever);
		world.register(bottomLeverB);

		Key redKey = new Key("red", .5, new Vector(8, 2.5));
		Key blueKey = new Key("blue", .5, new Vector(14, 2.5));
		world.register(redKey);
		world.register(blueKey);

		world.register(new LaserDoor(new Vector(6.5, 2.5), 1, Orientation.VERICAL, "yellow", topLever));
		world.register(new LaserDoor(new Vector(12.5, 2.5), 1, Orientation.VERICAL, "green", bottomLever));
		world.register(new LaserDoor(new Vector(14, 0.5), 1, Orientation.VERICAL, "yellow", topLever.not().and(topLeverB).and(bottomLeverB.not())));
		world.register(new LaserDoor(new Vector(6.5, 0.5), 1, Orientation.VERICAL, "red", topLever.not().or(bottomLeverB).and(bottomLever.and(topLeverB).not())));

		world.register(new Door(new Box(new Vector(8.5, 0.5), 1, 1), "lock.red", redKey));
		world.register(new Door(new Box(new Vector(13, 0.5), 1, 1), "lock.blue", blueKey));

		world.register(new Exit(new Vector(15, 0.5)));


		// Contours
		Block leftStone = new Block(new Box(new Vector(-1, -1), new Vector(-0, 4)), "stone.7");
		leftStone.setRepeatBehaviour(new RepeatBehaviour(1, 2.5, false, true));
		world.register(leftStone);

		Block rightStone = new Block(new Box(new Vector(16, -1), new Vector(17, 4)), "stone.7");
		rightStone.setRepeatBehaviour(new RepeatBehaviour(1, 2.5, false, true));
		world.register(rightStone);


		world.register(new Background("background.cave", true, false));
		world.setViewRadius(6D);
		world.setView(new Vector(8, 2));
		world.setFixedView(true);
	}
}
