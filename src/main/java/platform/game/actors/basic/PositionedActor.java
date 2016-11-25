package platform.game.actors.basic;

import platform.game.actors.Direction;
import platform.game.actors.Orientation;
import platform.game.actors.interfaces.IPositioned;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         An actor defined by its position, sprite, and direction.
 */
public abstract class PositionedActor extends DisplayableActor implements IPositioned {
	private final double sizeX;
	private final double sizeY;
	private Vector position;
	// direction of the sprite
	private Direction direction = Direction.UP;

	// The last position used as a middle
	private Vector lastCalcBase;
	private Box lastCalcBox;
	private boolean directionChanged = false;

	/**
	 * Create a positioned actor using a box
	 *
	 * @param box the box defining the position of the actor
	 * @param spriteName the name of the sprite of the actor
	 */
	public PositionedActor(Box box, String spriteName) {
		this(box, spriteName, Direction.UP);
	}

	/**
	 * Create a positioned actor using a position and a size
	 *
	 * @param position the center of the actor
	 * @param size the size of the actor
	 * @param spriteName the name of the sprite of the actor
	 */
	public PositionedActor(Vector position, double size, String spriteName) {
		this(position, size, spriteName, Direction.UP);
	}

	/**
	 * Create a positioned actor using a position and a size
	 *
	 * @param position the center of the actor
	 * @param sizeX the width of the actor
	 * @param sizeY the height of the actor
	 * @param spriteName the name of the sprite of the actor
	 */
	public PositionedActor(Vector position, double sizeX, double sizeY, String spriteName) {
		this(position, sizeX, sizeY, spriteName, Direction.UP);
	}

	/**
	 * Create a positioned actor using a box
	 *
	 * @param box the box defining the position of the actor, not null
	 * @param spriteName the name of the sprite of the actor
	 * @param direction the direction of the actor. The direction is applied on the provided box an
	 * the sprite, which means the sprite is rotated by the correct angle (considering that the default
	 * direction is JUMP). The box is rotated if the direction is not vertical.
	 */
	public PositionedActor(Box box, String spriteName, Direction direction) {
		this(box.getCenter(), box.getWidth(), box.getHeight(), spriteName, direction);
	}

	/**
	 * Create a positioned actor using a box
	 *
	 * @param position the center of the actor
	 * @param size the size of the actor
	 * @param spriteName the name of the sprite of the actor
	 * @param direction the direction of the actor. The direction is applied on the provided box an
	 * the sprite, which means the sprite is rotated by the correct angle (considering that the default
	 * direction is JUMP). The box is rotated if the direction is not vertical.
	 */
	public PositionedActor(Vector position, double size, String spriteName, Direction direction) {
		this(position, size, size, spriteName, direction);
	}

	/**
	 * Create a positioned actor using a box
	 *
	 * @param position the center of the actor
	 * @param sizeX the width of the actor
	 * @param sizeY the height of the actor
	 * @param spriteName the name of the sprite of the actor
	 * @param direction the direction of the actor. The direction is applied on the provided box an
	 * the sprite, which means the sprite is rotated by the correct angle (considering that the default
	 * direction is JUMP). The box is rotated if the direction is not vertical.
	 */
	public PositionedActor(Vector position, double sizeX, double sizeY, String spriteName, Direction direction) {
		super(spriteName);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.position = position;
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	/**
	 * Change the direction of this actor.
	 * <br>The texture of this actor will be rotated by the correct angle, considering that the
	 * default state is {@link Direction#UP}
	 * <br>If the direction orientation is different, the box will be recalculated, inverting
	 * width and height.
	 * @param direction the new direction, not null
	 */
	public void setDirection(Direction direction) {
		if (direction == null) { throw new NullPointerException(); }

		this.directionChanged = (this.direction == null ||
				this.direction.getOrientation() != direction.getOrientation());
		this.direction = direction;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector position) {
		this.position = position;
	}

	private boolean boxChanged() {
		Vector currentPosition = getPosition();
		if (lastCalcBase == null && currentPosition == null) {
			// same (null) position
			return false;
		}

		if (lastCalcBase != null && currentPosition != null && currentPosition.equals(lastCalcBase)) {
			// same position
			if (directionChanged) {
				directionChanged = false;
				return true; // need recalculation because direction changed
			} else {
				return false; // nothing changed
			}
		}

		lastCalcBase = currentPosition;
		return true;
	}

	@Override
	public Box getBox() {
		if (!boxChanged()) {
			return lastCalcBox;
		}

		if (lastCalcBase == null) {
			lastCalcBox = null;
		} else {
			// if orientation is horizontal, we invert the coordinates.
			lastCalcBox = direction.getOrientation() == Orientation.VERICAL ?
					new Box(getPosition(), sizeX, sizeY) : new Box(getPosition(), sizeY, sizeX);
		}
		return lastCalcBox;
	}

	/**
	 * Get the width of this actor. This width doesn't take into account the eventual orientation
	 * modification.
	 * @return the width of the actor in a vertical orientation
	 */
	public double getSizeX() {
		return sizeX;
	}

	/**
	 * Get the height of this actor. This height doesn't take into account the eventual orientation
	 * modification.
	 * @return the height of the actor in a vertical orientation
	 */
	public double getSizeY() {
		return sizeY;
	}

	@Override
	public double getRotation() {
		return direction.getRotation();
	}
}
