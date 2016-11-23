package platform.game.actors.basic;

import platform.game.Actor;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class InteractableBlock extends OrientedBlock {
	public InteractableBlock(Vector position, double size, String spriteName, Direction direction) {
		super(position, size, spriteName, direction);
	}


	protected boolean isRightDirection(Vector vector) {
		switch (getDirection()) {
			case UP:
				return vector.getY() < -1;
			case DOWN:
				return vector.getY() > 1;
			case LEFT:
				return vector.getX() > 1;
			case RIGHT:
				return vector.getX() < -1;
		}

		return false;
	}

	protected boolean canBeUsed() {
		return true;
	}

	// TODO : rework this
	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (!(other instanceof MovableActor))
			return;

		if (!getBox().isColliding(other.getBox()))
			return;

		MovableActor movableActor = ((MovableActor) other);
		if (!(isRightDirection(movableActor.getVelocity()) && canBeUsed())) {
			if (!isSolid())
				return;

			Vector delta = getBox().getCollision(other.getBox());

			if (delta != null) {
				movableActor.setPosition(movableActor.getPosition().add(delta));

				if (delta.getX() != 0D) {
					movableActor.setVelocity(new Vector(0D, movableActor.getVelocity().getY()));
				}
				if (delta.getY() != 0D) {
					movableActor.setVelocity(new Vector(movableActor.getVelocity().getX(), 0D));
				}
			}
			return;
		}

		doInteract(movableActor);
	}

	protected abstract void doInteract(MovableActor other);
}
