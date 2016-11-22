package platform.game.actors;

import platform.game.Actor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;

/**
 * @author zyuiop
 */
public class Label extends Actor {
	private final int priority;
	private final Vector position;
	private final String text;
	private final Font font;
	private Color color = Color.BLACK;

	public Label(int priority, Vector position, String text, Font font) {
		this.priority = priority;
		this.position = position;
		this.text = text;
		this.font = font;
	}

	public Label(int priority, Vector position, String text, Font font, Color color) {
		this.priority = priority;
		this.position = position;
		this.text = text;
		this.font = font;
		this.color = color;
	}

	@Override
	public void draw(Input input, Output output) {
		output.drawText(getText(), position, font, color);
	}

	@Override
	public int getPriority() {
		return priority;
	}

	public String getText() {
		return text;
	}
}
