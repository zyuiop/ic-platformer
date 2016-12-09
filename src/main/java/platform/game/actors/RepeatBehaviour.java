package platform.game.actors;

import java.util.ArrayList;
import java.util.List;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         A class describing how the sprite must be repeated on a block
 */
public final class RepeatBehaviour {
	private final double spriteWidth;
	private final double spriteHeight;
	private final boolean repeatX;
	private final boolean repeatY;

	public RepeatBehaviour(double spriteWidth, double spriteHeight, boolean repeatX, boolean repeatY) {
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.repeatX = repeatX;
		this.repeatY = repeatY;
	}

	/**
	 * Compute all the display boxes of this sprite
	 *
	 * @param box the entire box where the sprite must be displayed
	 * @return all the sub-boxes needed to draw the sprite correctly keeping the ratio
	 */
	public List<Box> computeBoxes(Box box) {
		List<Box> computedBoxes = new ArrayList<>();

		// source image ratio
		double ratio = spriteWidth / spriteHeight;

		// base image size (if no repeat, we maximize the size using box size)
		double width = !repeatX ? box.getWidth() : spriteWidth;
		double height = !repeatY ? box.getHeight() : spriteHeight;

		// we repeat only on an axis : resize the other one to keep the ratio
		if (!repeatX && repeatY) {
			height = (width / ratio);
		} else if (repeatX && !repeatY) {
			width = (height * ratio);
		}

		Vector center = box.getCenter();
		// create as many boxes as needed to fill the space (one box per sprite)
		for (double x = center.getX() - (box.getWidth() / 2); x < center.getX() + (box.getWidth() / 2); x += width) {
			for (double y = center.getY() - (box.getHeight() / 2); y < center.getY() + (box.getHeight() / 2); y += height) {
				Box b = new Box(new Vector(x, y), new Vector(x + width, y + height));
				computedBoxes.add(b);
			}
		}

		return computedBoxes;
	}
}
