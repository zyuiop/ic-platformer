package platform.game.actors.environment;

import platform.game.Signal;
import platform.game.actors.animations.BlowAnimation;
import platform.game.actors.blocks.Block;
import platform.game.particles.ParticleEffect;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

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
			ParticleEffect.BLOW.play(getWorld(), super.getPosition());
	}

	@Override
	public void draw(Input input, Output output) {
		if (!listenSignal.isActive())
			super.draw(input, output);
	}

	@Override
	public Vector getPosition() {
		if (isSolid())
			return super.getPosition();
		return null;
	}

	@Override
	public boolean isSolid() {
		return !listenSignal.isActive();
	}
}
