package platform.game.menus.settings;

import platform.game.Actor;
import platform.game.KeyBindings.Key;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;

/**
 * @author zyuiop
 */
public class KeyLineActor extends Actor {
	private final Font font;
	private final Vector position;
	private final KeyBindingsLevel parentLevel;
	private final Key key;
	private int usedPositions = 0;

	public KeyLineActor(Font font, Vector position, KeyBindingsLevel level, Key key) {
		this.font = font;
		this.position = position;
		parentLevel = level;
		this.key = key;
	}

	@Override
	public void draw(Input input, Output output) {
		output.drawText(key.getDescription(), position, font, Color.BLACK);
	}

	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
		usedPositions = 0;
	}

	protected Vector getNextAvailablePosition() {
		return position.add(new Vector(100 + (++usedPositions) * 120, 0));
	}

	@Override
	public int getPriority() {
		return 80;
	}

	protected Font getFont() {
		return font;
	}

	public KeyBindingsLevel getParent() {
		return parentLevel;
	}
}
