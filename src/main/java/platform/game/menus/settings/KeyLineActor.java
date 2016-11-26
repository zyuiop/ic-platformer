package platform.game.menus.settings;

import platform.game.Actor;
import platform.game.KeyBindings.Key;
import platform.game.actors.ui.TextBox;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;

/**
 * @author zyuiop
 */
public class KeyLineActor extends TextBox {
	private final Font font;
	private final Vector position;
	private final KeyBindingsLevel parentLevel;
	private int usedPositions = 0;

	public KeyLineActor(Font font, Vector position, KeyBindingsLevel level, Key key) {
		super(position, null, font, 0D, 30, 100, 0, 0, 0, 50, key.getDescription());
		this.font = font;
		this.position = position;
		parentLevel = level;
	}

	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
		usedPositions = 0;
	}

	protected Vector getNextAvailablePosition() {
		return position.add(new Vector((++usedPositions) * 120, 15));
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
