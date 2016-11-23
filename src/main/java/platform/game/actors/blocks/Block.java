package platform.game.actors.blocks;

import platform.game.actors.basic.PositionedActor;
import platform.util.Box;
import platform.util.Vector;

/**
 * Simple solid actor that does nothing.
 */
public class Block extends PositionedActor {
	public Block(Box box, String spriteName) {
		super(box.getCenter(), box.getWidth(), box.getHeight(), spriteName);
	}

	public Block(Vector position, double size, String spriteName) {
		super(position, size, spriteName);
	}

	public Block(Vector position, double sizeX, double sizeY, String spriteName) {
		super(position, sizeX, sizeY, spriteName);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
