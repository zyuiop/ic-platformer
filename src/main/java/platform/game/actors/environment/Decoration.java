package platform.game.actors.environment;

import platform.game.actors.basic.DisplayableActor;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Decoration extends DisplayableActor {
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
	public double getRotation() {
		return rotation;
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
