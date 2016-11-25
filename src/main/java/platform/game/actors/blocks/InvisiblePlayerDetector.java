package platform.game.actors.blocks;

import platform.game.Actor;
import platform.game.Signal;
import platform.game.actors.basic.DisplayableActor;
import platform.game.actors.entities.Player;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class InvisiblePlayerDetector extends DisplayableActor implements Signal {
	private boolean isPresent = false;

	public InvisiblePlayerDetector(Box box) {
		super(box, null);
	}

	public InvisiblePlayerDetector(Vector position, double size) {
		super(position, size, null);
	}

	public InvisiblePlayerDetector(Vector position, double sizeX, double sizeY) {
		super(position, sizeX, sizeY, null);
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
