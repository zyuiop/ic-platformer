package platform.game.level;

import platform.game.Actor;
import platform.game.World;
import platform.game.actors.animations.Overlay;
import platform.game.actors.basic.OrientedActor;
import platform.game.actors.entities.Player;
import platform.game.actors.environment.*;
import platform.game.logic.And;
import platform.game.logic.Not;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;
import java.io.File;
import java.io.IOException;

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
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/kenpixel.ttf"));
			font = font.deriveFont(Font.BOLD, 20);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		Font finalFont = font;
		world.register(new Actor() {
			@Override
			public int getPriority() {
				return 1000000;
			}

			@Override
			public void draw(Input input, Output output) {
				output.drawText("cc c moua mdr", new Vector(0, 0), finalFont, Color.RED);
			}
		});

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

		world.register(new AlwaysMovingPlatform(new Box(new Vector(0, 4), 1, 1), "box.single", new Vector(0, 4), new Vector(0, 0), new Not(torch), .5, 1D));

		world.register(new Door(new Box(new Vector(-1.5, 1.5), 1, 1), "lock.red", new And(key, torch)));
	}

}
