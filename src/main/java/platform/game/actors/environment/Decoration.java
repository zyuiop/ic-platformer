package platform.game.actors.environment;

import platform.game.actors.basic.PositionedActor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Decoration extends PositionedActor {
	private double rotation = 0D;

	public Decoration(Vector position, double size, String spriteName) {
		super(position, size, spriteName);
	}

	public Decoration(Vector position, double sizeX, double sizeY, String spriteName) {
		super(position, sizeX, sizeY, spriteName);
	}

	public Decoration(Vector position, double size, String spriteName, double rotation) {
		super(position, size, spriteName);
		this.rotation = rotation;
	}

	public Decoration(Vector position, double sizeX, double sizeY, String spriteName, double rotation) {
		super(position, sizeX, sizeY, spriteName);
		this.rotation = rotation;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		if (sprite != null)
			output.drawSprite(sprite, getBox(), rotation);
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
