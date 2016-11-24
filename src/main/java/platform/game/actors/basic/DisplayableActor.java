package platform.game.actors.basic;

import platform.game.Actor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;

/**
 * @author zyuiop
 *
 * An actor defined by its sprite and that can be displayed
 */
public abstract class DisplayableActor extends Actor {
	private boolean reloadSprite = false;
	private String spriteName;
	private Sprite sprite;

	public DisplayableActor(String spriteName) {
		this.spriteName = spriteName;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		Box box = getDisplayBox();
		if (sprite != null && box != null)
			output.drawSprite(sprite, getDisplayBox());
	}

	/**
	 * Get the box used to display the actor. In general it is the same than the classic box.
	 * @return a box corresponding to the dimensions of the displayed actor
	 * @implNote this implementation returns {@link Actor#getBox()}
	 */
	public Box getDisplayBox() {
		return getBox();
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
}
