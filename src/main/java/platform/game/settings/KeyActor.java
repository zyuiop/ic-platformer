package platform.game.settings;

import platform.game.Actor;
import platform.game.actors.basic.DisplayableActor;
import platform.game.settings.KeyBindings.Key;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * @author zyuiop
 */
public class KeyActor extends DisplayableActor {
	private KeyLineActor parent;
	private Key key;
	private int binding;
	private Vector position;

	public KeyActor(KeyLineActor parent, Key key, int binding) {
		super("green_button04");
		this.parent = parent;
		this.key = key;
		this.binding = binding;
		this.position = parent.getNextAvailablePosition();
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output);
		Color color = Color.WHITE;
		output.drawText(KeyEvent.getKeyText(binding), position.add(new Vector(3, 5)), parent.getFont().deriveFont(Font.BOLD), color);
	}

	@Override
	public void update(Input input) {
		String sprite = "green_button04";
		if (input.getMouseButton(MouseEvent.BUTTON1).isDown() && getBox().isColliding(input.getMouseLocation())) {
			sprite = "red_button02";
			// remove font
			KeyBindings.getInstance().removeKey(key, binding);
			getWorld().setNextLevel(new KeyBindingsLevel());
			getWorld().nextLevel();
		} else if (getBox().isColliding(input.getMouseLocation()))
			sprite = "yellow_button04";

		setSpriteName(sprite);
	}

	@Override
	public Box getBox() {
		return new Box(position.add(new Vector(-5, -5)), position.add(new Vector(100, 30)));
	}

	@Override
	public int getPriority() {
		return 100;
	}


}
