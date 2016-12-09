package platform.game.actors.blocks;

import platform.game.actors.Actor;
import platform.game.Signal;
import platform.game.Side;
import platform.game.actors.MovableActor;
import platform.game.actors.entities.Player;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A platform that moves when its signal is active. This class provides common methods used by all
 * moving platforms implementations.
 *
 * Although this actor can move, its movements are not controlled by anything else than itself, so
 * it doesn't extend {@link MovableActor} or {@link platform.game.actors.interfaces.IMovable}
 */
public abstract class MovingPlatform extends Block {
	private final Vector first;
	private final Vector second;
	private double current = 0;
	private final Signal signal;

	/**
	 * Create a moving platform. The platform will move between first and second positions.
	 * @param box the initial box of the platform
	 * @param sprite the sprite of the platform
	 * @param first the first position
	 * @param second the second position
	 * @param signal the signal to listen
	 */
	public MovingPlatform(Box box, String sprite, Vector first, Vector second, Signal signal) {
		super(box, sprite);
		this.first = first;
		this.second = second;
		this.signal = signal;
	}

	/**
	 * Get the first position of this platform. The platform will move between first and second
	 * positions.
	 * @return the first extreme position
	 */
	public Vector getFirst() {
		return first;
	}

	/**
	 * Get the second position of this platform. The platform will move between first and second
	 * positions.
	 * @return the second extreme position
	 */
	public Vector getSecond() {
		return second;
	}

	/**
	 * Check if the moving platorm is currently active
	 * @return true if the platform is active
	 */
	public boolean isActive() {
		return signal.isActive();
	}

	/**
	 * Get the current progression between the two positions, in [0;1]
	 * @return the current progression
	 */
	protected double getCurrent() {
		return current;
	}

	/**
	 * Set the current progression between the two positions
	 * @param current the new progression
	 */
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
