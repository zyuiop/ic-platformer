package platform.game.actors.blocks;

import platform.game.actors.Direction;
import platform.game.actors.Orientation;
import platform.game.actors.basic.DisplayableActor;
import platform.util.Box;
import platform.util.Vector;

/**
 * Simple solid actor that does nothing more than being solid.
 */
public class Block extends DisplayableActor {
	// direction of the box
	private Direction direction = Direction.UP;

	public Block(Box box, String spriteName) {
		super(box.getCenter(), box.getWidth(), box.getHeight(), spriteName);
	}

	public Block(Vector position, double size, String spriteName) {
		super(position, size, spriteName);
	}

	public Block(Vector position, double sizeX, double sizeY, String spriteName) {
		super(position, sizeX, sizeY, spriteName);
	}

	/**
	 * Create a block using a box, a sprite, and a direction
	 * @param box the box of the block
	 * @param spriteName the sprite of the block
	 * @param direction the facing direction of the block. The default facing direction is {@link Direction#UP}
	 * and if the provided direction is not UP, the sprite will be rotated by the correct angle to display
	 * correctly. If the direction is not a vertical one, the box will also be rotated correctly to reflect
	 * the block orientation
	 */
	public Block(Box box, String spriteName, Direction direction) {
		super(box, spriteName);
		this.direction = direction;

	}

	/**
	 * Create a block using its center and its size
	 * @param position the center of the block
	 * @param size the size of the block
	 * @param spriteName the sprite of the block
	 * @param direction the facing direction of the block. The default facing direction is {@link Direction#UP}
	 * and if the provided direction is not UP, the sprite will be rotated by the correct angle to display
	 * correctly.
	 */
	public Block(Vector position, double size, String spriteName, Direction direction) {
		super(position, size, spriteName);
		this.direction = direction;

	}

	/**
	 * Create a block using its center and its size
	 * @param position the center of the block
	 * @param sizeX the size on the x axis (width)
	 * @param sizeY the size on the y axis (height)
	 * @param spriteName the sprite of the block
	 * @param direction the facing direction of the block. The default facing direction is {@link Direction#UP}
	 * and if the provided direction is not UP, the sprite will be rotated by the correct angle to display
	 * correctly. If the direction is not a vertical one, the height and width will be inverted by the block.
	 */
	public Block(Vector position, double sizeX, double sizeY, String spriteName, Direction direction) {
		super(position, sizeX, sizeY, spriteName);
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	public double getRotation() {
		return direction.getRotation();
	}

	@Override
	public Box getBox() {
		Box box = super.getBox();
		if (box == null || direction.getOrientation() == Orientation.VERICAL) { return box; }

		// if orientation is horizontal, we invert the coordinates.
		return new Box(box.getCenter(), box.getHeight(), box.getWidth());
	}
}
