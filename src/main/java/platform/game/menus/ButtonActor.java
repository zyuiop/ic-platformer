package platform.game.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import platform.game.actors.basic.DisplayableActor;
import platform.game.actors.ui.TextBox;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;
import platform.util.sounds.Sound;

/**
 * @author zyuiop
 */
public class ButtonActor extends TextBox {
	private final ClickHandler clickHandler;
	private final String sprite;
	private final String hoverSprite;
	private double paddingLeft = 10;
	private double paddingBot = 10;

	public ButtonActor(ClickHandler handler, Vector position, Font font, Color color, String text, String sprite, String hoverSprite) {
		this(handler, position, font, color, text, sprite, hoverSprite, 100, 30, 10, 10);
	}

	public ButtonActor(ClickHandler clickHandler, Vector position, Font font, Color color, String text, String sprite, String hoverSprite, double width, double heigth, double paddingLeft, double paddingBot) {
		super(position, sprite, font, color, 0D, heigth, width, paddingLeft, 0, 0, paddingBot, text);

		// super(new Box(position.add(new Vector(-paddingLeft, -paddingBot)), position.add(new Vector(width, heigth))), sprite);
		this.sprite = sprite;
		this.clickHandler = clickHandler;
		this.hoverSprite = hoverSprite;
		this.paddingLeft = paddingLeft;
		this.paddingBot = paddingBot;
	}

	@Override
	public void update(Input input) {
		String sprite = this.sprite;

		if (getBox().isColliding(input.getMouseLocation())) {
			sprite = hoverSprite;
			if (input.getMouseButton(MouseEvent.BUTTON1).isPressed()) {

				Sound sound = getWorld().getSoundLoader().getSound("switch5");
				sound.play();

				clickHandler.onClick();
			}
		}

		setSpriteName(sprite);
	}


	@Override
	public int getPriority() {
		return 100;
	}

	public interface ClickHandler {
		void onClick();
	}

}
