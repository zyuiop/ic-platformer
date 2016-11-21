package platform.game;

import platform.game.level.Level;
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
	 * Start the previously defined next level.
	 * @see World#setNextLevel(Level)
	 */
	void nextLevel();

	/**
	 * Set the next level. The next level is started when {@link World#nextLevel()} is called
	 * @param level the next level to set
	 */
	void setNextLevel(Level level);
}
