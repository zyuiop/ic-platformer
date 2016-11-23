package platform.game.actors.interfaces;

import platform.util.Vector;

/**
 * @author zyuiop
 */
public interface IPositioned extends IActor {
	Vector getPosition();

	void setPosition(Vector position);
}
