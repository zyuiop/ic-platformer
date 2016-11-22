package platform.game.settings;

import platform.game.Simulator;
import platform.game.World;
import platform.game.level.Level;
import platform.game.settings.KeyBindings.Key;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;
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

		int pos = 30;
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/kenpixel.ttf"));

			for (Key key : Key.values()) {
				Vector vector = new Vector(30, pos);
				KeyLineActor kla = new KeyLineActor(font.deriveFont(Font.PLAIN, 15), vector, this, key);
				world.register(kla);
				for (int val : bindings.getKeys(key))
					world.register(new KeyActor(kla, key, val));
				world.register(new AddKeyActor(kla, key));
				pos += 45;
			}
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
