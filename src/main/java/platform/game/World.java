package platform.game;

import platform.game.level.Level;
import platform.util.Box;
import platform.util.Loader;
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
	 * Set viewport location and size.
	 *
	 * @param center viewport center , not null
	 * @param radius viewport radius , positive
	 */
	void setView(Vector center, double radius);

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
	 * Start the previously defined add level.
	 * @see World#setNextLevel(Level)
	 */
	void nextLevel();

	/**
	 * Set the add level. The add level is started when {@link World#nextLevel()} is called
	 * @param level the add level to set
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
