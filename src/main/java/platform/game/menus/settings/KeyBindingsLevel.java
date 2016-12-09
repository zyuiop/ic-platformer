package platform.game.menus.settings;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.IOException;
import platform.game.KeyBindings;
import platform.game.KeyBindings.Key;
import platform.game.Simulator;
import platform.game.World;
import platform.game.actors.ui.ButtonActor;
import platform.game.actors.ui.TextBox;
import platform.game.level.Level;
import platform.game.menus.main.MainMenuLevel;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class KeyBindingsLevel extends Level {
	private AddKeyActor activeKeyAdd = null;

	@Override
	public void update(Input input) {
		// sobre svp
	}

	@Override
	public void draw(Input input, Output output) {
		// sobre svp
	}

	@Override
	public void register(World world) {
		super.register(world);
		KeyBindings bindings = KeyBindings.getInstance();

		int pos = 80;
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 15);

		for (Key key : Key.values()) {
			Vector vector = new Vector(105, pos);
			KeyLineActor kla = new KeyLineActor(font, vector, this, key);
			world.register(kla);
			for (int val : bindings.getKeys(key)) {
				world.register(new ButtonActor(kla.getNextAvailablePosition(), font, Color.WHITE, KeyEvent.getKeyText(val), "green_button04", "yellow_button04") {
					@Override
					protected void onClick() {
						bindings.removeKey(key, val);
						getWorld().setNextLevel(new KeyBindingsLevel());
						getWorld().nextLevel();
					}
				});
			}
			world.register(new AddKeyActor(kla, key));
			pos += 45;
		}

		world.register(new ButtonActor(new Vector(105, 50), font, Color.WHITE, "Sauvegarder", "green_button04", "yellow_button04", 100, 30, 10, 10) {
			@Override
			protected void onClick() {
				try {
					bindings.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
				getWorld().setNextLevel(new MainMenuLevel());
				getWorld().nextLevel();
			}
		});

		world.register(new ButtonActor(new Vector(225, 50), font, Color.WHITE, "Abandonner", "red_button_02", "yellow_button04", 100, 30, 10, 10) {
			@Override
			protected void onClick() {
				try {
					bindings.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				getWorld().setNextLevel(new MainMenuLevel());
				getWorld().nextLevel();
			}
		});

		world.register(new TextBox(new Vector(125, pos + 15), null, font.deriveFont(Font.BOLD, 20), Color.BLACK, 0D, 30, 150, 0, "Configuration des touches"));


		((Simulator) world).setRaw(true);

	}


	protected void setActiveKeyAdd(AddKeyActor add) {
		if (activeKeyAdd != null && !activeKeyAdd.equals(add)) {
			activeKeyAdd.setActive(false);
		}
		activeKeyAdd = add;
	}
}
