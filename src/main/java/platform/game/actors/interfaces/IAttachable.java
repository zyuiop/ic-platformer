package platform.game.actors.interfaces;

import platform.util.Vector;

/**
 * @author zyuiop
 */
public interface IAttachable extends IMovable {
	void attachTo(IPositioned attachedTo, Vector positionDifference);

	void detach(Vector velocity);

	boolean isAttached();
}
