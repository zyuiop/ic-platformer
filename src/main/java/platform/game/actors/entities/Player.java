package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.KeyBindings;
import platform.game.KeyBindings.Key;
import platform.game.World;
import platform.game.actors.AttachLink;
import platform.game.actors.Side;
import platform.game.actors.animations.BlowAnimation;
import platform.game.actors.animations.Overlay;
import platform.game.actors.basic.LivingActor;
import platform.game.actors.interfaces.IAttachable;
import platform.game.actors.interfaces.IPositioned;
import platform.game.menus.main.MainMenuLevel;
import platform.game.particles.ParticleEffect;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Player extends LivingActor implements IAttachable {
	private KeyBindings bindings;
	private boolean isColliding = false;
	private int maxAirJumps = 1;
	private int remainingAirJumps = 1;
	private boolean isOnFloor = false;
	private AttachLink attachLink;

	public Player(Vector position, Vector velocity, KeyBindings bindings) {
		super(position, .5, "blocker.happy", velocity, 10);
		this.bindings = bindings;
	}

	public Player(Vector position, Vector velocity, double maxhealth, double health, KeyBindings bindings) {
		super(position, .5, "blocker.happy", velocity, maxhealth, health);
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

				// We call detach because it moves but also frees the player from a moving platform
				detach(new Vector(speed, getVelocity().getY()));
			}
		} else if (bindings.isDown(input, Key.LEFT)) {
			if (getVelocity().getX() > -maxSpeed) {
				double increase = 60.0 * input.getDeltaTime();
				double speed = getVelocity().getX() - increase;
				if (speed < -maxSpeed) {
					speed = -maxSpeed;
				}

				// We call detach because it moves but also frees the player from a moving platform
				detach(new Vector(speed, getVelocity().getY()));
			}
		}

		if (bindings.isPressed(input, Key.JUMP)) {
			if (isOnFloor || remainingAirJumps > 0) {
				// We call detach because it moves but also frees the player from a moving platform
				detach(new Vector(getVelocity().getX(), 5D));

				if (!isOnFloor) {
					remainingAirJumps--;
					ParticleEffect.BLOW.play(getWorld(), getPosition());
				}
			}
		}

		if (bindings.isPressed(input, Key.ATTACK)) {
			Vector fireballSpeed = getVelocity().add(getVelocity().resized(2.0));
			getWorld().register(new Arrow(getPosition(), fireballSpeed, this));
		}

		if (bindings.isPressed(input, Key.BLOW)) {
			getWorld().hurt(getBox(), this, Effect.AIR, 1.0, getPosition());
			getWorld().register(new BlowAnimation(getPosition()));
			ParticleEffect.BLOW.play(getWorld(), getPosition());
		}

		if (bindings.isPressed(input, Key.USE)) {
			getWorld().hurt(getBox(), this, Effect.ACTIVATION, 1.0, getPosition());
		}

		if (bindings.isPressed(input, Key.MENU)) {
			getWorld().setNextLevel(new MainMenuLevel());
			getWorld().nextLevel();
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

				Side direction = Side.compute(delta);

				if (delta.getX() != 0D) {
					setVelocity(new Vector(0D, getVelocity().getY()));
				}

				if (delta.getY() != 0D) {
					setVelocity(new Vector(getVelocity().getX(), 0D));
					if (delta.getY() > 0) {
						this.isOnFloor = true;
						this.remainingAirJumps = this.maxAirJumps;
						if (direction != Side.TOP) {
							System.err.println("Wrong side " + direction + " / " + delta);
						}
					}
				}

				other.onCollide(this, direction);
				this.isColliding = true;
			}
		}
	}

	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
		this.isColliding = false;
		this.isOnFloor = false;
	}

	@Override
	public void postUpdate(Input input) {
		getWorld().setView(getPosition(), 5D);
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
	public void unregister() {
		// save life in level manager
		if (getHealth() > 0D) { getWorld().getLevelManager().setPlayerLife(getHealth()); }

		super.unregister();
	}

	@Override
	public void die() {
		super.die();
		getWorld().setNextLevel(getWorld().getLevelManager().restartGroup());
		getWorld().nextLevel();
	}

	@Override
	public void register(World world) {
		super.register(world);
		world.register(new Overlay(this));
	}

	@Override
	public void attachTo(IPositioned attachedTo, Vector positionDifference) {
		detach(Vector.ZERO); // detach and set velocity
		this.attachLink = new AttachLink(attachedTo, this, positionDifference);
		getWorld().register(attachLink);
	}

	/**
	 * Detach the current actor from it's attach (if it exists), and give it a velocity.
	 *
	 * @param velocity the velocity to set to the actor when detached. In general,
	 * {@link Vector#ZERO} is used as the new velocity parameter.
	 * @implNote This implementation tries to remove the link then it sets the velocity of the
	 * player, even if it was not attached.
	 */
	@Override
	public void detach(Vector velocity) {
		if (attachLink != null) { attachLink.detach(); }
		this.attachLink = null;
		this.setVelocity(velocity);
	}

	@Override
	public boolean isAttached() {
		if (attachLink != null && !attachLink.isRegistered()) {
			detach(Vector.ZERO);
			return false;
		}

		return attachLink != null;
	}
}
