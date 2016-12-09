package platform.game.actors.blocks;

import platform.game.Signal;
import platform.game.Direction;
import platform.game.actors.entities.Arrow;
import platform.game.actors.entities.Fireball;
import platform.game.actors.entities.Projectile;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A block that can launch {@link Projectile}
 */
public class ProjectileLauncher extends Block {
	private final Signal signal;
	private final LaunchPolicy launchPolicy;
	private final ProjectileCreator projectileCreator;
	private boolean lastState = false;
	private double sinceLastTime = 0D;

	/**
	 * Create a projectile launcher
	 * @param position The center of the launcher
	 * @param size the size of the launcher
	 * @param spriteName the sprite of the launcher
	 * @param direction the direction which the launcher will be facing. The sprite will be rotated
	 * accordingly and the projectiles will be launched in that direction. See {@link Block} for
	 * more details.
	 * @param signal the signal to listen to
	 * @param launchPolicy a function called at every update of this block. {@link LaunchPolicy}
	 * @param projectileCreator a function called to create a projectile when needed {@link ProjectileCreator}
	 */
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

	/**
	 * An functional interface representing a launch policy that is called at each update of the block
	 */
	@FunctionalInterface
	public interface LaunchPolicy {
		/**
		 * Check if the block should launch a projectile
		 * @param sinceLastLaunch the time (in seconds) since the last projectile launch
		 * @param oldSignal the old value of the signal
		 * @param newSignal the new (current) value of the signal
		 * @return a boolean, true if the block should launch a projectile
		 */
		boolean shouldLaunch(double sinceLastLaunch, boolean oldSignal, boolean newSignal);
	}

	/**
	 * A functional interface creating projectiles using a position and a direction
	 * @param <T> the type of projectile to launch
	 */
	@FunctionalInterface
	public interface ProjectileCreator<T extends Projectile> {
		/**
		 * Create a projectile (without registering it !)
		 * @param launcher the launcher creating the projectile
		 * @param position the position of the new projectile
		 * @param launchDirection the direction in which the projectile has to be directed
		 * @return the newly created projectile
		 */
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
