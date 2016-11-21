package platform.game.actors.basic;

import platform.game.data.ActorFactory;
import platform.util.Box;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * An actor defined by its position and sprite
 */
public abstract class PositionedActor extends DisplayableActor {
	protected double sizeX;
	protected double sizeY;
	protected Vector position;

	protected PositionedActor() {
	}

	public PositionedActor(String spriteName, double size, Vector position) {
		super(spriteName);
		this.sizeX = size;
		this.sizeY = size;
		this.position = position;
	}

	public PositionedActor(Sprite sprite, double size, Vector position) {
		super(sprite);
		this.sizeX = size;
		this.sizeY = size;
		this.position = position;
	}

	public PositionedActor(String spriteName, double sizeX, double sizeY, Vector position) {
		super(spriteName);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.position = position;
	}

	public PositionedActor(Sprite sprite, double sizeX, double sizeY, Vector position) {
		super(sprite);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.position = position;
	}

	public Vector getPosition() {
		return position;
	}

	public Box getBox() {
		return new Box(position, sizeX, sizeY);
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public double getSizeX() {
		return sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		sizeX = factory.getDataMap().get("sizeX").getAsDouble();
		sizeY = factory.getDataMap().get("sizeY").getAsDouble();
		position = factory.getDataMap().get("position").getAsVector();
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("sizeX", sizeX);
		factory.getDataMap().put("sizeX", sizeY);
		factory.getDataMap().put("position", position);
	}
}
