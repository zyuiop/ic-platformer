package platform.game.actors.animations;

import platform.game.actors.DisplayableActor;
import platform.game.actors.entities.Player;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * The small target on the cursor of the mouse, used as a shoot-helper
 */
public class Crosshair extends DisplayableActor {
	private final Player parent;

	public Crosshair(Player parent) {
		super(null, .5, "crosshair");
		this.parent = parent;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		Vector vector = input.getMouseLocation().sub(parent.getPosition());
		if (vector.getLength() > 7D) {
			double angle = vector.getAngle();
			vector = Vector.X.rotated(angle).mul(7D);
		}
		setPosition(parent.getPosition().add(vector));
	}

	@Override
	public void draw(Input input, Output output) {
		if (output.getBox().isColliding(input.getMouseLocation()))
			super.draw(input, output);
	}

	@Override
	public void postUpdate(Input input) {
		super.postUpdate(input);

		if (parent.isDead()) {
			getWorld().unregister(this);
		}
	}

	@Override
	public int getPriority() {
		return 1000;
	}
}
