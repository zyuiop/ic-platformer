package platform.game.actors.basic;

import platform.game.Actor;
import platform.game.actors.DataReadable;
import platform.game.actors.DataWritable;
import platform.game.data.ActorFactory;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;

/**
 * @author zyuiop
 *
 * An actor defined by its sprite and that can be displayed
 */
public abstract class DisplayableActor extends Actor implements DataReadable, DataWritable {
	private boolean reloadSprite = false;
	private String spriteName;
	private Sprite sprite;

	protected DisplayableActor() {
	}

	public DisplayableActor(String spriteName) {
		this.spriteName = spriteName;
	}

	public DisplayableActor(Sprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		if (sprite != null && getBox() != null)
			output.drawSprite(sprite, getBox());
	}

	protected Sprite getCurrentSprite() {
		if (sprite == null || reloadSprite) {
			if (spriteName != null)
				if (getWorld() != null)
					sprite = getWorld().getLoader().getSprite(spriteName);
			reloadSprite = false;
		}

		return sprite;
	}

	public void setSpriteName(String spriteName) {
		this.spriteName = spriteName;
		this.reloadSprite = true;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public String getSpriteName() {
		return spriteName;
	}

	@Override
	public void read(ActorFactory factory) {
		spriteName = factory.getDataMap().get("spriteName").getAsString();
	}

	@Override
	public void write(ActorFactory factory) {
		factory.getDataMap().put("spriteName", spriteName);
	}
}
