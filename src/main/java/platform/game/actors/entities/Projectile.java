package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.actors.Side;
import platform.game.actors.basic.MovableActor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class Projectile extends MovableActor {
	private Actor sender;

	public Projectile(String texture, Vector position, Vector velocity, Actor sender, double size) {
		super(position, size, texture, velocity);
		this.sender = sender;
	}
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

	protected double getAngle() {
		return getVelocity() == null ? 0 : getVelocity().getAngle();
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		if (sprite != null)
			output.drawSprite(sprite, getBox(), getAngle());
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
