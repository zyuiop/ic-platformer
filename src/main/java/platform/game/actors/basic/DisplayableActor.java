package platform.game.actors.basic;

import java.util.function.Function;
import platform.game.actors.Direction;
import platform.game.actors.Orientation;
import platform.game.actors.interfaces.IPositioned;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         An actor that has a position and can be displayed
 */
public abstract class DisplayableActor extends PositionedActor implements IPositioned {
	private boolean reloadSprite = false;
	private String spriteName;
	private Sprite sprite;
	private Function<Box, Box> boxTransformer = null;

	public DisplayableActor(Box box, String spriteName) {
		super(box);
		this.spriteName = spriteName;
	}

	public DisplayableActor(Vector position, double size, String spriteName) {
		super(position, size);
		this.spriteName = spriteName;
	}

	public DisplayableActor(Vector position, double sizeX, double sizeY, String spriteName) {
		super(position, sizeX, sizeY);
		this.spriteName = spriteName;
	}

	@Override
	public void draw(Input input, Output output) {
		Sprite sprite = getCurrentSprite();
		Box box = getDisplayBox();

		if (sprite != null && box != null) {
			output.drawSprite(sprite, box, getRotation(), getOpacity());
		}
	}

	/**
	 * Get the rotation of this actor, in radians
	 *
	 * @return the rotation angle of this actor
	 */
	public double getRotation() {
		return 0D;
	}

	/**
	 * Get a box corresponding to the display position of the sprite
	 */
	public Box getDisplayBox() {
		Box box = getBox();

		if (boxTransformer != null) { box = boxTransformer.apply(box); }

		return box;
	}

	/**
	 * Defines a transformer to convert the bounding box to a displayable box. The transformer
	 * argument is the bounding box of this actor, and the function must return a box which will
	 * be used as a basis to display this actor.
	 *
	 * @param boxTransformer a box-to-box transformer
	 */
	public void setBoxTransformer(Function<Box, Box> boxTransformer) {
		this.boxTransformer = boxTransformer;
	}

	/**
	 * Defines a box transformer and return this actor. Same method as
	 * {@link DisplayableActor#setBoxTransformer(Function)} but for chained calls.
	 *
	 * @param boxTransformer a box-to-box transformer
	 * @return this actor
	 */
	public PositionedActor boxTransformer(Function<Box, Box> boxTransformer) {
		setBoxTransformer(boxTransformer);
		return this;
	}

	public Sprite getCurrentSprite() {
		if (sprite == null || reloadSprite) {
			if (spriteName != null) {
				if (getWorld() != null) { sprite = getWorld().getLoader().getSprite(spriteName); }
			}
			reloadSprite = false;
		}

		return sprite;
	}

	/**
	 * Get the opacity of this actor, where 1 is fully opaque and 0 invisible
	 *
	 * @return the opacity
	 *
	 * @see Output#drawSprite(Sprite, Box, double, double)
	 */
	public double getOpacity() {
		return 1D;
	}


	/**
	 * Change the sprite of the actor
	 * @param sprite the new sprite of the actor
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	/**
	 * Get the name of the current sprite of this actor
	 * @return the name of the current sprite
	 */
	public String getSpriteName() {
		return spriteName;
	}

	/**
	 * Change the sprite of the actor
	 * @param spriteName the name of the new sprite of the actor
	 */
	public void setSpriteName(String spriteName) {
		this.spriteName = spriteName;
		this.reloadSprite = true;
	}
}
