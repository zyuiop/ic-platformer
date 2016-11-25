package platform.game.actors.blocks;

import platform.game.actors.Direction;
import platform.game.actors.basic.DisplayableActor;
import platform.util.Box;
import platform.util.Vector;

/**
 * Simple solid actor that does nothing more than being solid.
 */
public class Block extends DisplayableActor {
	public Block(Box box, String spriteName) {
		super(box.getCenter(), box.getWidth(), box.getHeight(), spriteName);
	}

	public Block(Vector position, double size, String spriteName) {
		super(position, size, spriteName);
	}

	public Block(Vector position, double sizeX, double sizeY, String spriteName) {
		super(position, sizeX, sizeY, spriteName);
	}

	public Block(Box box, String spriteName, Direction direction) {
		super(box, spriteName, direction);
	}

	public Block(Vector position, double size, String spriteName, Direction direction) {
		super(position, size, spriteName, direction);
	}

	public Block(Vector position, double sizeX, double sizeY, String spriteName, Direction direction) {
		super(position, sizeX, sizeY, spriteName, direction);
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
