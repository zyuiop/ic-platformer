package platform.game.actors.basic;

import platform.game.actors.Direction;
import platform.game.actors.blocks.Block;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 * An actor defined by its position, size, direction and sprite
 */
public abstract class OrientedBlock extends Block {
	private Direction direction;

	// TODO : rewrite ratio system ? ==> add sizeX sizeY support using cos/sin (see laser)
	// TODO : move jumper specific methods to the jumper itself

	public OrientedBlock(Vector position, double size, String spriteName, Direction direction) {
		super(position, size, spriteName);
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	protected void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Box getBox() {
		return new Box(position, sizeX * getHitboxXRatio(), sizeY * getHitboxYRatio());
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		if (sprite != null) {
			output.drawSprite(sprite, super.getBox(), getDirection().getRotation());
		}
	}

	protected double getHitboxXRatio() {
		return 1;
	}

	protected double getHitboxYRatio() {
		return 1;
	}

}
