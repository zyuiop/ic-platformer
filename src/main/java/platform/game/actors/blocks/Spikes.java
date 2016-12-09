package platform.game.actors.blocks;

import platform.game.Effect;
import platform.game.Direction;
import platform.game.actors.MovableActor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Spikes extends InteractableBlock {
	private double damage = 5D;
	private double cooldown = 0;

	// current system :
	// - solid block
	// - half hitbox
	// - damage on collide with hitbox
	// TODO : improve (use the old velocity system ?) (major drawback of current system : horizontal are easier to avoid)

	public Spikes(Vector position, double size) {
		this(position, size, Direction.UP, 5D);
	}

	public Spikes(Vector position, double size, Direction direction, double damage) {
		super(position.add(new Vector(0, -size / 4)), size, size / 2, "spikes", direction);
		this.damage = damage;

		this.setBoxTransformer((box) -> {
			// we add a useless zone in the top
			Vector max = box.getMin().add(new Vector(size, size));
			return new Box(box.getMin(), max);
		});
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
	public boolean isSolid() {
		return true;
	}
}
