package platform.game.actors.entities;

import platform.game.Actor;
import platform.game.Effect;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Arrow extends AtachableProjectible {
	public Arrow(Vector position, Vector velocity, Actor sender) {
		super("arrow", position, velocity, sender, 1, 0.25);
	}

	@Override
	protected boolean damage(Actor other) {
		if (other.hurt(this, Effect.PHYSICAL, 2D, getPosition())) {
			Vector delta = other.getBox().getCollision(getPosition());
			attachTo(other, delta == null ? Vector.ZERO : delta);

			return true;
		}

		return false;
	}

	@Override
	protected void hitBlock(Actor solidActor, Vector delta) {
		/*Vector center = solidActor.getPosition();
		// angle :
		double angle = getVelocity().getAngle();
		double cos = Math.abs(Math.cos(angle));
		double sin = Math.abs(Math.sin(angle));

		// center + (cos(angle), sin(angle))*magnitude
		// magnitude :
		double magnitude = getPosition().distance(solidActor.getPosition());

		Vector diff = center.add(new Vector(cos, sin).mul(magnitude));
		// Vector diff = Vector.Y.rotated(getVelocity().getAngle());
		// diff = diff.add(getPosition().sub(solidActor.getPosition()));

		System.out.println(getPosition() + " : " + solidActor.getPosition() + " ==> " + diff + ", " + angle + ", " + cos + ", " + sin + ", " + magnitude);
		*/

		Vector diff = getPosition().sub(solidActor.getPosition());
		System.out.println(solidActor.getPosition() + " " + getPosition() + " => " + diff);
		attachTo(solidActor, diff, getVelocity().getAngle());
	}
}
