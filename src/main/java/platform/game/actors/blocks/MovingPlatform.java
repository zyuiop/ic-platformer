package platform.game.actors.blocks;

import platform.game.Actor;
import platform.game.Signal;
import platform.game.actors.Side;
import platform.game.actors.entities.Player;
import platform.game.actors.interfaces.IPositioned;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class MovingPlatform extends Block {
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
	public Vector getPosition() {
		return super.getPosition().add((getSecond().sub(getFirst()).mul(current)));
	}

	@Override
	public void onCollide(Actor actor, Side side) {
		super.onCollide(actor, side);

		if (side == Side.TOP && actor instanceof Player && !((Player) actor).isAttached() &&
				(Math.abs(((Player) actor).getVelocity().getX())) < .2) {
			// This is a bit glitchy but it works.
			// The last check is there to avoid sticking the player when he wants to move

			if (actor.getPosition().getY() >= getPosition().getY()) {
				Vector diff = actor.getPosition().sub(getPosition());
				((Player) actor).attachTo(this, diff);
			}
		}
	}
}
