package platform.game.actors.blocks;

import platform.game.actors.Actor;
import platform.game.Signal;
import platform.game.World;
import platform.game.Side;
import platform.game.actors.entities.Player;
import platform.game.level.Level;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         The exit door of a level
 */
public class Exit extends Block {
	private final Signal signal;
	private final Level targetLevel;

	/**
	 * Create an exit using the postion of its center
	 * The target level is, by default, the next level as defined by {@link World#setNextLevel(Level)}
	 * Using this constructor, the created exit door is always opened.
	 *
	 * @param position the center of the exit block (which has a 1x1 size)
	 */
	public Exit(Vector position) {this(position, Signal.ENABLED, null);}

	/**
	 * Create an exit using the postio of its center and a target level
	 * Using this constructor, the created exit door is always opened.
	 *
	 * @param position the center of the exit block (which has a 1x1 size)
	 * @param targetLevel the level that will be started when the player will have passed through the exit door
	 */
	public Exit(Vector position, Level targetLevel) {this(position, Signal.ENABLED, targetLevel);}

	/**
	 * Create an exit using the postion of its center
	 * The target level is, by default, the next level as defined by {@link World#setNextLevel(Level)}
	 *
	 * @param position the center of the exit block (which has a 1x1 size)
	 * @param signal a signal indicating if the exit door should be opened or not.
	 * The exit door is opened only when the signal is active.
	 */
	public Exit(Vector position, Signal signal) {this(position, signal, null);}

	/**
	 * Create an exit using the postion of its center
	 *
	 * @param position the center of the exit block (which has a 1x1 size)
	 * @param signal a signal indicating if the exit door should be opened or not.
	 * The exit door is opened only when the signal is active.
	 * @param targetLevel the level that will be started when the player will have passed through the exit door
	 */
	public Exit(Vector position, Signal signal, Level targetLevel) {
		super(position, 1, 1, "door.closed");
		this.signal = signal;
		this.targetLevel = targetLevel;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (signal.isActive() && getSpriteName().endsWith("closed")) {
			setSpriteName("door.open");
		} else if (!signal.isActive() && getSpriteName().endsWith("open")) {
			setSpriteName("door.closed");
		}
	}

	@Override
	public void onCollide(Actor actor, Side side) {
		super.onCollide(actor, side);

		if (actor instanceof Player && signal.isActive()) {
			if (targetLevel != null) { getWorld().setNextLevel(targetLevel); }
			getWorld().nextLevel();
		}
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
