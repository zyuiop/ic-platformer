package platform.game.level;


import platform.game.KeyBindings;
import platform.game.KeyBindings.Key;
import platform.game.World;
import platform.game.actors.environment.Background;
import platform.game.actors.ui.ButtonActor;
import platform.game.actors.ui.TextBox;
import platform.game.menus.main.MainMenuLevel;
import platform.game.menus.settings.KeyBindingsLevel;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

import java.awt.*;

/**
 * @author zyuiop
 */
public class EndGameLevel extends Level {
	@Override
	public void update(Input input) {
		if (KeyBindings.getInstance().isDown(input, Key.MENU)) {
			getWorld().setNextLevel(new MainMenuLevel());
			getWorld().nextLevel();
		}
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
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);

			world.register(new TextBox(new Vector(0, 0.5), null, font, Color.BLACK, 0, 1, 2.5, 0, "Bravo !"));
			world.register(new TextBox(new Vector(0, -0.5), null, font, Color.BLACK, 0, 1, 5, 0, "Vous avez terminé le jeu"));

			world.register(new Background("background.hills", true, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
