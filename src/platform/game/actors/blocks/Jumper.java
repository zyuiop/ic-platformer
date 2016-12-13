package platform.game.actors.blocks;

import java.util.function.Function;
import platform.game.Effect;
import platform.game.Direction;
import platform.game.Orientation;
import platform.game.actors.MovableActor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A basic jumper that gives velocity to players walking on it
 */
public class Jumper extends InteractableBlock {
	private double cooldown = 0;
	private final double power;

	public Jumper(Vector position, double sizeX, double sizeY, Direction direction, double power) {
		super(position, sizeX, sizeY, "jumper.normal", direction);

		Function<Box, Box> transformer = (box) -> {
			// the box we get has already been transformed by the rotation
			double size = (direction.getOrientation() == Orientation.HORIZONTAL) ?
					box.getHeight() : box.getWidth();

			// we add a useless zone in the top
			Vector min, max;
			if (direction == Direction.RIGHT || direction == Direction.UP) {
				min = box.getMin();
				max = box.getMin().add(new Vector(size, size));
			} else {
				max = box.getMax();
				min = box.getMax().sub(new Vector(size, size));
			}

			return new Box(min, max);
		};
		this.setBoxTransformer(transformer);

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

}
