package platform.game.actors.ui;

import java.awt.Font;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class DismissableTextBox extends TextBox {
	public DismissableTextBox(Vector center, String spriteName, Font font, double lineSpacing, double lineHeight, double lineWidth, double padding, String... text) {
		super(center, spriteName, font, lineSpacing, lineHeight, lineWidth, padding, text);
	}

	public DismissableTextBox(Vector center, String spriteName, Font font, double lineSpacing, double lineHeight, double lineWidth, double paddingLeft, double paddingTop, double paddingRight, double paddingBottom, String... lines) {
		super(center, spriteName, font, lineSpacing, lineHeight, lineWidth, paddingLeft, paddingTop, paddingRight, paddingBottom, lines);
	}

	@Override
	public void update(Input input) {
		super.update(input);
		if (input.getPressedKeys().size() > 0) {
			getWorld().unregister(this);
		}
	}
}
