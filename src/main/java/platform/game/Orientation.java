package platform.game;

/**
 * @author zyuiop
 * This enumeration represents a basic orientation (vertical or horizontal). It's useful for
 * sprites that can be used on both direction of same orientation (i.e. facing left = facing right).
 */
public enum Orientation {
	HORIZONTAL(Math.PI / 2), VERICAL(0);

	private final double angle;

	Orientation(double angle) {
		this.angle = angle;
	}

	/**
	 * Get the angle corresponding to this orientation. This angle is always positive.
	 * @return the angle corresponding to the orientation, where vertical is 0
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Get the angle corresponding to this orientation, but relative to an other orientation.
	 * @param spriteOrientation the orientation used as a reference (by default : vertical)
	 * @return the angle corresponding to this orientation, with a specified sprite orientation
	 */
	public double getAngle(Orientation spriteOrientation) {
		return Math.abs(getAngle() - spriteOrientation.getAngle());
	}
}
