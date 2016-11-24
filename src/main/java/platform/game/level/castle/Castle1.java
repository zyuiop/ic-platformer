package platform.game.level.castle;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import platform.game.World;
import platform.game.actors.Background;
import platform.game.actors.Orientation;
import platform.game.actors.Tooltip;
import platform.game.actors.blocks.Block;
import platform.game.actors.environment.Decoration;
import platform.game.actors.environment.Exit;
import platform.game.actors.environment.LaserDoor;
import platform.game.actors.environment.Lever;
import platform.game.level.PlayableLevel;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Castle1 extends PlayableLevel {
	@Override
	protected Vector startPosition() {
		return new Vector(-3, 2);
	}

	@Override
	protected Box getLimits() {
		return new Box(new Vector(-10, -2), new Vector(10, 20));
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

		world.register(new Block(new Vector(0, 5.5), 3, 1, "stone.3"));


		// laser and stuff
		Lever lever = new Lever(new Vector(-1.5, 1.25), .5, 30D, false, "lever.red.off", "lever.red.on", null);
		world.register(lever);
		world.register(new LaserDoor(new Vector(0, 3), 4, Orientation.VERICAL, "red", lever));
		world.register(new Exit(new Vector(3, 1.5)));

		world.register(new Decoration(new Vector(2.2, 1.2), .5, "exit", Math.PI / 8));

		world.register(new Background("background.hills", true, false));

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/kenvector_future_thin.ttf")).deriveFont(Font.BOLD, 20);
			world.register(new Tooltip(startPosition().add(new Vector(-4, -2.5)), font, Color.BLACK, "Bienvenue dans mon chateau, aventurier", 8, 1, .5, .5));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
