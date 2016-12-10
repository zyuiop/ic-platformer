package platform.game.level.castle;

import java.awt.Font;
import java.util.function.Function;
import platform.game.KeyBindings;
import platform.game.World;
import platform.game.actors.environment.Background;
import platform.game.Direction;
import platform.game.RepeatBehaviour;
import platform.game.actors.blocks.Block;
import platform.game.actors.technical.InvisiblePlayerDetector;
import platform.game.actors.blocks.Jumper;
import platform.game.actors.blocks.OneWayMovingPlatform;
import platform.game.actors.blocks.Spikes;
import platform.game.actors.environment.Decoration;
import platform.game.actors.blocks.Exit;
import platform.game.actors.environment.Heart;
import platform.game.actors.environment.Torch;
import platform.game.actors.ui.SlowingAdapter;
import platform.game.actors.ui.TriggerableTextbox;
import platform.game.level.EndGameLevel;
import platform.game.level.PlayableLevel;
import platform.game.logic.And;
import platform.game.logic.Not;
import platform.util.Box;
import platform.util.Vector;

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

		// LEFT SIDE
		Block middle = new Block(new Vector(-8.5, .5), 7, 1, "castle.middle");
		middle.setRepeatBehaviour(new RepeatBehaviour(1, 1, true, false));
		world.register(middle);

		Block center = new Block(new Vector(-8.5, -7.5), 7, 15, "castle.center");
		center.setRepeatBehaviour(new RepeatBehaviour(1, 1, true, true));
		world.register(center);

		// RIGHT SIDE
		middle = new Block(new Vector(8.5, .5), 7, 1, "castle.middle");
		middle.setRepeatBehaviour(new RepeatBehaviour(1, 1, true, false));
		world.register(middle);

		center = new Block(new Vector(8.5, -7.5), 7, 15, "castle.center");
		center.setRepeatBehaviour(new RepeatBehaviour(1, 1, true, true));
		world.register(center);

		// CENTER
		middle = new Block(new Vector(0, -4.5), 10, 1, "castle.middle");
		middle.setRepeatBehaviour(new RepeatBehaviour(1, 1, true, false));
		world.register(middle);

		center = new Block(new Vector(0, -9.5), 10, 10, "castle.center");
		center.setRepeatBehaviour(new RepeatBehaviour(1, 1, true, true));
		world.register(center);

		for (double x = -4.5; x <= 4.5; ++x) {
			if (x > -4.5) {
				world.register(new Spikes(new Vector(x - 0.25, -3.75), .5, Direction.UP, 1D));
			} else {
				world.register(new Jumper(new Vector(x - 0.25, -3.75), .5, .5, Direction.UP, 10.8D));
			}
			world.register(new Spikes(new Vector(x + 0.25, -3.75), .5, Direction.UP, 1D));
		}

		world.register(new Heart(new Vector(0, 1.5), 2D, 0, Double.POSITIVE_INFINITY));

		// Contours
		Block leftStone = new Block(new Vector(-12.5, -3.5), 1, 18, "stone.7");
		leftStone.setRepeatBehaviour(new RepeatBehaviour(1, 3, false, true));
		world.register(leftStone);

		Block rightStone = new Block(new Vector(12.5, -3.5), 1, 18, "stone.7");
		rightStone.setRepeatBehaviour(new RepeatBehaviour(1, 3, false, true));
		world.register(rightStone);

		Block topStone = new Block(new Vector(0, 5), 24, 1, "stone.3");
		topStone.setRepeatBehaviour(new RepeatBehaviour(3, 1, true, false));
		world.register(topStone);

		Torch torch = new Torch(new Vector(-8, 1.5), true);
		world.register(torch);

		world.register(new Exit(new Vector(10, 1.5), torch, new EndGameLevel()));
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
