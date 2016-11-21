package platform.game.actors;

import platform.game.actors.basic.PositionedActor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class BlowAnimation extends PositionedActor {
	private double time;

	public BlowAnimation(Vector position) {
		super("smoke.gray.1", .8, position);
	}

	@Override
	public void update(Input input) {
		super.update(input);

		double oldTime = time;
		time = time + input.getDeltaTime();
		if (oldTime < .1 && time >= .1)
			setSpriteName("smoke.gray.2");
		else if (oldTime < .2 && time >= .2)
			setSpriteName("smoke.gray.3");
		else if (time >= .3)
			getWorld().unregister(this);
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		if (sprite != null)
			output.drawSprite(sprite, getBox(), 0, .80 - time);
	}

	@Override
	public int getPriority() {
		return 50;
	}
}
