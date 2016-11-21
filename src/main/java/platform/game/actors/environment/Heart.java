package platform.game.actors.environment;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.actors.basic.PositionedActor;
import platform.game.data.ActorFactory;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Heart extends PositionedActor {
	private double healthBonus;
	private double cooldown = 0D;

	protected Heart() {
	}

	public Heart(Vector position, double healthBonus) {
		super("heart.full", .5, position);
		this.healthBonus = healthBonus;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (cooldown > 0) { cooldown = Math.max(0, cooldown - input.getDeltaTime()); }
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);

		if (cooldown > 0) { return; }

		if (getBox().isColliding(other.getBox())) {
			other.hurt(this, Effect.HEAL, healthBonus, getPosition());
			this.cooldown = 10D;
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (cooldown == 0D) { super.draw(input, output); }
	}

	@Override
	public int getPriority() {
		return 50;
	}

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		healthBonus = factory.getDataMap().get("healthBonus").getAsDouble();
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("healthBonus", healthBonus);
	}
}
