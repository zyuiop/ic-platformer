package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.actors.basic.MovableActor;
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
