package platform.game.actors.ui;

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

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output); // first we draw the box

		// TODO : finish this
		// TODO : replace Tooltip with this
		// TODO : implement as slow and not slow
		// and now we draw the text
		// output.drawSprite();
	}
}
