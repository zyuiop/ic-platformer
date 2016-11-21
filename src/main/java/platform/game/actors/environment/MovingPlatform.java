package platform.game.actors.environment;

import platform.game.Signal;
import platform.game.actors.MutableActor;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class MovingPlatform extends Block implements MutableActor {
	private final Vector first;
	private final Vector second;
	private double current = 0;
	private Signal signal;

	public MovingPlatform(Box box, String sprite, Vector first, Vector second, Signal signal) {
		super(box, sprite);
		this.first = first;
		this.second = second;
		this.signal = signal;
	}

	public Vector getFirst() {
		return first;
	}

	public Vector getSecond() {
		return second;
	}

	public boolean isActive() {
		return signal.isActive();
	}

	protected double getCurrent() {
		return current;
	}

	protected void setCurrent(double current) {
		this.current = current;
	}

	@Override
	public Box getBox() {
		return super.getBox().add((getSecond().sub(getFirst()).mul(current)));
	}
}
