package platform.game.actors.blocks;

import platform.game.Effect;
import platform.game.actors.basic.InteractableBlock;
import platform.game.actors.basic.MovableActor;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Spikes extends InteractableBlock {
	private double damage = 5D;
	private double cooldown = 0;

	public Spikes(Vector position, double size) {
		this(position, size, Direction.UP, 5D);
	}

	public Spikes(Vector position, double size, Direction direction, double damage) {
		super(position, size, "spikes", direction);
		this.damage = damage;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (cooldown > 0) { cooldown = Math.max(0, cooldown - input.getDeltaTime()); }
	}

	@Override
	protected boolean canBeUsed() {
		return cooldown <= 0;
	}

	@Override
	protected void doInteract(MovableActor other) {
		Vector below = getPosition().sub(getDirection().getUnitVector());
		other.hurt(this, Effect.PHYSICAL, damage, below);
		cooldown = 1D;
	}

	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public boolean isSolid() {
		return false;
	}
}
