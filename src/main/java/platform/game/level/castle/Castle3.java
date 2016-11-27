package platform.game.level.castle;

import java.awt.Font;
import java.util.function.Function;
import platform.game.KeyBindings;
import platform.game.World;
import platform.game.actors.Background;
import platform.game.actors.Direction;
import platform.game.actors.blocks.Block;
import platform.game.actors.blocks.InvisiblePlayerDetector;
import platform.game.actors.blocks.Jumper;
import platform.game.actors.blocks.OneWayMovingPlatform;
import platform.game.actors.blocks.Spikes;
import platform.game.actors.environment.Decoration;
import platform.game.actors.environment.Exit;
import platform.game.actors.environment.Torch;
import platform.game.actors.ui.SlowingAdapter;
import platform.game.actors.ui.TriggerableTextbox;
import platform.game.level.PlayableLevel;
import platform.game.logic.And;
import platform.game.logic.Not;
import platform.util.Box;
import platform.util.Vector;
import platform.util.sounds.Sound;

/**
 * @author zyuiop
 */
public class Castle3 extends PlayableLevel {
	@Override
	protected Vector startPosition() {
		return new Vector(-10, 2);
	}

	@Override
	protected Box getLimits() {
		return new Box(new Vector(-20, -10), new Vector(20, 20));
	}

	@Override
	public void register(World world) {
		super.register(world);

		world.setNextLevel(world.getLevelManager().getNextLevel(this));

		for (double x = -11.5; x <= -5.5; ++x) {
			for (double y = -15.5; y <= -0.5; ++y) {
				world.register(new Block(new Vector(x, y), 1, "castle.center"));
			}

			world.register(new Block(new Vector(x, .5), 1, "castle.middle"));
		}

		for (double x = 5.5; x <= 11.5; ++x) {
			for (double y = -15.5; y <= -0.5; ++y) {
				world.register(new Block(new Vector(x, y), 1, "castle.center"));
			}

			world.register(new Block(new Vector(x, .5), 1, "castle.middle"));
		}

		for (double x = -4.5; x <= 4.5; ++x) {
			for (double y = -15.5; y <= -5.5; ++y) {
				world.register(new Block(new Vector(x, y), 1, "castle.center"));
			}

			world.register(new Block(new Vector(x, -4.5), 1, "castle.middle"));
			if (x > -4.5) {
				world.register(new Spikes(new Vector(x - 0.25, -3.75), .5, Direction.UP, 1D));
			} else {
				world.register(new Jumper(new Vector(x - 0.25, -3.75), .5, .5, Direction.UP, 10.8D));
			}
			world.register(new Spikes(new Vector(x + 0.25, -3.75), .5, Direction.UP, 1D));
		}

		// Contours de la map
		for (double y = 4; y >= -15.5; y -= 3) {
			world.register(new Block(new Vector(-12.5, y), 1, 3, "stone.7"));
			world.register(new Block(new Vector(12.5, y), 1, 3, "stone.7"));
		}

		for (double x = -11.5; x <= 11.5; x += 1) {
			world.register(new Block(new Vector(x, 5), 1, 1, "stone.4"));
		}

		Torch torch = new Torch(new Vector(-8, 1.5), true);
		world.register(torch);

		world.register(new Exit(new Vector(10, 1.5), torch)); // todo : add key up ?
		world.register(new Decoration(new Vector(9.8, 1.2), .5, "exit", Math.PI / 8));
		world.register(new Decoration(new Vector(-5.3, 1.2), .5, "exit", Math.PI / 8));


		Not elevators = new Not(torch);

		// Platforms
		double spriteRatio = 18D / 70D; // the ratio of the sprite
		Function<Box, Box> transformer = (box) -> {
			double newHeight = box.getWidth(); // 1/1 ratio

			// we add a useless zone in the bottom
			Vector min = box.getMin().add(new Vector(0, box.getHeight() - newHeight));
			return new Box(min, box.getMax());
		};

		double startY = -4 - spriteRatio / 2D; // center y position
		double endY = 1 - spriteRatio / 2D;

		world.register(new OneWayMovingPlatform(new Box(new Vector(-3.5, startY), 1.5, spriteRatio),
				"metalPlatform", new Vector(4.5, startY), new Vector(4.5, endY), elevators, .35)
				.boxTransformer(transformer));
		world.register(new OneWayMovingPlatform(new Box(new Vector(3.5, startY), 1.5, spriteRatio),
				"metalPlatform", new Vector(4.5, startY), new Vector(4.5, endY), elevators, .35)
				.boxTransformer(transformer));

		InvisiblePlayerDetector djumpDetector = new InvisiblePlayerDetector(new Vector(-2, 1), 4, 6);
		world.register(djumpDetector);

		InvisiblePlayerDetector torchDetector = new InvisiblePlayerDetector(torch.getPosition(), 0.05, 2);
		world.register(torchDetector);

		InvisiblePlayerDetector endZoneDetector = new InvisiblePlayerDetector(new Vector(6, 2), 2, 2);
		world.register(endZoneDetector);

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

		Vector view = new Vector(0, -2);
		KeyBindings kb = KeyBindings.getInstance();

		world.register(new TriggerableTextbox(view.add(new Vector(0, -5)),
				"text.background", font, 0.05, .7, 15, .7, .2, .25, .7, torchDetector, "Eteindre " +
				"cette torche permet peut être de traverser...", "Utilisez la touche " +
				"[" +  kb.getFirstConfiguredKey(KeyBindings.Key.BLOW) + "] pour souffler sur la torche")
				.setLinesAdapter(new SlowingAdapter(.05)));
		world.register(new TriggerableTextbox(view.add(new Vector(0, -5)),
				"text.background", font, 0.05, .7, 15, .7, .2, .25, .7,
				new And(djumpDetector, elevators), "Il faut parfois utiliser un double saut pour " +
				"traverser", "Utilisez la touche [" + kb.getFirstConfiguredKey(KeyBindings.Key.JUMP) + "] en l'air pour faire un double saut")
				.setLinesAdapter(new SlowingAdapter(.05)));
		world.register(new TriggerableTextbox(view.add(new Vector(0, -5)),
				"text.background", font, 0.05, .7, 15, .7, .2, .25, .7, endZoneDetector, "La" +
				" porte s'est fermée lors de l'extinction de la torche", "Utilisez la touche " +
				"[" + kb.getFirstConfiguredKey(KeyBindings.Key.ATTACK) + "] ou cliquez pour lancer " +
				"des boules de feu", "Vous pouvez utiliser la souris pour viser plus précisément")
				.setLinesAdapter(new SlowingAdapter(.05)));

		world.register(new Background("background.cave", true, false));
		world.setViewRadius(8D);
		world.setView(view);
		world.setFixedView(true);
	}
}
