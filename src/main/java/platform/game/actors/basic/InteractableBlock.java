package platform.game.actors.basic;

import platform.game.Actor;
import platform.game.actors.Direction;
import platform.game.actors.Side;
import platform.game.actors.blocks.Block;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
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

	protected boolean canBeUsed() {
		return true;
	}

	protected abstract void doInteract(MovableActor other);

	@Override
	public void onCollide(Actor actor, Side side) {
		super.onCollide(actor, side);

		if (isRightSide(side) && canBeUsed() && actor instanceof MovableActor) {
			doInteract((MovableActor) actor);
		}
	}
}
