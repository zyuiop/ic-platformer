package platform.game;

import platform.game.level.Level;
import platform.game.level.LevelManager;
import platform.util.Box;
import platform.util.Loader;
import platform.util.sounds.SoundLoader;
import platform.util.Vector;


/**
 * Represents an environment populated by actors.
 */
public interface World {

	/**
	 * @return associated loader, not null
	 */
	Loader getLoader();

	/**
	 * @return associated sound loader, not null
	 */
	SoundLoader getSoundLoader();

	/**
	 * Set viewport location
	 *
	 * @param center viewport center , not null
	 */
	void setView(Vector center);

	/**
	 * Set viewport size
	 * @param radius viewport radius , positive
	 */
	void setViewRadius(double radius);

	/**
	 * Locks the view to the current one. If the view is locked, it cannot be modified.
	 * @param fixedView true if the view should be locked
	 */
	void setFixedView(boolean fixedView);

	/**
	 * Add an actor to the world
	 * @param actor the actor to add
	 */
	void register(Actor actor);

	/**
	 * Removes an actor from the world
	 * @param actor the actor to remove
	 */
	void unregister(Actor actor);

	/**
	 * Return the gravity for this world
	 * @return a vector representing the gravity of this world
	 */
	Vector getGravity();

	/**
	 * Get the {@link LevelManager} associated with this world
	 * @return the level manager used
	 */
	LevelManager getLevelManager();

	/**
	 * Start the previously defined next level.
	 * @see World#setNextLevel(Level)
	 */
	void nextLevel();

	/**
	 * Set the next level. The next level is started when {@link World#nextLevel()} is called
	 * @param level the next level to set
	 */
	void setNextLevel(Level level);

	/**
	 * Apply zone damages
	 * @param area the damage area
	 * @param instigator the actor who gave damage (damager)
	 * @param type the type of damage to apply
	 * @param amount the amount of damage to give
	 * @param location the location of the damager
	 * @return the number of damaged actors
	 */
	int hurt(Box area, Actor instigator, Effect type, double amount, Vector location);
}
