package platform.game.actors.basic;

import platform.game.Actor;
import platform.game.Effect;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 * An actor which has a life and which can loose it.
 */
public abstract class LivingActor extends MovableActor {
	private double maxHealth;
	private double health;
	private double invulnerability = 0D;

	public LivingActor(String spriteName, double size, Vector position, Vector velocity, double maxHealth) {
		super(spriteName, size, position, velocity);
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}

	public LivingActor(String spriteName, double size, Vector position, Vector velocity, double maxHealth, double health) {
		super(spriteName, size, position, velocity);
		this.maxHealth = maxHealth;
		this.health = health;
	}

	public LivingActor(Sprite sprite, double size, Vector position, Vector velocity, double maxHealth) {
		super(sprite, size, position, velocity);
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}


	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = Math.min(health, maxHealth);
	}

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
		if (!isVulnerable())
			this.invulnerability = Math.max(0, invulnerability - input.getDeltaTime());
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (amount > 0 && (damageType.isHealing() || damageType.isHarming())) {
			if (damageType.isHealing())
				amount = -amount; // heal : we add the damage instead of removing it
			else if (!isVulnerable()) {
				return false; // prend pas les dégats
			} else {
				invulnerability = 2D;
			}
			setHealth(getHealth() - amount);
			return true;
		} else
			return super.hurt(damageFrom, damageType, amount, location);
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public boolean isDead() {
		return getHealth() <= 0;
	}

	public boolean isVulnerable() {
		return invulnerability <= 0;
	}
}
