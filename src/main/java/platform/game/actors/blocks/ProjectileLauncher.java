package platform.game.actors.blocks;

import platform.game.Signal;
import platform.game.actors.basic.OrientedBlock;
import platform.game.actors.entities.Arrow;
import platform.game.actors.entities.Fireball;
import platform.game.actors.entities.Projectile;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class ProjectileLauncher extends OrientedBlock {
	private final Signal signal;
	private final LaunchPolicy launchPolicy;
	private final ProjectileCreator projectileCreator;
	private boolean lastState = false;
	private double sinceLastTime = 0D;

	public ProjectileLauncher(Vector position, double size, String spriteName, Direction direction, Signal signal, LaunchPolicy launchPolicy, ProjectileCreator projectileCreator) {
		super(position, size, spriteName, direction);
		this.signal = signal;
		this.launchPolicy = launchPolicy;
		this.projectileCreator = projectileCreator;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		sinceLastTime += input.getDeltaTime();

		if (launchPolicy.shouldLaunch(sinceLastTime, lastState, signal.isActive())) {
			sinceLastTime = 0D;
			fire();
		}
	}

	private void fire() {
		Projectile projectile = projectileCreator.createProjectile(this, getPosition().add(getDirection().getUnitVector().div(2)), getDirection().getUnitVector());
		getWorld().register(projectile);
	}

	@Override
	public void postUpdate(Input input) {
		super.postUpdate(input);
		lastState = signal.isActive();
	}

	public interface LaunchPolicy {
		boolean shouldLaunch(double sinceLastLaunch, boolean oldSignal, boolean newSignal);
	}

	public interface ProjectileCreator<T extends Projectile> {
		T createProjectile(ProjectileLauncher launcher, Vector position, Vector launchDirection);
	}

	private static abstract class AbstractProjectileCreator<T extends Projectile> implements ProjectileCreator<T> {
		protected double power = 2D;
		protected double damage = 2D;

		public AbstractProjectileCreator() {
		}

		public AbstractProjectileCreator<T> power(double val) {
			this.power = val;
			return this;
		}

		public AbstractProjectileCreator<T> damage(double val) {
			this.damage = val;
			return this;
		}
	}

	public static final class ArrowCreator extends AbstractProjectileCreator<Arrow> {
		@Override
		public Arrow createProjectile(ProjectileLauncher launcher, Vector position, Vector launchDirection) {
			return new Arrow(position, launchDirection.mul(power), launcher, damage);
		}
	}

	public static final class FireballCreator extends AbstractProjectileCreator<Fireball> {
		@Override
		public Fireball createProjectile(ProjectileLauncher launcher, Vector position, Vector launchDirection) {
			return new Fireball(position, launchDirection.mul(power), launcher, damage);
		}
	}
}
