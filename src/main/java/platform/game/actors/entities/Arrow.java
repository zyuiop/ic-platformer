package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.actors.interfaces.IPositioned;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Arrow extends AttachableProjectile {
	private double damage = 2D;
	private double baseAttachTime = 45D;
	private double attachTime = 45D; // expiration time
	private double blink = 0D;

	public Arrow(Vector position, Vector velocity, Actor sender, double damage) {
		super("arrow", position, velocity, sender, .75, 0.1725);
		this.damage = damage;
	}

	public Arrow(Vector position, Vector velocity, Actor sender, double damage, double attachTime) {
		super("arrow", position, velocity, sender, .75, 0.1725);
		this.damage = damage;
		this.baseAttachTime = attachTime;
		this.attachTime = attachTime;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (isAttached()) {
			attachTime -= input.getDeltaTime();

			if (attachTime < 3) {
				if (attachTime <= 0) {
					getWorld().unregister(this);
				} else {
					blink += input.getDeltaTime();
					// 3 => 0.3 sec
					// 2 => 0.2 sec
					// 1 => 0.1 sec
					// Multiply by two : show then hide
					if (blink > attachTime * .2)
						blink = 0;
				}
			}
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (isAttached()) {
			if (blink > attachTime * .1)
				return;
		}
		super.draw(input, output);
	}

	@Override
	public void attachTo(IPositioned attachedTo, Vector positionDifference, double attachAngle) {
		super.attachTo(attachedTo, positionDifference, attachAngle);
		attachTime = baseAttachTime; // reset attach
		blink = 0D;
	}

	@Override
	protected boolean damage(Actor other) {
		if (other.hurt(this, Effect.PHYSICAL, damage, getPosition())) {
			if (other.getPosition() != null) {
				Vector diff = getPosition().sub(other.getPosition());
				if (other instanceof IPositioned)
				attachTo((IPositioned) other, diff, getVelocity().getAngle());
			}
			return true;
		}

		return false;
	}

	@Override
	protected void hitBlock(Actor solidActor, Vector delta) {
		Vector diff = getPosition().sub(solidActor.getPosition());
		if (solidActor instanceof IPositioned)
			attachTo((IPositioned) solidActor, diff, getVelocity().getAngle());
	}
}
