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

	public Decoration(String spriteName, double size, Vector position) {
		super(spriteName, size, position);
	}

	public Decoration(Sprite sprite, double size, Vector position) {
		super(sprite, size, position);
	}

	public Decoration(String spriteName, double sizeX, double sizeY, Vector position) {
		super(spriteName, sizeX, sizeY, position);
	}

	public Decoration(Sprite sprite, double sizeX, double sizeY, Vector position) {
		super(sprite, sizeX, sizeY, position);
	}

	public Decoration(String spriteName, double size, Vector position, double rotation) {
		super(spriteName, size, position);
		this.rotation = rotation;
	}

	public Decoration(Sprite sprite, double size, Vector position, double rotation) {
		super(sprite, size, position);
		this.rotation = rotation;
	}

	public Decoration(String spriteName, double sizeX, double sizeY, Vector position, double rotation) {
		super(spriteName, sizeX, sizeY, position);
		this.rotation = rotation;
	}

	public Decoration(Sprite sprite, double sizeX, double sizeY, Vector position, double rotation) {
		super(sprite, sizeX, sizeY, position);
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
