package platform.game;

import platform.util.*;

/**
 * Basic implementation of world, managing a complete collection of actors.
 */
public class Simulator implements World {

	private Loader loader;

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

	}

	/**
	 * Simulate a single step of the simulation.
	 *
	 * @param input  input object to use, not null
	 * @param output output object to use, not null
	 */
	public void update(Input input, Output output) {
		Sprite sprite = loader.getSprite("heart.full");
		Box zone = new Box(new Vector(100.0, 100.0), 32, 32);
		output.drawSprite(sprite, zone);
	}

	@Override
	public Loader getLoader() {
		return loader;
	}


}
