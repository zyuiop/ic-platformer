package platform.game.actors;

import platform.game.Effect;
import platform.game.actors.basic.InteractableBlock;
import platform.game.actors.basic.MovableActor;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Jumper extends InteractableBlock {
	private double cooldown = 0;
	private double power;

	public Jumper(Vector position, double size) {
		this(position, size, Direction.UP, 10D);
	}

	private double getHitboxRatio() {
		if (cooldown >= 0)
			return .5;
		return .4;
	}

	@Override
	protected double getHitboxXRatio() {
		if (getDirection() == Direction.LEFT || getDirection() == Direction.RIGHT)
			return getHitboxRatio();
		return 1;
	}

	@Override
	protected double getHitboxYRatio() {
		if (getDirection() == Direction.UP || getDirection() == Direction.DOWN)
			return getHitboxRatio();
		return 1;
	}

	public Jumper(Vector position, double size, Direction direction, double power) {
		super("jumper.normal", size, position, direction);
		this.power = power;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (cooldown > 0) { cooldown = Math.max(0, cooldown - input.getDeltaTime()); }

		if (cooldown == 0 && !getSpriteName().equals("jumper.normal")) {
			setSpriteName("jumper.normal");
		}
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	protected boolean canBeUsed() {
		return cooldown <= 0;
	}

	@Override
	protected void doInteract(MovableActor other) {
		Vector below = getPosition().sub(getDirection().getUnitVector());
		if (other.hurt(this, Effect.AIR, power, below)) {
			cooldown = 0.5;
			setSpriteName("jumper.extended");
		}
	}

	@Override
	public int getPriority() {
		return 100;
	}

}
