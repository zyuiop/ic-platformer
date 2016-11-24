package platform.game.actors.ui;

import java.awt.Color;
import java.awt.Font;
import platform.game.menus.ButtonActor;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Tooltip extends ButtonActor {
	public Tooltip(Vector position, Font font, Color color, String text) {
		super(() -> {}, position, font, color, text, "text.background", "text.background");
	}

	public Tooltip(Vector position, Font font, Color color, String text, double width, double heigth, double paddingLeft, double paddingBot) {
		super(() -> {}, position, font, color, text, "text.background", "text.background", width, heigth, paddingLeft, paddingBot);
	}

	@Override
	public void update(Input input) {
		if (input.getPressedKeys().size() > 0) {
			getWorld().unregister(this);
		}
	}
}
