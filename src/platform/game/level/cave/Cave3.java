package platform.game.level.cave;

import platform.game.Direction;
import platform.game.RepeatBehaviour;
import platform.game.Signal;
import platform.game.World;
import platform.game.actors.blocks.*;
import platform.game.actors.blocks.ProjectileLauncher.ArrowCreator;
import platform.game.actors.blocks.ProjectileLauncher.FireballCreator;
import platform.game.actors.environment.Background;
import platform.game.level.EndGameLevel;
import platform.game.level.PlayableLevel;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Cave3 extends PlayableLevel {
	@Override
	protected Vector startPosition() {
		return new Vector(0, 1);
	}

	@Override
	protected Box getLimits() {
		return new Box(new Vector(-20, -0), new Vector(20, 100));
	}

	@Override
	public void register(World world) {
		super.register(world);

		final double power = 14;

		world.register(new Block(new Box(new Vector(-3, -1), new Vector(3, 0)), "stone.2",
				new RepeatBehaviour(3, 1, true, false)));

		world.register(new Jumper(new Vector(-2.5, 0.25), 1, .5, Direction.UP, power));
		world.register(new Jumper(new Vector(2.5, 0.25), 1, .5, Direction.UP, power));

		world.register(new Block(new Box(new Vector(-2, 5), new Vector(2, 6)), "stone.2",
				new RepeatBehaviour(2, 1, true, false)));

		world.register(new Block(new Box(new Vector(3, 13), new Vector(4, 14)), "stone.1",
				new RepeatBehaviour(1, 1, true, false)));


		world.register(new Spikes(new Vector(-1.25, 6.25), .5, Direction.UP, 1));
		world.register(new Spikes(new Vector(-1.75, 6.25), .5, Direction.UP, 1));
		world.register(new Spikes(new Vector(-.75, 6.25), .5, Direction.UP, 1));
		world.register(new Jumper(new Vector(-.25, 6.125), .5, .25, Direction.UP, power));
		world.register(new Jumper(new Vector(.25, 6.125), .5, .25, Direction.UP, power));
		world.register(new Spikes(new Vector(.75, 6.25), .5, Direction.UP, 1));
		world.register(new Spikes(new Vector(1.25, 6.25), .5, Direction.UP, 1));
		world.register(new Spikes(new Vector(1.75, 6.25), .5, Direction.UP, 1));

		world.register(new Jumper(new Vector(2.875, 15.75), .5, .25, Direction.LEFT, power));
		world.register(new Jumper(new Vector(2.875, 15.25), .5, .25, Direction.LEFT, power));
		world.register(new Jumper(new Vector(2.875, 14.75), .5, .25, Direction.LEFT, power));
		world.register(new Jumper(new Vector(2.875, 14.25), .5, .25, Direction.LEFT, power));
		world.register(new Jumper(new Vector(2.875, 13.75), .5, .25, Direction.LEFT, power));
		world.register(new Jumper(new Vector(2.875, 13.25), .5, .25, Direction.LEFT, power));

		world.register(new Spikes(new Vector(-2.875, 16), .5, Direction.RIGHT, 1));
		world.register(new Spikes(new Vector(-2.875, 15.5), .5, Direction.RIGHT, 1));
		world.register(new Spikes(new Vector(-2.875, 15), .5, Direction.RIGHT, 1));
		world.register(new Spikes(new Vector(-2.875, 14.5), .5, Direction.RIGHT, 1));
		world.register(new Spikes(new Vector(-2.875, 14), .5, Direction.RIGHT, 1));
		world.register(new Spikes(new Vector(-2.875, 13.5), .5, Direction.RIGHT, 1));
		world.register(new Spikes(new Vector(-2.875, 13), .5, Direction.RIGHT, 1));

		// Contours
		Block leftStone = new Block(new Box(new Vector(-4, -1), new Vector(-3, 17)), "stone.7");
		leftStone.setRepeatBehaviour(new RepeatBehaviour(1, 3, false, true));
		world.register(leftStone);

		Block rightStone = new Block(new Box(new Vector(3, -1), new Vector(4, 11)), "stone.7");
		rightStone.setRepeatBehaviour(new RepeatBehaviour(1, 3, false, true));
		world.register(rightStone);

		rightStone = new Block(new Box(new Vector(3, 14), new Vector(4, 17)), "stone.7");
		rightStone.setRepeatBehaviour(new RepeatBehaviour(1, 3, false, true));
		world.register(rightStone);


		// Plafond
		world.register(new Block(new Box(new Vector(-3, 16), new Vector(3, 17)), "stone.2",
				new RepeatBehaviour(3, 1, true, false)));

		// Sol couloir
		world.register(new Block(new Box(new Vector(2, 11), new Vector(17, 12)), "stone.3",
				new RepeatBehaviour(4, 1, true, false)));

		// Plafond couloir
		world.register(new Block(new Box(new Vector(4, 14), new Vector(7, 15)), "stone.2"));
		world.register(new ProjectileLauncher(new Vector(7.5, 14.5), 1D, "box.warning.dark", Direction.DOWN, Signal.ENABLED, (time, u1, u2) -> time >= 1.5, new ArrowCreator().power(10)));
		world.register(new Block(new Box(new Vector(8, 14), new Vector(11, 15)), "stone.2"));
		world.register(new ProjectileLauncher(new Vector(11.5, 14.5), 1D, "box.warning.dark", Direction.DOWN, Signal.ENABLED, (time, u1, u2) -> time >= 1.5, new ArrowCreator().power(10)));
		world.register(new Block(new Box(new Vector(12, 14), new Vector(15, 15)), "stone.2"));
		world.register(new ProjectileLauncher(new Vector(15.5, 14.5), 1D, "box.warning.dark", Direction.DOWN, Signal.ENABLED, (time, u1, u2) -> time >= 1.5, new ArrowCreator().power(10)));
		world.register(new Block(new Box(new Vector(16, 14), new Vector(19, 15)), "stone.2"));

		// Limite droite
		rightStone = new Block(new Box(new Vector(19, 9), new Vector(20, 15)), "stone.7");
		rightStone.setRepeatBehaviour(new RepeatBehaviour(1, 3, false, true));
		world.register(rightStone);

		// Sol couloir bis
		world.register(new Block(new Box(new Vector(5, 7), new Vector(20, 8)), "stone.3",
				new RepeatBehaviour(3, 1, true, false)));

		world.register(new ProjectileLauncher(new Vector(19.5, 8.5), 1D, "box.warning.dark", Direction.LEFT, Signal.ENABLED, (time, u1, u2) -> time >= 1.5, new FireballCreator().power(15)));

		// Sol dernier couloir

		world.register(new Block(new Box(new Vector(4, 2), new Vector(19, 3)), "stone.3",
				new RepeatBehaviour(3, 1, true, false)));

		rightStone = new Block(new Box(new Vector(19, 2), new Vector(20, 8)), "stone.7");
		rightStone.setRepeatBehaviour(new RepeatBehaviour(1, 3, false, true));
		world.register(rightStone);

		world.register(new Exit(new Vector(17, 3.5), new EndGameLevel()));
		world.register(new Spikes(new Vector(4.25, 3.25), .5, Direction.UP, 1));
		world.register(new Spikes(new Vector(4.75, 3.25), .5, Direction.UP, 1));
		world.register(new Spikes(new Vector(5.25, 3.25), .5, Direction.UP, 1));



		world.register(new Background("background.cave", true, false));
		world.setViewRadius(6D);
	}
}
