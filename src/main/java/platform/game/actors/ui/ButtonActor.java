package platform.game.actors.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         A button that calls a function when it's clicked
 */
public class ButtonActor extends TextBox {
	private final ClickHandler clickHandler;
	private final String sprite;
	private final String hoverSprite;

	public ButtonActor(ClickHandler handler, Vector position, Font font, Color color, String text, String sprite, String hoverSprite) {
		this(handler, position, font, color, text, sprite, hoverSprite, 100, 30, 10, 10);
	}

	public ButtonActor(ClickHandler clickHandler, Vector position, Font font, Color color, String text, String sprite, String hoverSprite, double width, double heigth, double paddingLeft, double paddingBot) {
		super(position, sprite, font, color, 0D, heigth, width, paddingLeft, 0, 0, paddingBot, text);

		this.sprite = sprite;
		this.clickHandler = clickHandler;
		this.hoverSprite = hoverSprite;
	}

	@Override
	public void update(Input input) {
		String sprite = this.sprite;

		if (getBox().isColliding(input.getMouseLocation())) {
			sprite = hoverSprite;
			if (input.getMouseButton(MouseEvent.BUTTON1).isPressed()) {
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
