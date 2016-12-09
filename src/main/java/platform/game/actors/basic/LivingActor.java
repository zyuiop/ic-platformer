package platform.game.actors.basic;

import platform.game.Actor;
import platform.game.Effect;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 * An actor which has a life and which can loose it.
 */
public abstract class LivingActor extends MovableActor {
	private final double maxHealth;
	private double health;
	private double invulnerability = 0D;
	private double blink = 0D;

	public LivingActor(Box box, String spriteName, Vector velocity, double maxHealth) {
		super(box, spriteName, velocity);
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}

	public LivingActor(Vector position, double size, String spriteName, Vector velocity, double maxHealth) {
		super(position, size, spriteName, velocity);
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}

	public LivingActor(Vector position, double size, String spriteName, Vector velocity, double maxHealth, double health) {
		super(position, size, spriteName, velocity);
		this.maxHealth = maxHealth;
		this.health = health;
	}

	/**
	 * Get the current health of the actor
	 * @return the health of the actor
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * Changes the health of the actor
	 * @param health the new health
	 */
	public void setHealth(double health) {
		this.health = Math.min(health, maxHealth);
	}

	/**
	 * Kill the actor
	 */
	public void die() {
		getWorld().unregister(this);
	}

	@Override
	public void postUpdate(Input input) {
		super.postUpdate(input);

		if (isDead())
			die();
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (isInvulnerable()) {
			blink += input.getDeltaTime();
			this.invulnerability = Math.max(0, invulnerability - input.getDeltaTime());

			if (blink > .08)
				blink = 0;
		} else if (blink > 0) {
			blink = 0;
		}
	}

	@Override
	public void draw(Input input, Output output) {
		if (blink > .05)
			return;
		super.draw(input, output);
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (amount > 0 && (damageType.isHealing() || damageType.isHarming())) {
			if (damageType.isHealing()) {
				amount = -amount; // heal : we add the damage instead of removing it

				if (this.health >= this.getMaxHealth())
					return false; // cannot be healed at it's full
			} else if (isInvulnerable()) {
				return false; // prend pas les dégats
			} else {
				invulnerability = 1.5D;
			}
			setHealth(getHealth() - amount);
			return true;
		} else
			return super.hurt(damageFrom, damageType, amount, location);
	}

	/**
	 * Get the maximum health of this actor
	 * @return the max health
	 */
	public double getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Check if this actor is dead, i.e. if its health is lower than 0
	 * @return true if the actor is dead
	 */
	public boolean isDead() {
		return getHealth() <= 0;
	}

	/**
	 * Check if this actor is invulnerable
	 * An invulnerable actor cannot take any damage
	 * @return true if this actor is invulnerable and cannot be damaged
	 */
	public boolean isInvulnerable() {
		return invulnerability > 0;
	}
}
