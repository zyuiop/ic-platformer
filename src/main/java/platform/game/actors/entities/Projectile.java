package platform.game.actors.entities;

import platform.game.Actor;
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
		super(texture, size, position, velocity);
		this.sender = sender;
	}
	public Projectile(String texture, Vector position, Vector velocity, Actor sender, double sizeX, double sizeY) {
		super(texture, sizeX, sizeY, position, velocity);
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
				hitBlock(other, delta);
			}
		}
	}
}