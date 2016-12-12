package platform.game.actors.environment;

import java.util.ArrayList;
import java.util.List;
import platform.game.actors.Actor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.View;

/**
 * @author zyuiop
 *         <p>
 *         This class displays a background in the back of the scene.
 */
public class Background extends Actor {
	private final String spriteName;
	private final boolean repeatX;
	private final boolean repeatY;
	private Sprite sprite;

	private final List<Box> computedBoxes = new ArrayList<>();
	private Box computeBase = null;

	/**
	 * Create a background. The repeat parameters allow to keep the image ratio for any window
	 * size
	 *
	 * @param name the sprite to use as the background image
	 * @param repeatX true to allow repeating the sprite to fill the x axis (if false, the image
	 * will be resized)
	 * @param repeatY true to allow repeating the sprite to fill the y axis (if false, the image
	 * will be resized)
	 */
	public Background(String name, boolean repeatX, boolean repeatY) {
		spriteName = name;
		this.repeatX = repeatX;
		this.repeatY = repeatY;
	}

	@Override
	public int getPriority() {
		return -1;
	}

	private void computeBoxes(Box box) {
		computedBoxes.clear();

		// source image ratio
		double ratio = (double) sprite.getWidth() / (double) sprite.getHeight();

		// base image size (if no repeat, we maximize the size using box size)
		double width = !repeatX ? box.getWidth() : sprite.getWidth();
		double height = !repeatY ? box.getHeight() : sprite.getHeight();

		// we repeat only on an axis : resize the other one to keep the ratio
		if (!repeatX && repeatY) {
			height = (width / ratio);
		} else if (repeatX && !repeatY) {
			width = (height * ratio);
		}

		// create as many boxes as needed to fill the space (one box per sprite)
		for (int x = 0; x < box.getWidth(); x += width) {
			for (int y = 0; y < box.getHeight(); y += height) {
				Box b = new Box(new Vector(x, y), new Vector(x + width, y + height));
				computedBoxes.add(b);
			}
		}
		this.computeBase = box;
	}

	@Override
	public void draw(Input input, Output output) {
		if (sprite == null) { sprite = getSprite(spriteName); }

		if (output instanceof View) { output = ((View) output).getOutput(); } else { return; }
		Box box = output.getBox();

		if (computedBoxes.size() == 0 || computeBase == null ||
				box.getHeight() != computeBase.getHeight() || box.getWidth() != computeBase.getWidth()) {
			computeBoxes(box);
		}

		Output finalOutput = output;
		computedBoxes.forEach(b -> finalOutput.drawSprite(sprite, b));
	}
}
