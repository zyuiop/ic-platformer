package platform.game.actors.ui;

import java.awt.Font;
import platform.game.Signal;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class TriggerableTextbox extends TextBox {
	private final Signal signal;

	public TriggerableTextbox(Vector center, String spriteName, Font font, double lineSpacing, double lineHeight, double lineWidth, double padding, Signal signal, String... text) {
		super(center, spriteName, font, lineSpacing, lineHeight, lineWidth, padding, text);
		this.signal = signal;
	}

	public TriggerableTextbox(Vector center, String spriteName, Font font, double lineSpacing, double lineHeight, double lineWidth, double paddingLeft, double paddingTop, double paddingRight, double paddingBottom, Signal signal, String... lines) {
		super(center, spriteName, font, lineSpacing, lineHeight, lineWidth, paddingLeft, paddingTop, paddingRight, paddingBottom, lines);
		this.signal = signal;
	}

	@Override
	public void update(Input input) {
		if (signal.isActive())
			super.update(input);
	}

	@Override
	public void draw(Input input, Output output) {
		if (signal.isActive())
			super.draw(input, output);
	}
}
