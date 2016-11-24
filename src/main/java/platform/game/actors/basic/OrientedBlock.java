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

	public OrientedBlock(Vector position, double sizeX, double sizeY, String spriteName, Direction direction) {
		super(position, sizeX, sizeY, spriteName);
		this.direction = direction;
	}

	public OrientedBlock(Box box, String spriteName, Direction direction) {
		super(box, spriteName);
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	protected void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public Box getBox() {
		if (super.getPosition() == null)
			return null;
		double angle = direction.getOrientation().getAngle();
		return new Box(getPosition(), getSizeX() * Math.cos(angle) + getSizeY() * Math.sin(angle), getSizeY() * Math.cos(angle) + getSizeX() * Math.sin(angle));
	}

	@Override
	public double getRotation() {
		return direction.getRotation();
	}
}
