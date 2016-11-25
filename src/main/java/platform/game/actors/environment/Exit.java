package platform.game.actors.environment;

import platform.game.Actor;
import platform.game.Signal;
import platform.game.actors.basic.DisplayableActor;
import platform.game.actors.basic.PositionedActor;
import platform.game.actors.entities.Player;
import platform.game.level.Level;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Exit extends PositionedActor {
	private final Signal signal;
	private final Level targetLevel;

	public Exit(Vector position) {this(position, Signal.ENABLED, null);}

	public Exit(Vector position, Level targetLevel) {this(position, Signal.ENABLED, targetLevel);}

	public Exit(Vector position, Signal signal, Level targetLevel) {
		super(position, .4, 1, "door.closed");
		this.signal = signal;
		this.targetLevel = targetLevel;
		this.setBoxTransformer(box -> new Box(box.getCenter(), 1, 1));
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (signal.isActive() && getSpriteName().endsWith("closed"))
			setSpriteName("door.open");
		else if (!signal.isActive() && getSpriteName().endsWith("open"))
			setSpriteName("door.closed");
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);

		if (other instanceof Player && signal.isActive()) {
			if (getBox().isColliding(other.getBox())) {
				if (targetLevel != null)
					getWorld().setNextLevel(targetLevel);
				getWorld().nextLevel();
			}
		}
	}

	@Override
	public int getPriority() {
		return 50;
	}
}
