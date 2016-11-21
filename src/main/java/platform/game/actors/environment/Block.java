package platform.game.actors.environment;

import platform.game.actors.basic.DisplayableActor;
import platform.game.data.ActorFactory;
import platform.util.Box;

/**
 * Simple solid actor that does nothing.
 */
public class Block extends DisplayableActor {
	private Box box;

	protected Block() {}

	public Block(Box box, String sprite) {
		super(sprite);
		this.box = box;
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

	@Override
	public void read(ActorFactory factory) {
		super.read(factory);

		box = factory.getDataMap().get("box").getAsBox();
	}

	@Override
	public void write(ActorFactory factory) {
		super.write(factory);

		factory.getDataMap().put("box", box);
	}
}
