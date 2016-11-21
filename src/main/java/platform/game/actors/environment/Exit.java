package platform.game.actors.environment;

import platform.game.Actor;
import platform.game.Signal;
import platform.game.actors.basic.DisplayableActor;
import platform.game.actors.entities.Player;
import platform.game.data.ActorFactory;
import platform.game.level.Level;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Exit extends DisplayableActor {
	private Box box;
	private Signal signal;
	private Level targetLevel;

	protected Exit() {
	}

	public Exit(Vector position, Level targetLevel) {this(position, Signal.ENABLED, targetLevel);}

	public Exit(Vector position, Signal signal, Level targetLevel) {
		super("door.closed");
		this.box = new Box(position, 1, 1);
		this.signal = signal;
		this.targetLevel = targetLevel;
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
				getWorld().setNextLevel(targetLevel);
				getWorld().nextLevel();
			}
		}
	}

	@Override
	public Box getBox() {
		return box;
	}

	@Override
	public int getPriority() {
		return 50;
	}

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		box = factory.getDataMap().get("box").getAsBox();
		signal = factory.getSignal("signal");
		targetLevel = factory.getLevel("targetLevel");
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("box", box);
	}
}
