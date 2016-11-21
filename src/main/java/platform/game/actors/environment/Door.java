package platform.game.actors.environment;

import platform.game.Signal;
import platform.game.actors.animations.BlowAnimation;
import platform.game.data.ActorFactory;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;

/**
 * @author zyuiop
 */
public class Door extends Block {
	private Signal listenSignal;
	private boolean lastState = false;

	protected Door() {
	}

	public Door(Box box, String sprite, Signal listenSignal) {
		super(box, sprite);
		this.listenSignal = listenSignal;
	}

	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
		lastState = listenSignal.isActive();
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (listenSignal.isActive() != lastState)
			getWorld().register(new BlowAnimation(super.getBox().getCenter()));
	}

	@Override
	public void draw(Input input, Output output) {
		if (!listenSignal.isActive())
			super.draw(input, output);
	}

	@Override
	public boolean isSolid() {
		return !listenSignal.isActive();
	}

	@Override
	public Box getBox() {
		return listenSignal.isActive() ? null : super.getBox();
	}

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		listenSignal = factory.getSignal("signal");
		if (listenSignal == null)
			throw new NullPointerException("signal cannot be null");
	}

	// We don't write anything in the actorFactory since our signal doesn't have a name
	// The name will have to be given in the level editor
}
