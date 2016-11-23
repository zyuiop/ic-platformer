package platform.game.level.castle;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import platform.game.World;
import platform.game.actors.Background;
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
				world.register(new Block("castle.center", 1, new Vector(x, y)));
			}

			world.register(new Block("castle.middle", 1, new Vector(x, .5)));
		}

		world.register(new Block("castle.left", 1, new Vector(-3.5, .5)));
		world.register(new Block("castle.right", 1, new Vector(3.5, .5)));


		// laser and stuff
		Lever lever = new Lever(new Vector(-1.5, 1.25), .5, 30D, false, "lever.red.off", "lever.red.on", null);
		world.register(lever);
		world.register(new LaserDoor(new Vector(0, 2), 4, LaserDoor.Orientation.VERICAL, "red", lever));
		world.register(new Exit(new Vector(3, 1.5)));

		world.register(new Decoration("exit", .5, new Vector(2.2, 1.2), Math.PI / 8));

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
