package platform.game.actors.blocks;

import platform.game.actors.basic.PositionedActor;
import platform.util.Box;
import platform.util.Vector;

/**
 * Simple solid actor that does nothing.
 */
public class Block extends PositionedActor {
	public Block(Box box, String spriteName) {
		super(spriteName, box.getWidth(), box.getHeight(), box.getCenter());
	}

	public Block(String spriteName, double size, Vector position) {
		super(spriteName, size, position);
	}

	public Block(String spriteName, double sizeX, double sizeY, Vector position) {
		super(spriteName, sizeX, sizeY, position);
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
