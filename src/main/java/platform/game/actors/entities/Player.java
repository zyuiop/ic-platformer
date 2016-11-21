package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.World;
import platform.game.actors.animations.BlowAnimation;
import platform.game.actors.animations.Overlay;
import platform.game.actors.basic.LivingActor;
import platform.game.settings.KeyBindings;
import platform.game.settings.KeyBindings.Key;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Player extends LivingActor {
	private KeyBindings bindings;
	private boolean isColliding = false;

	public Player(Vector position, Vector velocity, KeyBindings bindings) {
		super("blocker.happy", .5, position, velocity, 10);
		this.bindings = bindings;
	}

	public Player(Vector position, Vector velocity, double maxhealth, double health, KeyBindings bindings) {
		super("blocker.happy", .5, position, velocity, maxhealth, health);
		this.bindings = bindings;
	}

	@Override
	public int getPriority() {
		return 42;
	}

	@Override
	public void update(Input input) {
		if (isColliding) {
			double scale = Math.pow(0.001, input.getDeltaTime()); // frottements
			setVelocity(getVelocity().mul(scale));
		}

		double maxSpeed = 4.0;
		if (bindings.isDown(input, Key.RIGHT)) {
			if (getVelocity().getX() < maxSpeed) {
				double increase = 60.0 * input.getDeltaTime();
				double speed = getVelocity().getX() + increase;
				if (speed > maxSpeed) {
					speed = maxSpeed;
				}
				setVelocity(new Vector(speed, getVelocity().getY()));
			}
		} else if (bindings.isDown(input, Key.LEFT)) {
			if (getVelocity().getX() > -maxSpeed) {
				double increase = 60.0 * input.getDeltaTime();
				double speed = getVelocity().getX() - increase;
				if (speed < -maxSpeed) {
					speed = -maxSpeed;
				}
				setVelocity(new Vector(speed, getVelocity().getY()));
			}
		}

		if (bindings.isPressed(input, Key.UP) && isColliding) {
			setVelocity(new Vector(getVelocity().getX(), 7D));
		}

		if (bindings.isPressed(input, Key.ATTACK)) {
			Vector fireballSpeed = getVelocity().add(getVelocity().resized(2.0));
			getWorld().register(new Fireball(getPosition(), fireballSpeed, this));
		}

		if (bindings.isPressed(input, Key.BLOW)) {
			getWorld().hurt(getBox(), this, Effect.AIR, 1.0, getPosition());
			getWorld().register(new BlowAnimation(getPosition()));
		}

		if (bindings.isPressed(input, Key.USE)) {
			getWorld().hurt(getBox(), this, Effect.ACTIVATION, 1.0, getPosition());
		}

		super.update(input);
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other.isSolid()) {
			Vector delta = other.getBox().getCollision(getBox());
			if (delta != null) {
				setPosition(getPosition().add(delta));

				if (delta.getX() != 0D) {
					setVelocity(new Vector(0D, getVelocity().getY()));
				}
				if (delta.getY() != 0D) {
					setVelocity(new Vector(getVelocity().getX(), 0D));
				}

				this.isColliding = true;
			}
		}
	}

	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
		this.isColliding = false;
	}

	@Override
	public void postUpdate(Input input) {
		getWorld().setView(getPosition(), 8D);
		super.postUpdate(input);
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		switch (damageType) {
			case AIR:
				setVelocity(getPosition().sub(location).resized(amount));
				return true;
			default:
				return super.hurt(damageFrom, damageType, amount, location);
		}
	}

	@Override
	public void die() {
		super.die();
		getWorld().nextLevel();
	}

	@Override
	public void register(World world) {
		super.register(world);
		world.register(new Overlay(this));
	}
}
