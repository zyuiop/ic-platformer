package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.Effect;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Fireball extends Projectile {
	private int bounces = 0;

	public Fireball(Vector position, Vector velocity, Actor sender) {
		this(position, velocity, sender, .4);
	}

	public Fireball(Vector position, Vector velocity, Actor sender, double size) {
		super("fireball", position, velocity, sender, size);
	}

	@Override
	public int getPriority() {
		return 1000;
	}

	@Override
	protected boolean damage(Actor other) {
		if (other.hurt(this, Effect.FIRE, 1D, getPosition())) {
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
	public void draw(Input input, Output output) {
		if (getCurrentSprite() != null)
			output.drawSprite(getCurrentSprite(), getBox(), input.getTime(), 1 - ((double) bounces * 8) / 100);
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (bounces > 7)
			getWorld().unregister(this);
	}
}
