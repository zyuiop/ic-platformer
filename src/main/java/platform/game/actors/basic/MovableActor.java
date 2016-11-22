package platform.game.actors.basic;

import platform.util.*;

/**
 * @author zyuiop
 *
 * This class defines a common base for different actors that can move
 */
public abstract class MovableActor extends PositionedActor {
	private Vector velocity;

	public MovableActor(String spriteName, double size, Vector position, Vector velocity) {
		super(spriteName, size, position);
		this.velocity = velocity;
	}

	public MovableActor(String spriteName, double sizeX, double sizeY, Vector position, Vector velocity) {
		super(spriteName, sizeX, sizeY, position);
		this.velocity = velocity;
	}

	public MovableActor(Sprite sprite, double size, Vector position, Vector velocity) {
		super(sprite, size, position);
		this.velocity = velocity;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		Vector acceleration = getWorld().getGravity();
		setVelocity(getVelocity().add(acceleration.mul(delta)));
		setPosition(getPosition().add(getVelocity().mul(delta)));
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}
}
