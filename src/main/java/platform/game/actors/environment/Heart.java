package platform.game.actors.environment;

import platform.game.actors.Actor;
import platform.game.Effect;
import platform.game.actors.DisplayableActor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * An item a player can pick up to gain some health
 */
public class Heart extends DisplayableActor {
	private final double healthBonus;
	private double cooldown = 0D;
	private double cooldownTime = 10D;

	public Heart(Vector position, double healthBonus) {
		super(position, .5, "heart.full");
		this.healthBonus = healthBonus;
	}

	public Heart(Vector position, double healthBonus, double cooldown, double cooldownTime) {
		super(position, .5, "heart.full");
		this.healthBonus = healthBonus;
		this.cooldown = cooldown;
		this.cooldownTime = cooldownTime;
	}


	@Override
	public void update(Input input) {
		super.update(input);

		if (cooldown > 0)
			cooldown = Math.max(0, cooldown - input.getDeltaTime());
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);

		if (cooldown > 0)
			return;

		if (getBox().isColliding(other.getBox())) {
			if (other.hurt(this, Effect.HEAL, healthBonus, getPosition())) {
				this.cooldown = cooldownTime;
			}
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (cooldown == 0D)
			super.draw(input, output);
	}

	@Override
	public int getPriority() {
		return 50;
	}
}
