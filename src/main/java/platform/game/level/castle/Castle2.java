package platform.game.level.castle;

import platform.game.Signal;
import platform.game.World;
import platform.game.actors.Background;
import platform.game.actors.blocks.AlwaysMovingPlatform;
import platform.game.actors.blocks.Block;
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
		world.register(new LaserDoor(new Vector(0, 3), 14, LaserDoor.Orientation.HORIZONTAL, "green", lever));

		// Elevatooooor

		// The used sprite has a strange ratio
		// To avoid having a hitbox that doesn't correspond to the reality we recreate it correctly
		// (owwww I really dislike declaring a class directly in a method)
		class CastlePlatform extends AlwaysMovingPlatform {
			private CastlePlatform(Box box, Vector first, Vector second) {
				super(box, "metalPlatform", first, second, elevators, .35, 1);
			}

			@Override
			public Box getBox() {
				Box box = super.getBox();
				if (box == null)
					return null; // no parent box, we don't return anything

				double ratio = 18D / 70; // ratio of the image (hardcoded)
				double newHeight = box.getWidth() * ratio;

				// the useful part of the image is the part at the top :
				// remove the useless transparent part in the bottom by increasing lowest position
				Vector min = box.getMin().add(new Vector(0, box.getHeight() - newHeight));
				return new Box(min, box.getMax());
			}

			@Override
			public Box getDisplayBox() {
				// display box is classic box
				return super.getBox();
			}
		}

		world.register(new CastlePlatform(new Box(new Vector(-5, .5), 1, 1), new Vector(-5, .5), new Vector(-2.5, 4.5)));
		world.register(new CastlePlatform(new Box(new Vector(5, .5), 1, 1), new Vector(5, .5), new Vector(2.5, 4.5)));

		world.register(new Background("background.hills", true, false));
	}
}
