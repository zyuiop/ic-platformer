package platform.game.actors;

import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         An actor defined by its sprite and that can be displayed
 */
public abstract class PositionedActor extends Actor {
	private final double sizeX;
	private final double sizeY;
	private Vector position;

	// The last position used as a middle
	private Vector lastCalcBase;
	private Box lastCalcBox;

	/**
	 * Create a positioned actor using a box
	 *
	 * @param box the box defining the position of the actor, not null
	 */
	public PositionedActor(Box box) {
		this(box.getCenter(), box.getWidth(), box.getHeight());
	}

	/**
	 * Create a positioned actor using a box
	 *
	 * @param position the center of the actor
	 * @param size the size of the actor
	 */
	public PositionedActor(Vector position, double size) {
		this(position, size, size);
	}

	/**
	 * Create a positioned actor using a box
	 *
	 * @param position the center of the actor
	 * @param sizeX the width of the actor
	 * @param sizeY the height of the actor
	 */
	public PositionedActor(Vector position, double sizeX, double sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.position = position;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

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
			return false; // nothing changed
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
			lastCalcBox = new Box(getPosition(), sizeX, sizeY);
		}
		return lastCalcBox;
	}

	/**
	 * Get the width of this actor. This width doesn't take into account the eventual orientation
	 * modification.
	 *
	 * @return the width of the actor in a vertical orientation
	 */
	public double getSizeX() {
		return sizeX;
	}

	/**
	 * Get the height of this actor. This height doesn't take into account the eventual orientation
	 * modification.
	 *
	 * @return the height of the actor in a vertical orientation
	 */
	public double getSizeY() {
		return sizeY;
	}
}
