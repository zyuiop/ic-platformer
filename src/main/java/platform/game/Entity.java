package platform.game;

import platform.util.*;

/**
 * @author zyuiop
 *
 * This class defines a common base for different actors that can move
 */
public abstract class Entity extends Actor {
	private Vector position;
	private Vector velocity;
	private final String spriteName;
	private final double size;

	public Entity(Vector position, Vector velocity, String spriteName, double size) {
		this.position = position;
		this.velocity = velocity;
		this.spriteName = spriteName;
		this.size = size;
	}

	protected String getSpriteName() {
		return spriteName;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getSprite();
		if (sprite != null)
			output.drawSprite(sprite, getBox());
	}

	@Override
	public Vector getPosition() {
		return position;
	}

	@Override
	public Box getBox() {
		return new Box(position, size, size);
	}

	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		Vector acceleration = getWorld().getGravity();
		setVelocity(getVelocity().add(acceleration.mul(delta)));
		setPosition(getPosition().add(getVelocity().mul(delta)));
	}

	protected void setPosition(Vector position) {
		this.position = position;
	}

	protected Vector getVelocity() {
		return velocity;
	}

	protected void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	protected Sprite getSprite() {
		return getSprite(spriteName);
	}

	protected double getSize() {
		return size;
	}
}
