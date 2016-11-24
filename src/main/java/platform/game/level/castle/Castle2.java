package platform.game.level.castle;

import java.util.function.Function;
import platform.game.Signal;
import platform.game.World;
import platform.game.actors.Background;
import platform.game.actors.Direction;
import platform.game.actors.Orientation;
import platform.game.actors.blocks.AlwaysMovingPlatform;
import platform.game.actors.blocks.Block;
import platform.game.actors.blocks.Jumper;
import platform.game.actors.blocks.Spikes;
import platform.game.actors.environment.Decoration;
import platform.game.actors.environment.Exit;
import platform.game.actors.environment.LaserDoor;
import platform.game.actors.environment.Lever;
import platform.game.level.PlayableLevel;
import platform.game.logic.And;
import platform.game.logic.Not;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Castle2 extends PlayableLevel {
	@Override
	protected Vector startPosition() {
		return new Vector(0, 2);
	}

	@Override
	protected Box getLimits() {
		return new Box(new Vector(-20, -2), new Vector(20, 20));
	}

	@Override
	public void register(World world) {
		super.register(world);

		world.setNextLevel(world.getLevelManager().getNextLevel(this));

		for (double x = -2.5; x <= 2.5; ++x) {
			for (double y = -15.5; y <= -0.5; ++y) {
				world.register(new Block(new Vector(x, y), 1, "castle.center"));
			}

			world.register(new Block(new Vector(x, .5), 1, "castle.middle"));
		}

		world.register(new Block(new Vector(-3.5, .5), 1, "castle.left"));
		world.register(new Block(new Vector(3.5, .5), 1, "castle.right"));

		// exit platform
		world.register(new Block(new Vector(-1, 4.5), 1, "castle.left"));
		world.register(new Block(new Vector(0, 4.5), 1, "castle.middle"));
		world.register(new Block(new Vector(1, 4.5), 1, "castle.right"));
		world.register(new Exit(new Vector(0, 5.5)));
		world.register(new Decoration(new Vector(-0.8, 5.2), .5, "exit", Math.PI / 8));

		// laser and stuff
		Lever lever = new Lever(new Vector(-1.5, 1.25), .5, 30D, false, "lever.green.off", "lever.green.on", null);
		Lever lever2 = new Lever(new Vector(1.5, 1.25), .5, 30D, true);
		world.register(lever);
		world.register(lever2);
		Signal elevators = new And(lever, new Not(lever2));
		world.register(new LaserDoor(new Vector(0, 3), 14, Orientation.HORIZONTAL, "green", lever));

		world.register(new Spikes(new Vector(.5 , 1.25), .5, Direction.UP, 1D));
		world.register(new Spikes(new Vector(-1.5 , 1.25), .5, Direction.RIGHT, 1D));

		// Elevatooooor


		double spriteRatio = 18D / 70D; // the ratio of the sprite
		Function<Box, Box> transformer = (box) -> {
			double newHeight = box.getWidth(); // 1/1 ratio

			// we add a useless zone in the bottom
			Vector min = box.getMin().add(new Vector(0, box.getHeight() - newHeight));
			return new Box(min, box.getMax());
		};

		double startY = 1 - spriteRatio / 2D; // center y position
		double endY = 5 - spriteRatio / 2D;

		world.register(new AlwaysMovingPlatform(new Box(new Vector(-5, startY), 1, spriteRatio),
				"metalPlatform", new Vector(-5, startY), new Vector(-2.5, endY), elevators, .35, 1D)
				.boxTransformer(transformer));
		world.register(new AlwaysMovingPlatform(new Box(new Vector(5, startY), 1, spriteRatio),
				"metalPlatform", new Vector(5, startY), new Vector(2.5, endY), elevators, .35, 1D)
				.boxTransformer(transformer));

		world.register(new Background("background.hills", true, false));
	}
}
