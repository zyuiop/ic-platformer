package platform.game.actors.animations;

import platform.game.actors.Actor;
import platform.game.actors.LivingActor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * The health overlay that follows the player everywhere to display its life
 */
public class Overlay extends Actor {
	private final LivingActor parent;

	public Overlay(LivingActor parent) {
		this.parent = parent;
	}

	@Override
	public void draw(Input input, Output output) {
		double health = 5.0 * parent.getHealth() / parent.getMaxHealth();
		for (int i = 1; i <= 5; ++i) {
			String name;
			if (health >= i) {
				name = "heart.full";
			} else if (health >= i - 0.5) {
				name = "heart.half";
			} else {
				name = "heart.empty";
			}

			output.drawSprite(getSprite(name), getBox(i));
		}
	}

	@Override
	public void postUpdate(Input input) {
		super.postUpdate(input);

		if (parent.isDead()) {
			getWorld().unregister(this);
		}
	}

	public Box getBox(int i) {
		return new Box(getPosition(i), .15, .15);
	}

	public Vector getPosition(int i) {
		return parent.getPosition().add(new Vector((i - 3D) / 7D, .5D));
	}

	@Override
	public int getPriority() {
		return 1000;
	}
}
