package platform.game.actors.entities;

import platform.game.actors.Actor;
import platform.game.Effect;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Fireball extends Projectile {
	private int bounces = 0;
	private double damage = 1D;
	private double rotation = 0D;

	public Fireball(Vector position, Vector velocity, Actor sender) {
		super("fireball", position, velocity, sender, .4);
	}

	public Fireball(Vector position, Vector velocity, Actor sender, double damage) {
		super("fireball", position, velocity, sender, .4);
		this.damage = damage;
	}

	@Override
	public int getPriority() {
		return 1000;
	}

	@Override
	protected boolean damage(Actor other) {
		if (other.hurt(this, Effect.FIRE, damage, getPosition())) {
			getWorld().unregister(this); // unregister when damage given
			return true;
		}

		return false;
	}

	@Override
	protected void hitBlock(Actor solidActor, Vector delta) {
		// change direction on block hit

		setPosition(getPosition().add(delta));
		setVelocity(getVelocity().mirrored(delta));
		bounces++;
	}

	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public double getOpacity() {
		return 1 - ((double) bounces * 8) / 100;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		rotation = input.getTime();

		if (bounces > 7)
			getWorld().unregister(this);
	}
}
