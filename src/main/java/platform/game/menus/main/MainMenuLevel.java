package platform.game.menus.main;


import platform.game.World;
import platform.game.actors.SlowWriteLabel;
import platform.game.level.BasicLevel;
import platform.game.level.Level;
import platform.game.menus.ButtonActor;
import platform.game.menus.settings.KeyBindingsLevel;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;
import java.io.File;

/**
 * @author zyuiop
 */
public class MainMenuLevel extends Level {
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

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/kenvector_future_thin.ttf")).deriveFont(Font.BOLD, 20);

			world.register(new ButtonActor(() -> {
				getWorld().setNextLevel(new BasicLevel());
				getWorld().nextLevel();
			}, new Vector(-2, -.5), font, Color.BLACK, "Jouer", "grey_button14", "grey_button00", 4, 1, .5, .5));

			world.register(new ButtonActor(() -> {
				getWorld().setNextLevel(new KeyBindingsLevel());
				getWorld().nextLevel();
			}, new Vector(-2, -2.2), font, Color.BLACK, "Touches", "grey_button14", "grey_button00", 4, 1, .5, .5));

			world.register(new SlowWriteLabel(50, new Vector(-5, 3), "Salut ceci est un texte lent a s'ecrire omg", font));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
