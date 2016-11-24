package platform.game.actors.basic;

import java.util.function.Function;
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
	private Function<Box, Box> boxTransformer = null;

	public DisplayableActor(String spriteName) {
		this.spriteName = spriteName;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		Box box = getBox();

		if (boxTransformer != null)
			box = boxTransformer.apply(box);

		if (sprite != null && box != null)
			output.drawSprite(sprite, box);
	}

	/**
	 * Defines a transformer to convert the bounding box to a displayable box. The transformer
	 * argument is the bounding box of this actor, and the function must return a box which will
	 * be used as a basis to display this actor.
	 * @param boxTransformer a box-to-box transformer
	 */
	public void setBoxTransformer(Function<Box, Box> boxTransformer) {
		this.boxTransformer = boxTransformer;
	}

	/**
	 * Defines a box transformer and return this actor. Same method as
	 * {@link DisplayableActor#setBoxTransformer(Function)} but for chained calls.
	 * @param boxTransformer a box-to-box transformer
	 * @return this actor
	 */
	public DisplayableActor boxTransformer(Function<Box, Box> boxTransformer) {
		setBoxTransformer(boxTransformer);
		return this;
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
