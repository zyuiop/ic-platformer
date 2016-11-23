package platform.game.menus;

import platform.game.actors.basic.DisplayableActor;
import platform.util.*;
import platform.util.sounds.Sound;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author zyuiop
 */
public class ButtonActor extends DisplayableActor {
	private final ClickHandler clickHandler;
	private final Vector position;
	private final Font font;
	private final Color color;
	private final String text;
	private final String sprite;
	private final String hoverSprite;
	private double width = 100;
	private double heigth = 30;
	private double paddingLeft = 10;
	private double paddingBot = 10;

	public ButtonActor(ClickHandler handler, Vector position, Font font, Color color, String text, String sprite, String hoverSprite) {
		super(sprite);
		this.clickHandler = handler;
		this.font = font;
		this.color = color;
		this.text = text;
		this.sprite = sprite;
		this.hoverSprite = hoverSprite;
		this.position = position;
	}

	public ButtonActor(ClickHandler clickHandler, Vector position, Font font, Color color, String text, String sprite, String hoverSprite, double width, double heigth, double paddingLeft, double paddingBot) {
		super(sprite);
		this.clickHandler = clickHandler;
		this.position = position;
		this.font = font;
		this.color = color;
		this.text = text;
		this.sprite = sprite;
		this.hoverSprite = hoverSprite;
		this.width = width;
		this.heigth = heigth;
		this.paddingLeft = paddingLeft;
		this.paddingBot = paddingBot;
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output);
		output.drawText(text, position, font, color);
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
	public Box getBox() {
		return new Box(position.add(new Vector(-paddingLeft, -paddingBot)), position.add(new Vector(width, heigth)));
	}

	@Override
	public int getPriority() {
		return 100;
	}

	public interface ClickHandler {
		void onClick();
	}

}
