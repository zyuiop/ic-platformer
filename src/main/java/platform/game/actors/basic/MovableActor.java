package platform.game.actors.basic;

import platform.game.actors.interfaces.IMovable;
import platform.util.*;

/**
 * @author zyuiop
 *
 * This class defines a common base for different actors that can move, and that are moved
 * automatically by different forces of the world
 */
public abstract class MovableActor extends PositionedActor implements IMovable {
	private Vector velocity;

	public MovableActor(Box box, String spriteName, Vector velocity) {
		super(box, spriteName);
		this.velocity = velocity;
	}

	public MovableActor(Vector position, double size, String spriteName, Vector velocity) {
		super(position, size, spriteName);
		this.velocity = velocity;
	}

	public MovableActor(Vector position, double sizeX, double sizeY, String spriteName, Vector velocity) {
		super(position, sizeX, sizeY, spriteName);
		this.velocity = velocity;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		Vector acceleration = computeAcceleration();
		setVelocity(getVelocity().add(acceleration.mul(delta)));
		setPosition(getPosition().add(getVelocity().mul(delta)));
	}

	/**
	 * Get the physical acceleration of this actor.
	 * @return the physical acceleration
	 * @implNote the default implementation only returns the gravity of the world.
	 */
	protected Vector computeAcceleration() {
		return getWorld().getGravity();
	}

	/**
	 * Get the current velocity of the actor
	 * @return the velocity of the actor
	 */
	@Override
	public Vector getVelocity() {
		return velocity;
	}

	/**
	 * Set the velocity of the actor.
	 * @param velocity the new velocity vector
	 */
	@Override
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
}
