package platform.game.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import platform.game.actors.basic.DisplayableActor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;
import platform.util.sounds.Sound;

/**
 * @author zyuiop
 */
public class ButtonActor extends DisplayableActor {
	private final ClickHandler clickHandler;
	private final Font font;
	private final Color color;
	private final String text;
	private final String sprite;
	private final String hoverSprite;
	private double paddingLeft = 10;
	private double paddingBot = 10;

	public ButtonActor(ClickHandler handler, Vector position, Font font, Color color, String text, String sprite, String hoverSprite) {
		this(handler, position, font, color, text, sprite, hoverSprite, 100, 30, 10, 10);
	}

	public ButtonActor(ClickHandler clickHandler, Vector position, Font font, Color color, String text, String sprite, String hoverSprite, double width, double heigth, double paddingLeft, double paddingBot) {
		super(new Box(position.add(new Vector(-paddingLeft, -paddingBot)), position.add(new Vector(width, heigth))), sprite);
		this.clickHandler = clickHandler;
		this.font = font;
		this.color = color;
		this.text = text;
		this.sprite = sprite;
		this.hoverSprite = hoverSprite;
		this.paddingLeft = paddingLeft;
		this.paddingBot = paddingBot;
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output);
		output.drawText(text, getBox().getMin().add(new Vector(paddingLeft, paddingBot)), font, color);
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
