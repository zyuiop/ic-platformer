package platform.game.menus.settings;

import java.awt.event.MouseEvent;
import java.util.Collection;
import platform.game.KeyBindings;
import platform.game.KeyBindings.Key;
import platform.game.actors.basic.DisplayableActor;
import platform.game.menus.ButtonActor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.SwingDisplay;
import platform.util.Vector;
import platform.util.View;

/**
 * @author zyuiop
 */
public class AddKeyActor extends ButtonActor {
	private KeyLineActor parent;
	private boolean isActive;
	private Key key;

	public AddKeyActor(KeyLineActor parent, Key key) {
		super(() -> {
			// oseeeef
		}, parent.getNextAvailablePosition().sub(new Vector(35, 0)), null, null, null, "green_button04", "yellow_button04", 40, 40, 0, 0);
		this.parent = parent;
		this.key = key;
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
			Collection<Integer> pressed = ((View) input).getInput().getPressedKeys();
			if (pressed.size() > 0) {
				KeyBindings.getInstance().addKey(key, pressed.iterator().next());
				this.getWorld().setNextLevel(new KeyBindingsLevel());
				this.getWorld().nextLevel();
			}
		}

		if (sprite != null) { setSpriteName(sprite); }
	}

	@Override
	public int getPriority() {
		return 100;
	}

	protected void setActive(boolean active) {
		this.isActive = active;
	}
}
