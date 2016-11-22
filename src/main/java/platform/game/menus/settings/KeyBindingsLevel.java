package platform.game.menus.settings;

import platform.game.KeyBindings;
import platform.game.Simulator;
import platform.game.World;
import platform.game.actors.Label;
import platform.game.level.Level;
import platform.game.menus.ButtonActor;
import platform.game.KeyBindings.Key;
import platform.game.menus.main.MainMenuLevel;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

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
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/kenvector_future_thin.ttf"));

			for (Key key : Key.values()) {
				Vector vector = new Vector(30, pos);
				KeyLineActor kla = new KeyLineActor(font.deriveFont(Font.BOLD, 15), vector, this, key);
				world.register(kla);
				for (int val : bindings.getKeys(key)) {
					world.register(new ButtonActor(() -> {
								bindings.removeKey(key, val);
								getWorld().setNextLevel(new KeyBindingsLevel());
								getWorld().nextLevel();
							}, kla.getNextAvailablePosition(), font.deriveFont(Font.BOLD, 15), Color.WHITE, KeyEvent.getKeyText(val), "green_button04", "yellow_button04"));
				}
				world.register(new AddKeyActor(kla, key));
				pos += 45;
			}

			world.register(new ButtonActor(() -> {
				try {
					bindings.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
				getWorld().setNextLevel(new MainMenuLevel());
				getWorld().nextLevel();
			}, new Vector(30, 35), font.deriveFont(Font.BOLD, 15), Color.WHITE, "Sauvegarder", "green_button04", "yellow_button04", 150, 30, 10, 10));

			world.register(new ButtonActor(() -> {
				try {
					bindings.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				getWorld().setNextLevel(new MainMenuLevel());
				getWorld().nextLevel();
			}, new Vector(200, 35), font.deriveFont(Font.BOLD, 15), Color.WHITE, "Abandonner", "red_button_02", "yellow_button04", 150, 30, 10, 10));

			world.register(new Label(100, new Vector(30, pos), "Configuration des touches", font.deriveFont(Font.BOLD, 20), Color.BLACK));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}



		((Simulator) world).setRaw(true);

	}


	protected void setActiveKeyAdd(AddKeyActor add) {
		if (activeKeyAdd != null && !activeKeyAdd.equals(add)) {
			activeKeyAdd.setActive(false);
		}
		activeKeyAdd = add;
	}
}
