package platform.game;

import platform.game.actors.interfaces.IActor;
import platform.util.*;

/**
 * Base class of all simulated actors, attached to a world.
 */
public abstract class Actor implements Comparable<Actor>, IActor {
	private World world;

	/**
	 * Change actor characteristics based on input. This method is called before interactions computation.
	 *
	 * @param input the current input state
	 */
	public void preUpdate(Input input) {
	}

	/**
	 * Change actor characteristics based on input. This method is called after interactions computation but before
	 * {@link Actor#draw(Input, Output)}
	 *
	 * @param input the current input state
	 */
	public void update(Input input) {
	}

	/**
	 * Change actor characteristics based on input. This method is called after all Actors have been drawn.
	 *
	 * @param input the current input state
	 */
	public void postUpdate(Input input) {
	}

	/**
	 * Draw this actor
	 *
	 * @param input  current input state
	 * @param output current output
	 */
	public void draw(Input input, Output output) {
	}

	/**
	 * Interact with an other actor. This method is called when this actor interacts with an other. The other actor must
	 * have a lower priority than this one. Two actors with same priority cannot interact.
	 *
	 * @param other the actor to interact with
	 */
	public void interact(Actor other) {
	}

	public abstract int getPriority();

	@Override
	public int compareTo(Actor actor) {
		if (actor.getPriority() == getPriority()) {
			return 0;
		}

		return actor.getPriority() > getPriority() ? 1 : -1;
	}

	/**
	 * Check if this actor is solid or not. By default an actor is not solid.
	 *
	 * @return true if this actor is solid, false if it's not
	 */
	public boolean isSolid() {
		return false;
	}

	/**
	 * Get the collision box of this actor
	 *
	 * @return the collision box of this actor, or null
	 */
	public Box getBox() {
		return null;
	}

	/**
	 * Get the position of this actor. In general, the position is the middle of the actor's box.
	 *
	 * @return the position, or null
	 */
	public Vector getPosition() {
		Box box = getBox();
		return box == null ? null : box.getCenter();
	}

	/**
	 * Mark this actor as belonging to a given world
	 *
	 * @param world the world this actor is registered in
	 */
	public void register(World world) {
		this.world = world;
	}

	/**
	 * Mark this actor as belonging to no world
	 */
	public void unregister() {
		this.world = null;
	}

	/**
	 * Get the current world. It can be null if the entity is not registered in any world.
	 *
	 * @return the current world of this entity, or null
	 */
	protected World getWorld() {
		return world;
	}

	/**
	 * Gets a sprite from the world loader
	 * @param name the sprite to get
	 * @return the loaded sprite, or null if the Actor is not attached to a world or if the sprite doesn't exist
	 * @see Loader#getSprite(String)
	 */
	protected Sprite getSprite(String name) {
		return getWorld() != null ? getWorld().getLoader().getSprite(name) : null;
	}

	/**
	 * React to a damage or action
	 * @param damageFrom the actor who gave the damage
	 * @param damageType the type of damage given
	 * @param amount the amount of damage given
	 * @param location the location of the damaging actor
	 * @return true if the damage haad an effect on this actor
	 */
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		return false;
	}

	public boolean isRegistered() {
		return getWorld() != null;
	}


	/**
	 * This method is called if the current actor is solid and an actor with an higher priority
	 * level touches it.
	 * @param actor the actor that touched this actor
	 */
	public void onCollide(Actor actor) {

	}
}