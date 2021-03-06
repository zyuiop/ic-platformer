package platform.game;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import platform.game.actors.Actor;
import platform.game.level.Level;
import platform.game.level.LevelManager;
import platform.game.level.PlayableLevel;
import platform.util.Box;
import platform.util.Input;
import platform.util.Loader;
import platform.util.Output;
import platform.util.SortedCollection;
import platform.util.Vector;
import platform.util.View;

/**
 * Basic implementation of world, managing a complete collection of actors.
 */
public class Simulator implements World {
	private final SortedCollection<Actor> actors = new SortedCollection<>();
	private final List<Actor> toAdd = new ArrayList<>();
	private final List<Actor> toRemove = new ArrayList<>();
	private final Loader loader;
	private final LevelManager levelManager = LevelManager.init();
	private Level nextLevel = Level.createDefaultLevel();
	private Level currentLevel = null;
	private boolean passLevel = true; // when true, the level must be changed
	private Vector center = Vector.ZERO;
	private Vector expectedCenter = Vector.ZERO;
	private double radius = 10D;
	private double expectedRadius = 10D;
	private boolean isRaw = false;
	private boolean isViewFixed = false;

	/**
	 * Create a new simulator.
	 *
	 * @param loader associated loader, not null
	 * @param args level arguments, not null
	 */
	public Simulator(Loader loader, String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		if (loader == null) { throw new NullPointerException(); }
		this.loader = loader;
		this.center = Vector.ZERO;

		if (args.length > 0) {
			// argument is classpath
			Class<?> clazz = Class.forName(args[0]);
			if (Level.class.isAssignableFrom(clazz)) {
				setNextLevel((Level) clazz.newInstance());
			} else {
				throw new IllegalArgumentException("given class is not a level");
			}
		}
	}

	/**
	 * Simulate a single step of the simulation.
	 *
	 * @param input input object to use, not null
	 * @param output output object to use, not null
	 */
	public void update(Input input, Output output) {
		double factor = 0.1;
		center = center.mul(1.0 - factor).add(expectedCenter.mul(factor));
		radius = radius * (1.0 - factor) + expectedRadius * factor;

		View view = new View(input, output);
		if (!isRaw) { view.setTarget(center, radius); }

		// Compute pre-updates
		actors.descending().forEach(actor -> actor.preUpdate(view));

		// Compute interactions
		actors.forEach(actor ->
				actors.stream()
						.filter(other -> actor.getPriority() > other.getPriority()) // filter by priority
						.forEach(actor::interact)
		);

		// Compute updates
		actors.descending().forEach(actor -> actor.update(view));

		// Draw everything
		actors.descending().forEach(actor -> actor.draw(view, view));

		// Compute post-updates
		actors.descending().forEach(actor -> actor.postUpdate(view));

		// Now we add/remove :
		toAdd.stream().filter(actor -> !actors.contains(actor)).forEach(actors::add);
		toAdd.clear();

		toRemove.forEach(actors::remove);
		toRemove.forEach(Actor::unregister);
		toRemove.clear();

		// And finally we check if we have to go to the add level
		if (passLevel) { passLevel(); }
	}

	public int countActors() {
		return actors.size();
	}

	public boolean isRaw() {
		return isRaw;
	}

	public void setRaw(boolean raw) {
		isRaw = raw;
	}

	@Override
	public Level getCurrentLevel() {
		return currentLevel;
	}

	private void passLevel() {
		if (nextLevel == null) {
			if (currentLevel == null || !(currentLevel instanceof PlayableLevel)) {
				nextLevel = Level.createDefaultLevel();
			} else {
				// Auto switch to next level
				nextLevel = levelManager.getNextLevel((PlayableLevel) currentLevel);
			}
		}

		// Reset the state
		Level level = nextLevel;
		passLevel = false;
		nextLevel = null;
		actors.forEach(Actor::unregister);
		actors.clear();
		toAdd.clear();
		toRemove.clear();
		isRaw = false;
		center = Vector.ZERO;
		expectedCenter = Vector.ZERO;
		isViewFixed = false;
		setViewRadius(5D); // default

		// register the new level
		currentLevel = level;
		register(level);
	}

	@Override
	public Loader getLoader() {
		return loader;
	}

	@Override
	public void setView(Vector center) {
		if (center == null) { throw new NullPointerException(); }
		if (isViewFixed) { return; }


		Logger.getGlobal().fine("Changed view to center " + center.getX() + "," + center.getY());
		this.expectedCenter = center;
	}

	@Override
	public void setViewRadius(double radius) {
		if (radius <= 0D) {
			throw new IllegalArgumentException("radius must be strictly positive");
		}
		if (isViewFixed) { return; }

		this.expectedRadius = radius;
	}

	@Override
	public void setFixedView(boolean fixedView) {
		this.isViewFixed = fixedView;
	}

	public void register(Actor actor) {
		toAdd.add(actor);
		actor.register(this);
	}

	public void unregister(Actor actor) {
		toRemove.add(actor);
	}

	@Override
	public Vector getGravity() {
		return new Vector(0, -9.81);
	}

	@Override
	public void nextLevel() {
		this.passLevel = true;
	}

	@Override
	public void setNextLevel(Level level) {
		this.nextLevel = level;
	}

	@Override
	public LevelManager getLevelManager() {
		return levelManager;
	}

	@Override
	public int hurt(Box area, Actor instigator, Effect type, double amount, Vector location) {
		int victims = 0;
		for (Actor actor : actors) {
			if (area.isColliding(actor.getBox())) {
				if (actor.hurt(instigator, type, amount, location)) { ++victims; }
			}
		}
		return victims;
	}
}
