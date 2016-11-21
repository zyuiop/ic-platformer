package platform.game;

import platform.util.*;

/**
 * Simple solid actor that does nothing.
 */
public class Block extends Actor {
	private final Box box;
	private final Sprite sprite;

	public Block(Box box, Sprite sprite) {
		this.box = box;
		this.sprite = sprite;
	}

	@Override
	public void draw(Input input, Output output) {
		if (sprite != null)
			output.drawSprite(sprite, box);
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
