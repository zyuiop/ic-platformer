package platform.game.actors.environment;

import java.util.Random;
import platform.game.Actor;
import platform.game.Effect;
import platform.game.Signal;
import platform.game.World;
import platform.game.actors.animations.ParticleAnimation;
import platform.game.actors.basic.LivingActor;
import platform.game.actors.blocks.Block;
import platform.game.particles.DisappearingParticleEffect;
import platform.game.particles.ParticleEffect;
import platform.game.particles.ParticleEffectAnimation;
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

	public LaserDoor(Vector center, double length, double angle, String color, Signal listenSignal) {
		super(new Box(center, length, .3), "laser." + color, listenSignal);
		effect = new SimpleParticleEffect("spark." + color).transparency(.8).stay(.2).fadeOut(.3).size(.4);

		this.center = center;
		this.angle = angle;
		this.length = length;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		if (sprite != null && getBox() != null)
			output.drawSprite(sprite, getDisplayBox(time > 1 ? .1 : 0), angle);
	}

	@Override
	public void register(World world) {
		super.register(world);

		Vector unit = Vector.X.rotated(angle);
		world.register(new LaserPower(center.add(unit.mul(length / 2)).add(unit.mul(0.2)), angle + Math.PI));
		world.register(new LaserPower(center.sub(unit.mul(length / 2)).sub(unit.mul(0.2)), angle));
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
				System.out.println(position);
				effect.play(getWorld(), position);
			}

			getWorld().getSoundLoader().getSound("highDown").play();
		} else {
			getWorld().getSoundLoader().getSound("highUp").play();
		}
	}

	private static class LaserPower extends Block {
		private double angle;
		public LaserPower(Vector position, double angle) {
			super("laserDown", .4, position);
			this.angle = angle;
		}

		@Override
		public void draw(Input input, Output output) {
			Sprite sprite = getCurrentSprite();
			if (sprite != null)
				output.drawSprite(sprite, getBox(), angle + Math.PI / 2);
		}
	}

	@Override
	public Box getBox() {
		return super.getBox(); // todo rotate (make rotatable boxes)
	}

	protected Box getDisplayBox(double yAdd) {
		return new Box(getPosition(), sizeX, sizeY + yAdd);
	}

	@Override
	public void onCollide(Actor actor) {
		super.onCollide(actor);

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
