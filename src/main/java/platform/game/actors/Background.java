package platform.game.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import platform.game.Actor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;
import platform.util.View;

/**
 * @author zyuiop
 */
public class Background extends Actor {
	private final String spriteName;
	private final boolean repeatX;
	private final boolean repeatY;
	private Sprite sprite;

	private List<Box> computedBoxes = new ArrayList<>();
	private Box computeBase = null;

	public Background(String name, boolean repeatX, boolean repeatY) {
		spriteName = name;
		this.repeatX = repeatX;
		this.repeatY = repeatY;
	}

	public Background(String spriteName) {
		this(spriteName, true, true);
	}

	@Override
	public int getPriority() {
		return -1;
	}

	private void computeBoxes(Box box) {
		computedBoxes.clear();

		double ratio = (double) sprite.getWidth() / (double) sprite.getHeight();
		double width = !repeatX ? box.getWidth() : sprite.getWidth();
		double height = !repeatY ? box.getHeight() : sprite.getHeight();

		if (!repeatX && repeatY) {
			height = (width / ratio);
		} else if (repeatX && !repeatY) {
			width = (height * ratio);
		}

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
		if (sprite == null)
			sprite = getSprite(spriteName);

		if (output instanceof View)
			output = ((View) output).getOutput();
		else
			return;
		Box box = output.getBox();

		if (computedBoxes.size() == 0 || computeBase == null ||
				box.getHeight() != computeBase.getHeight() || box.getWidth() != computeBase.getWidth()) {
			computeBoxes(box);
		}

		Output finalOutput = output;
		computedBoxes.forEach(b -> finalOutput.drawSprite(sprite, b));
	}
}
