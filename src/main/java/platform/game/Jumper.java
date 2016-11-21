package platform.game;

import platform.util.*;

/**
 * @author zyuiop
 */
public class Jumper extends Actor {
	private double cooldown;
	private Vector position;
	private double size;

	public Jumper(Vector position, double size) {
		this.position = position;
		this.size = size;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getSprite(getSpriteName());
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

	protected String getSpriteName() {
		if (cooldown > 0)
			return "jumper.extended";
		return "jumper.normal";
	}

	@Override
	public void update(Input input) {
		super.update(input);
		cooldown -= input.getDeltaTime();
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (cooldown <= 0 && getBox().isColliding(other.getBox())) {
			Vector below = new Vector(getPosition().getX(), getPosition().getY() - 1.0);
			if (other.hurt(this, Effect.AIR, 10.0, below))
				cooldown = 0.5;
		}
	}

	@Override
	public int getPriority() {
		return 100;
	}
}
