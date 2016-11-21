package platform.game.actors;

import platform.game.actors.basic.DisplayableActor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;

/**
 * @author zyuiop
 */
public abstract class TextBox extends DisplayableActor {
	private String[] lines;
	private double lineSpacing;
	private double fontSize;

	public TextBox(String spriteName) {
		super(spriteName);
	}

	public TextBox(Sprite sprite) {
		super(sprite);
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output); // first we draw the box

		// and now we draw the text
		// output.drawSprite();
	}
}
