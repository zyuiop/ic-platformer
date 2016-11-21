package platform.game.actors.basic;

import platform.game.data.ActorFactory;
import platform.util.Input;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         This class defines a common base for different actors that can move
 */
public abstract class MovableActor extends PositionedActor {
	private Vector velocity;

	protected MovableActor() {
	}

	public MovableActor(String spriteName, double size, Vector position, Vector velocity) {
		super(spriteName, size, position);
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

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		velocity = factory.getDataMap().get("velocity").getAsVector();
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("velocity", velocity);
	}
}
