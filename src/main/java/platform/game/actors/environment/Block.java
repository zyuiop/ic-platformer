package platform.game.actors.environment;

import platform.game.Actor;
import platform.util.*;

/**
 * Simple solid actor that does nothing.
 */
public class Block extends Actor {
	private final Box box;
	private final String spriteName;
	private Sprite sprite;

	public Block(Box box, String sprite) {
		this.box = box;
		this.spriteName = sprite;
	}

	@Override
	public void draw(Input input, Output output) {
		if (sprite == null && spriteName != null)
			sprite = getSprite(spriteName);

		if (sprite != null)
			output.drawSprite(sprite, getBox());
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public Box getBox() {
		return box;
	}
}
