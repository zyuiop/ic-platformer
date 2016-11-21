package platform.game.actors.environment;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.Signal;
import platform.game.actors.basic.PositionedActor;
import platform.game.actors.entities.Player;
import platform.game.data.ActorFactory;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Lever extends PositionedActor implements Signal {
	private boolean active = false;
	private double duration = 30D;
	private double time;

	public Lever(Vector position, double size, double duration) {
		this(position, size, duration, false);
	}

	public Lever(Vector position, double size, double duration, boolean active) {
		super("lever.left", size, position);
		this.duration = duration;
		setActive(active);
	}

	@Override
	public int getPriority() {
		return 30;
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (damageType == Effect.ACTIVATION) {
			setActive(!active);
			return true;
		}
		return super.hurt(damageFrom, damageType, amount, location);
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (time > 0) {
			time = Math.max(time - input.getDeltaTime(), 0);
			if (time == 0)
				setActive(false);
		}
	}

	private void setActive(boolean state) {
		this.active = state;
		if (this.active) {
			setSpriteName("lever.left");
			time = duration;
		} else
			setSpriteName("lever.right");
	}

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		active = factory.getDataMap().get("active").getAsInt() == 1;
		duration = factory.getDataMap().get("duration").getAsDouble();
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("active", active ? 1 : 0);
		factory.getDataMap().put("duration", duration);
	}
}
