package platform.game.actors;

/**
 * @author zyuiop
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

	// todo improve jd
	/**
	 * Get the angle corresponding to this orientation, but relative to an other orientation.
	 * @param spriteOrientation the orientation to compare
	 * @return
	 */
	public double getAngle(Orientation spriteOrientation) {
		return Math.abs(getAngle() - spriteOrientation.getAngle());
	}
}
