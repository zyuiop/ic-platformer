package platform.game.actors;

import platform.game.Actor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;

/**
 * @author zyuiop
 */
public class Background extends Actor {
	private final String spriteName;
	private final boolean repeatX;
	private final boolean repeatY;
	private Sprite sprite;

	public Background(String name, boolean repeatX, boolean repeatY) {
		spriteName = name;
		this.repeatX = repeatX;
		this.repeatY = repeatY;
	}

	public Background(String spriteName) {
		this(spriteName, true, true);
	}

	@Override
	public int getPriority() {
		return -1;
	}

	@Override
	public void draw(Input input, Output output) {
		if (sprite == null)
			sprite = getSprite(spriteName);
		output.drawBackground(sprite, repeatX, repeatY);
	}
}
