package platform.game.actors;

import platform.util.Input;
import platform.util.Vector;

import java.awt.*;

/**
 * @author zyuiop
 */
public class SlowWriteLabel extends Label {
	public static final double DIALOG_INTERVAL = .05; // parfait pour des messages de dialogues
	private final double interval;
	private double time = 0;
	private int letters = 0;

	public SlowWriteLabel(int priority, Vector position, String text, Font font) {
		this(priority, position, text, font, .05);
	}

	public SlowWriteLabel(int priority, Vector position, String text, Font font, double interval) {
		super(priority, position, text, font);
		this.interval = interval;
	}

	public SlowWriteLabel(int priority, Vector position, String text, Font font, Color color, double interval) {
		super(priority, position, text, font, color);
		this.interval = interval;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (letters >= super.getText().length())
			return;

		time += input.getDeltaTime();
		if (time >= interval) {
			letters++;
			time -= interval;
		}
	}

	@Override
	public String getText() {
		String text = super.getText();
		int letters = Math.min(this.letters, text.length());
		return text.substring(0, letters);
	}
}
