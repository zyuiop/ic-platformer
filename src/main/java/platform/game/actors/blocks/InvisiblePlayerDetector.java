package platform.game.actors.blocks;

import platform.game.actors.Actor;
import platform.game.Signal;
import platform.game.actors.PositionedActor;
import platform.game.actors.entities.Player;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A simple bounding box that reports the presence of players in it
 */
public class InvisiblePlayerDetector extends PositionedActor implements Signal {
	private boolean isPresent = false;

	public InvisiblePlayerDetector(Box box) {
		super(box);
	}

	public InvisiblePlayerDetector(Vector position, double size) {
		super(position, size);
	}

	public InvisiblePlayerDetector(Vector position, double sizeX, double sizeY) {
		super(position, sizeX, sizeY);
	}

	@Override
	public boolean isActive() {
		return isPresent;
	}

	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
		this.isPresent = false;
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);

		if (other instanceof Player && other.getBox() != null && getBox().isColliding(other.getBox())) {
			this.isPresent = true;
		}
	}

	@Override
	public int getPriority() {
		return 100;
	}
}
