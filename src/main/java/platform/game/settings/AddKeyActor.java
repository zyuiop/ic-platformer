package platform.game.settings;

import platform.game.actors.basic.DisplayableActor;
import platform.game.settings.KeyBindings.Key;
import platform.util.*;

import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * @author zyuiop
 */
public class AddKeyActor extends DisplayableActor {
	private KeyLineActor parent;
	private boolean isActive;
	private Key key;
	private Vector position;

	public AddKeyActor(KeyLineActor parent, Key key) {
		super("green_button04");
		this.parent = parent;
		this.key = key;
		this.position = parent.getNextAvailablePosition();
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output);

		output.drawSprite(getWorld().getLoader().getSprite("plus"), getBox().add(new Vector(0, 1)));
	}

	@Override
	public void update(Input input) {
		String sprite = null;
		if (input.getMouseButton(MouseEvent.BUTTON1).isPressed() && getBox().isColliding(input.getMouseLocation())) {
			if (!isActive) {
				this.isActive = true;
				parent.getParent().setActiveKeyAdd(this);
				sprite = "red_button02";
			} else {
				this.isActive = false;
				parent.getParent().setActiveKeyAdd(null);
				sprite = "green_button04";
			}

		} else if (getBox().isColliding(input.getMouseLocation()) && !isActive) {
			sprite = "yellow_button04";
		} else if (!isActive) {
			sprite = "green_button04";
		}

		if (isActive) {
			Collection<Integer> pressed = ((SwingDisplay) ((View) input).getInput()).getPressedKeys();
			if (pressed.size() > 0) {
				KeyBindings.getInstance().addKey(key, pressed.iterator().next());
				this.getWorld().setNextLevel(new KeyBindingsLevel());
				this.getWorld().nextLevel();
			}
		}

		if (sprite != null)
			setSpriteName(sprite);
	}

	@Override
	public Box getBox() {
		return new Box(position.add(new Vector(-5, -5)), position.add(new Vector(30, 30)));
	}

	@Override
	public int getPriority() {
		return 100;
	}

	protected void setActive(boolean active) {
		this.isActive = active;
	}
}
