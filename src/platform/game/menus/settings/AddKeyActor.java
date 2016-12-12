package platform.game.menus.settings;

import platform.game.KeyBindings;
import platform.game.KeyBindings.Key;
import platform.game.actors.ui.ButtonActor;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;
import platform.util.View;

import java.util.Collection;

/**
 * @author zyuiop
 */
public class AddKeyActor extends ButtonActor {
	private final KeyLineActor parent;
	private boolean isActive;
	private final Key key;

	public AddKeyActor(KeyLineActor parent, Key key) {
		super(parent.getNextAvailablePosition().sub(new Vector(35, 0)), null, null, null, "green_button04", "yellow_button04", 40, 40, 0, 0);
		this.parent = parent;
		this.key = key;
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output);

		output.drawSprite(getWorld().getLoader().getSprite("plus"), getBox().add(new Vector(0, 1)));
	}

	@Override
	protected String getSprite(boolean hover) {
		if (isActive) {
			return "red_button02";
		}

		return super.getSprite(hover);
	}

	@Override
	protected void onClick() {
		if (!isActive) {
			this.isActive = true;
			parent.getParent().setActiveKeyAdd(this);
		} else {
			this.isActive = false;
			parent.getParent().setActiveKeyAdd(null);
		}
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (isActive) {
			Collection<Integer> pressed = ((View) input).getInput().getPressedKeys();
			if (pressed.size() > 0) {
				KeyBindings.getInstance().addKey(key, pressed.iterator().next());
				this.getWorld().setNextLevel(new KeyBindingsLevel());
				this.getWorld().nextLevel();
			}
		}
	}

	@Override
	public int getPriority() {
		return 100;
	}

	protected void setActive(boolean active) {
		this.isActive = active;
	}
}
