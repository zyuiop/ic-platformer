package platform.game.actors.environment;

import platform.game.Signal;
import platform.game.actors.animations.BlowAnimation;
import platform.game.actors.blocks.Block;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;

/**
 * @author zyuiop
 */
public class Door extends Block {
	private final Signal listenSignal;
	private boolean lastState = false;

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
}
