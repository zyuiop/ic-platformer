package platform.game.level.castle;

import java.awt.Font;
import platform.game.KeyBindings;
import platform.game.World;
import platform.game.actors.Background;
import platform.game.actors.Orientation;
import platform.game.actors.RepeatBehaviour;
import platform.game.actors.blocks.Block;
import platform.game.actors.environment.InvisiblePlayerDetector;
import platform.game.actors.environment.Decoration;
import platform.game.actors.blocks.Exit;
import platform.game.actors.environment.LaserDoor;
import platform.game.actors.environment.Lever;
import platform.game.actors.ui.DismissableTextBox;
import platform.game.actors.ui.SlowingAdapter;
import platform.game.actors.ui.TriggerableTextbox;
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

		Block middle = new Block(new Vector(0, .5), 6, 1, "castle.middle");
		middle.setRepeatBehaviour(new RepeatBehaviour(1, 1, true, false));

		Block center = new Block(new Vector(0, -7.5), 6, 15, "castle.center");
		center.setRepeatBehaviour(new RepeatBehaviour(1, 1, true, true));

		world.register(middle);
		world.register(center);

		world.register(new Block(new Vector(-3.5, .5), 1, "castle.left"));
		world.register(new Block(new Vector(3.5, .5), 1, "castle.right"));

		world.register(new Block(new Vector(0, 5.5), 3, 1, "stone.3"));


		// laser and stuff
		Lever lever = new Lever(new Vector(-1.5, 1.25), .5, 30D, false, "lever.red.off", "lever.red.on", null);
		world.register(lever);

		InvisiblePlayerDetector detector = new InvisiblePlayerDetector(new Vector(-1.5, 1.25), .05);
		world.register(detector);

		world.register(new LaserDoor(new Vector(0, 3), 4, Orientation.VERICAL, "red", lever));
		world.register(new Exit(new Vector(3, 1.5)));

		world.register(new Decoration(new Vector(2.2, 1.2), .5, "exit", Math.PI / 8));

		world.register(new Background("background.hills", true, false));

		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

		KeyBindings kb = KeyBindings.getInstance();
		world.register(new DismissableTextBox(startPosition().add(new Vector(0, -3)),
				"text.background", font, 0, 0.3, 8, .4, .2, .25, .3, "Déplacez vous avec " +
				"[" + kb.getFirstConfiguredKey(KeyBindings.Key.LEFT) + "] et " +
				"[" + kb.getFirstConfiguredKey(KeyBindings.Key.RIGHT) + "] et sautez avec " +
				"[" + kb.getFirstConfiguredKey(KeyBindings.Key.JUMP) +"]")
				.setLinesAdapter(new SlowingAdapter(.05)));
		world.register(new TriggerableTextbox(detector.getPosition().add(new Vector(0, -3)),
				"text.background", font, 0, 0.3, 6, .4, .2, .25, .3, detector, "Utilisez la touche" +
				" [" +  kb.getFirstConfiguredKey(KeyBindings.Key.USE) + "] pour intéragir.", "Attention, les lasers ça pique !")
				.setLinesAdapter(new SlowingAdapter(.05)));
		world.setViewRadius(5D);
	}
}
