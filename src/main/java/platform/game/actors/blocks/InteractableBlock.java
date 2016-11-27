package platform.game.actors.blocks;

import platform.game.Actor;
import platform.game.actors.Direction;
import platform.game.actors.Side;
import platform.game.actors.basic.MovableActor;
import platform.game.actors.blocks.Block;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 *         A block with which the collision causes an interaction (example : jumper)
 */
public abstract class InteractableBlock extends Block {
	public InteractableBlock(Box box, String spriteName) {
		super(box, spriteName);
	}

	public InteractableBlock(Vector position, double size, String spriteName) {
		super(position, size, spriteName);
	}

	public InteractableBlock(Vector position, double sizeX, double sizeY, String spriteName) {
		super(position, sizeX, sizeY, spriteName);
	}

	public InteractableBlock(Box box, String spriteName, Direction direction) {
		super(box, spriteName, direction);
	}

	public InteractableBlock(Vector position, double size, String spriteName, Direction direction) {
		super(position, size, spriteName, direction);
	}

	public InteractableBlock(Vector position, double sizeX, double sizeY, String spriteName, Direction direction) {
		super(position, sizeX, sizeY, spriteName, direction);
	}

	/**
	 * Check if an interaction can come from the given side
	 *
	 * @param side the side to check
	 * @return true if an interaction can come from this size
	 *
	 * @implNote the default implementation returns true if the side corresponds to the orientation
	 * of the block
	 */
	protected boolean isRightSide(Side side) {
		switch (getDirection()) {
			case UP:
				return side == Side.TOP;
			case DOWN:
				return side == Side.BOTTOM;
			case LEFT:
				return side == Side.LEFT;
			case RIGHT:
				return side == Side.RIGHT;
		}

		return false;
	}

	/**
	 * Check if this block can be used. If this block implements a cooldown, it should return false
	 * when it's cooling down.
	 *
	 * @return true if the block can be used
	 */
	protected boolean canBeUsed() {
		return true;
	}

	/**
	 * Handle a validated interaction from an actor. When this method is called, this class already
	 * checked that the actor is interacting from the right side, and that the block can be used.
	 *
	 * @param other the actor interacting with this block
	 */
	protected abstract void doInteract(MovableActor other);

	@Override
	public void onCollide(Actor actor, Side side) {
		super.onCollide(actor, side);

		if (isRightSide(side) && canBeUsed() && actor instanceof MovableActor) {
			doInteract((MovableActor) actor);
		}
	}
}
