package platform.game.actors.environment;

import platform.game.Signal;
import platform.game.data.ActorFactory;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class MovingPlatform extends Block {
	private Vector first;
	private Vector second;
	private double current = 0;
	private Signal signal;

	protected MovingPlatform() {
	}

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

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		first = factory.getDataMap().get("first").getAsVector();
		second = factory.getDataMap().get("second").getAsVector();
		signal = factory.getSignal("signal");
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("first", first);
		factory.getDataMap().put("second", second);
	}
}
