package platform.game.actors.basic;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.data.ActorFactory;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 *         An actor which has a life and which can loose it.
 */
public abstract class LivingActor extends MovableActor {
	private double maxHealth;
	private double health;

	protected LivingActor() {
	}

	public LivingActor(String spriteName, double size, Vector position, Vector velocity, double maxHealth) {
		super(spriteName, size, position, velocity);
		this.maxHealth = maxHealth;
		this.health = maxHealth;
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

		if (isDead()) { die(); }
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (amount > 0 && (damageType.isHealing() || damageType.isHarming())) {
			if (damageType.isHealing()) {
				amount = -amount; // heal : we add the damage instead of removing it
			}
			setHealth(getHealth() - amount);
			return true;
		} else { return super.hurt(damageFrom, damageType, amount, location); }
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public boolean isDead() {
		return getHealth() <= 0;
	}

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		maxHealth = factory.getDataMap().get("maxHealth").getAsDouble();
		health = factory.getDataMap().get("health").getAsDouble();
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("maxHealth", maxHealth);
		factory.getDataMap().put("health", health);
	}
}