package platform.game.actors;

import platform.util.Vector;

/**
 * @author zyuiop
 *
 * Represents the side of a box
 */
public enum Side {
	TOP, BOTTOM, LEFT, RIGHT, UNKNOWN;

	public static Side compute(Vector delta) {
		if (delta.getX() != 0) {
			if (delta.getX() < 0)
				return LEFT;
			return RIGHT;
		}

		if (delta.getY() != 0) {
			if (delta.getY() > 0)
				return TOP;
			return BOTTOM;
		}

		return UNKNOWN;
	}
}
