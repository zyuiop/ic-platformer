package platform.game.menus.main;


import java.awt.Color;
import java.awt.Font;
import platform.game.World;
import platform.game.actors.Background;
import platform.game.actors.ui.ButtonActor;
import platform.game.actors.ui.TextBox;
import platform.game.level.Level;
import platform.game.menus.settings.KeyBindingsLevel;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

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
			Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);

			world.register(new TextBox(new Vector(0, 2), null, font, Color.BLACK, 0, 1, 4, 0, "IC Platformer"));

			world.register(new ButtonActor(new Vector(0, 0), font, Color.BLACK, "Jouer", "grey_button14", "grey_button00", 4, 1, .5, .5) {
				@Override
				protected void onClick() {
					getWorld().setNextLevel(getWorld().getLevelManager().restartGroup(null));
					getWorld().nextLevel();
				}
			});

			world.register(new ButtonActor(new Vector(0, -2), font, Color.BLACK, "Touches", "grey_button14", "grey_button00", 4, 1, .5, .5) {
				@Override
				protected void onClick() {
					getWorld().setNextLevel(new KeyBindingsLevel());
					getWorld().nextLevel();
				}
			});


			world.register(new Background("background.hills", true, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
