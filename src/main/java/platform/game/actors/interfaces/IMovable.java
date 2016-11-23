package platform.game.actors.interfaces;

import platform.util.Vector;

/**
 * @author zyuiop
 */
public interface IMovable extends IPositioned {
	Vector getVelocity();

	void setVelocity(Vector velocity);
}
