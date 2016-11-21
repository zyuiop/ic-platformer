package platform.game.actors.environment;

import platform.game.Effect;
import platform.game.actors.basic.InteractableBlock;
import platform.game.actors.basic.MovableActor;
import platform.game.data.ActorFactory;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Spikes extends InteractableBlock {
	private double damage = 5D;
	private double cooldown = 0;

	protected Spikes() {
	}

	public Spikes(Vector position, double size) {
		this(position, size, Direction.UP, 5D);
	}

	public Spikes(Vector position, double size, Direction direction, double damage) {
		super("spikes", size, position, direction);
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

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		damage = factory.getDataMap().get("damage").getAsDouble();
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("damage", damage);
	}
}
