package platform.game.actors.basic;

import platform.game.actors.interfaces.IPositioned;
import platform.util.Box;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         An actor defined by its position and sprite
 */
public abstract class PositionedActor extends DisplayableActor implements IPositioned {
	protected final double sizeX;
	protected final double sizeY;
	protected Vector position;

	// The last position used as a middle
	private Vector lastCalcBase;
	private Box lastCalcBox;

	public PositionedActor(Box box, String spriteName) {
		this(box.getCenter(), box.getWidth(), box.getHeight(), spriteName);
		this.lastCalcBox = box;
		this.lastCalcBase = box.getCenter();
	}

	public PositionedActor(Vector position, double size, String spriteName) {
		super(spriteName);
		this.sizeX = size;
		this.sizeY = size;
		this.position = position;
	}

	public PositionedActor(Vector position, double sizeX, double sizeY, String spriteName) {
		super(spriteName);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.position = position;
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
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
