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
public abstract class ButtonActor extends TextBox {
	private final String sprite;
	private final String hoverSprite;

	public ButtonActor(Vector position, Font font, Color color, String text, String sprite, String hoverSprite) {
		this(position, font, color, text, sprite, hoverSprite, 100, 30, 10, 10);
	}

	public ButtonActor(Vector position, Font font, Color color, String text, String sprite, String hoverSprite, double width, double heigth, double paddingLeft, double paddingBot) {
		super(position, sprite, font, color, 0D, heigth, width, paddingLeft, 0, 0, paddingBot, text);

		this.sprite = sprite;
		this.hoverSprite = hoverSprite;
	}

	@Override
	public void update(Input input) {
		String sprite = getSprite(false);

		if (getBox().isColliding(input.getMouseLocation())) {
			sprite = getSprite(true);
			if (input.getMouseButton(MouseEvent.BUTTON1).isPressed()) {
				onClick();
			}
		}

		setSpriteName(sprite);
	}

	protected String getSprite(boolean hover) {
		if (hover)
			return hoverSprite;
		return sprite;
	}

	protected abstract void onClick();

	@Override
	public int getPriority() {
		return 100;
	}
}
