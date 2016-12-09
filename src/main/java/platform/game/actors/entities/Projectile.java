package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.actors.Side;
import platform.game.actors.basic.MovableActor;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * Represents an actor that can be thrown or shot by an other actor (the sender)
 */
public abstract class Projectile extends MovableActor {
	private Actor sender;

	/**
	 * Create a projectile
	 * @param texture the texture of the projectile
	 * @param position the position of the projectile (center)
	 * @param velocity the velocity to apply to this projectile
	 * @param sender the sender of the projectile (the actor who sent it)
	 * @param size the size of the projectile
	 */
	public Projectile(String texture, Vector position, Vector velocity, Actor sender, double size) {
		super(position, size, texture, velocity);
		this.sender = sender;
	}

	/**
	 * Create a projectile
	 * @param texture the texture of the projectile
	 * @param position the position of the projectile (center)
	 * @param velocity the velocity to apply to this projectile
	 * @param sender the sender of the projectile (the actor who sent it)
	 * @param sizeX the width of the projectile
	 * @param sizeY the height of the projectile
	 */
	public Projectile(String texture, Vector position, Vector velocity, Actor sender, double sizeX, double sizeY) {
		super(position, sizeX, sizeY, texture, velocity);
		this.sender = sender;
	}

	@Override
	public int getPriority() {
		return 1000;
	}

	/**
	 * Damage the given entity
	 * @param other the entity to damage
	 * @return false if collisions with block should be computed, true if not
	 */
	protected abstract boolean damage(Actor other);

	/**
	 * This method is called when the projectile hits a block, after computing collisions
	 * @param solidActor the block hit
	 * @param delta the computed easiest wayout from this block's hitbox
	 */
	protected abstract void hitBlock(Actor solidActor, Vector delta);

	public double getRotation() {
		return getVelocity() == null ? 0 : getVelocity().getAngle();
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (getBox().isColliding(other.getBox()) && !other.equals(sender))
			if (damage(other))
				return;

		if (other.isSolid() && other.getBox() != null) {
			Vector delta = other.getBox().getCollision(getPosition());
			if (delta != null) {
				other.onCollide(this, Side.compute(delta));
				hitBlock(other, delta);
			}
		}
	}
}
