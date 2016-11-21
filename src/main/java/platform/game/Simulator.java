package platform.game;

import platform.game.level.Level;
import platform.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Basic implementation of world, managing a complete collection of actors.
 */
public class Simulator implements World {
	private Level nextLevel = Level.createDefaultLevel();
	private boolean passLevel = true; // when true, the level must be changed
	private final SortedCollection<Actor> actors = new SortedCollection<>();
	private final List<Actor> toAdd = new ArrayList<>();
	private final List<Actor> toRemove = new ArrayList<>();
	private final Loader loader;
	private Vector center = Vector.ZERO;
	private Vector expectedCenter = Vector.ZERO;
	private double radius = 10D;
	private double expectedRadius = 10D;


	/**
	 * Create a new simulator.
	 *
	 * @param loader associated loader, not null
	 * @param args   level arguments, not null
	 */
	public Simulator(Loader loader, String[] args) {
		if (loader == null)
			throw new NullPointerException();
		this.loader = loader;
		this.center = Vector.ZERO;
	}

	/**
	 * Simulate a single step of the simulation.
	 *
	 * @param input  input object to use, not null
	 * @param output output object to use, not null
	 */
	public void update(Input input, Output output) {
		double factor = 0.001;
		center = center.mul(1.0 - factor).add(expectedCenter.mul(factor));
		radius = radius * (1.0 - factor) + expectedRadius * factor;

		View view = new View(input, output);
		view.setTarget(center, radius);

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
		toRemove.clear();

		// And finally we check if we have to go to the next level
		if (passLevel)
			passLevel();
	}

	private void passLevel() {
		if (nextLevel == null)
			nextLevel = Level.createDefaultLevel();

		// Reset the state
		Level level = nextLevel;
		passLevel = false;
		nextLevel = null;
		actors.clear();
		toAdd.clear();
		toRemove.clear();

		// register the new level
		register(level);
	}

	@Override
	public Loader getLoader() {
		return loader;
	}

	@Override
	public void setView(Vector center, double radius) {
		if (center == null)
			throw new NullPointerException();
		if (radius <= 0D)
			throw new IllegalArgumentException("radius must be strictly positive");

		Logger.getGlobal().fine("Changed view to center " + center.getX() + "," + center.getY() + " and radius " + radius);
		this.expectedCenter = center;
		this.expectedRadius = radius;
	}

	public void register(Actor actor) {
		toAdd.add(actor);
		actor.register(this);
	}

	public void unregister(Actor actor) {
		toRemove.add(actor);
		actor.unregister();
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
}
