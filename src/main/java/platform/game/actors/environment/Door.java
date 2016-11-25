package platform.game.actors.environment;

import platform.game.Signal;
import platform.game.actors.Direction;
import platform.game.actors.Orientation;
import platform.game.actors.animations.BlowAnimation;
import platform.game.actors.blocks.Block;
import platform.game.particles.ParticleEffect;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A block that is solid and visible only when a given signal is not active
 */
public class Door extends Block {
	private final Signal listenSignal;
	private boolean lastState = false;

	public Door(Box box, String sprite, Signal listenSignal) {
		super(box, sprite);
		this.listenSignal = listenSignal;
		this.lastState = listenSignal.isActive();
	}

	public Door(Box box, String sprite, Direction direction, Signal listenSignal) {
		super(box, sprite, direction);
		this.listenSignal = listenSignal;
		this.lastState = listenSignal.isActive();
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (listenSignal.isActive() != lastState) {
			this.lastState = listenSignal.isActive();
			onChangeState(listenSignal.isActive());
		}
	}

	@Override
	public Vector getPosition() {
		if (isSolid())
			return super.getPosition();
		return null;
	}

	public void onChangeState(boolean newState) {
		ParticleEffect.BLOW.play(getWorld(), super.getPosition());
	}

	@Override
	public boolean isSolid() {
		return !listenSignal.isActive();
	}
}
