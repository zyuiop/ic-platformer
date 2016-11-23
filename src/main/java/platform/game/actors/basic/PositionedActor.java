package platform.game.actors.basic;

import platform.util.Box;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         An actor defined by its position and sprite
 */
public abstract class PositionedActor extends DisplayableActor {
	protected final double sizeX;
	protected final double sizeY;
	protected Vector position;

	private Vector lastCalcBase;
	private Box lastCalcBox;

	public PositionedActor(String spriteName, double size, Vector position) {
		super(spriteName);
		this.sizeX = size;
		this.sizeY = size;
		this.position = position;
	}

	public PositionedActor(Sprite sprite, double size, Vector position) {
		super(sprite);
		this.sizeX = size;
		this.sizeY = size;
		this.position = position;
	}

	public PositionedActor(String spriteName, double sizeX, double sizeY, Vector position) {
		super(spriteName);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.position = position;
	}

	public PositionedActor(Sprite sprite, double sizeX, double sizeY, Vector position) {
		super(sprite);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.position = position;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	private boolean positionChanged() {
		Vector currentPosition = getPosition();
		if (lastCalcBase == null && currentPosition == null) { return false; }
		if (lastCalcBase != null && currentPosition != null && currentPosition.equals(lastCalcBase)) {
			return false;
		}

		lastCalcBase = currentPosition;
		return true;
	}

	public Box getBox() {
		if (!positionChanged()) { return lastCalcBox; }

		if (lastCalcBase == null) {
			lastCalcBox = null;
		} else {
			lastCalcBox = new Box(getPosition(), sizeX, sizeY);
		}
		return lastCalcBox;
	}

	public double getSizeX() {
		return sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}
}
