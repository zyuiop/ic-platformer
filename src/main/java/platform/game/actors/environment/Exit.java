package platform.game.actors.environment;

import platform.game.Actor;
import platform.game.Signal;
import platform.game.actors.Side;
import platform.game.actors.blocks.Block;
import platform.game.actors.entities.Player;
import platform.game.level.Level;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Exit extends Block {
	private final Signal signal;
	private final Level targetLevel;

	public Exit(Vector position) {this(position, Signal.ENABLED, null);}

	public Exit(Vector position, Level targetLevel) {this(position, Signal.ENABLED, targetLevel);}

	public Exit(Vector position, Signal signal) {this(position, signal, null);}

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

		if (actor instanceof Player) {
			if (targetLevel != null) { getWorld().setNextLevel(targetLevel); }
			getWorld().nextLevel();
		}
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
