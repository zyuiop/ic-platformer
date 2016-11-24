package platform.game.actors.environment;

import java.util.Random;
import platform.game.Actor;
import platform.game.Effect;
import platform.game.Signal;
import platform.game.World;
import platform.game.actors.Orientation;
import platform.game.actors.Side;
import platform.game.actors.basic.OrientedBlock;
import platform.game.actors.blocks.Block;
import platform.game.particles.ParticleEffect;
import platform.game.particles.SimpleParticleEffect;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class LaserDoor extends Door {
	private double angle;
	private double length;
	private Vector center;
	private double time = 0D;
	private ParticleEffect effect;

	public LaserDoor(Vector center, double length, Orientation direction, String color, Signal listenSignal) {
		super(new Box(center, length, .3), "laser." + color, listenSignal);
		effect = new SimpleParticleEffect("spark." + color).transparency(.8).stay(.2).fadeOut(.3).size(.4);

		this.center = center;
		this.angle = direction.getAngle(Orientation.HORIZONTAL);
		this.length = length;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		Box box = getDisplayBox(time > 1 ? .1 : 0);
		if (sprite != null && box != null)
			output.drawSprite(sprite, box, angle);
	}

	@Override
	public void register(World world) {
		super.register(world);

		Vector unit = Vector.X.rotated(angle);
		world.register(new LaserPower(center.add(unit.mul(length / 2)).add(unit.mul(0.2)), angle - Math.PI / 2));
		world.register(new LaserPower(center.sub(unit.mul(length / 2)).sub(unit.mul(0.2)), angle + Math.PI / 2));
	}

	@Override
	public void onChangeState(boolean newState) {
		if (newState) {
			Random random = new Random();
			Vector unit = Vector.X.rotated(angle);
			Vector norm = Vector.Y.rotated(angle);

			for (double i = -(length / 2); i < (length / 2); ++i) {
				double diff = (random.nextDouble() - .5) / 2;

				Vector position = center.add(unit.mul(i + .5)).add(norm.mul(diff));
				effect.play(getWorld(), position);
			}

			getWorld().getSoundLoader().getSound("highDown").play();
		} else {
			getWorld().getSoundLoader().getSound("highUp").play();
		}
	}

	private static class LaserPower extends Block {
		private double angle;

		LaserPower(Vector position, double angle) {
			super(position, .4, "laserdown");
			this.angle = angle;
		}

		@Override
		public double getRotation() {
			return angle;
		}

		@Override
		public int getPriority() {
			return 1; // on the front of the blocks please !
		}
	}

	@Override
	public int getPriority() {
		return 1; // on the front of the blocks please !
	}

	@Override
	public Box getBox() {
		if (super.getPosition() == null)
			return null;
		return new Box(getPosition(), sizeX * Math.cos(angle) + sizeY * Math.sin(angle), sizeY * Math.cos(angle) + sizeX * Math.sin(angle));
	}

	protected Box getDisplayBox(double yAdd) {
		if (super.getPosition() == null)
			return null;
		return new Box(getPosition(), sizeX, sizeY + yAdd);
	}

	@Override
	public void onCollide(Actor actor, Side side) {
		super.onCollide(actor, side);

		actor.hurt(this, Effect.LASER, 2D, getPosition());
	}

	@Override
	public void update(Input input) {
		super.update(input);

		time += input.getDeltaTime();
		if (time > 2)
			time = 0D;
	}

}
