package platform.game.actors.basic;

import platform.game.Actor;
import platform.game.Effect;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class DirectedActor extends PositionedActor {
	private Direction direction;

	public DirectedActor(String spriteName, double size, Vector position, Direction direction) {
		super(spriteName, size, position);
		this.direction = direction;
	}

	public DirectedActor(Sprite sprite, double size, Vector position, Direction direction) {
		super(sprite, size, position);
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	protected void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Box getBox() {
		return new Box(position, sizeX * getHitboxXRatio(), sizeY * getHitboxYRatio());
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		if (sprite != null) { output.drawSprite(sprite, super.getBox(), getDirection().getRotation()); }
	}

	protected double getHitboxXRatio() {
		return 1;
	}

	protected double getHitboxYRatio() {
		return 1;
	}

	public enum Direction {
		LEFT(new Vector(-1, 0), Math.PI / 2),
		RIGHT(new Vector(1, 0), -Math.PI / 2),
		UP(new Vector(0, 1), 0),
		DOWN(new Vector(0, -1), Math.PI);
		private final Vector unitVector;
		private final double rotation;

		Direction(Vector unitVector, double rotation) {
			this.unitVector = unitVector;
			this.rotation = rotation;
		}

		public Vector getUnitVector() {
			return unitVector;
		}

		public double getRotation() {
			return rotation;
		}
	}
}
