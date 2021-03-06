package platform.game;

import platform.util.Vector;

/**
 * @author zyuiop
 * Represents a basic facing direction. Used in oriented blocks. Allows to quickly give velocity
 * to projectiles or players, or to rotate sprites easily. It's more precise than {@link Orientation}
 */
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

	public Orientation getOrientation() {
		return (this == LEFT || this == RIGHT) ? Orientation.HORIZONTAL : Orientation.VERICAL;
	}
}
