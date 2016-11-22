package platform.game.actors.basic;

import platform.util.Box;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * An actor defined by its position and sprite
 */
public abstract class PositionedActor extends DisplayableActor {
	protected final double sizeX;
	protected final double sizeY;
	protected Vector position;

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

	public Box getBox() {
		if (getPosition() == null)
			return null;
		return new Box(getPosition(), sizeX, sizeY);
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public double getSizeX() {
		return sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}
}
