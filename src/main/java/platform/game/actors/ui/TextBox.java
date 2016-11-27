package platform.game.actors.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.function.BiFunction;
import platform.game.actors.basic.DisplayableActor;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A box displaying text on the screen with an optional background texture
 */
public class TextBox extends DisplayableActor {
	private String[] lines;
	private Color color;
	private double lineSpacing;
	private double lineHeight;
	private double paddingLeft;
	private double paddingTop;
	private Font font;

	private double elapsedTime = 0D;
	private BiFunction<String[], Double, String[]> linesAdapter = (in, u1) -> in;

	public TextBox(Vector center, String spriteName, Font font, double lineSpacing, double lineHeight, double lineWidth, double padding, String... text) {this(center, spriteName, font, lineSpacing, lineHeight, lineWidth, padding, padding, padding, padding, text);}

	public TextBox(Vector center, String spriteName, Font font, Color color, double lineSpacing, double lineHeight, double lineWidth, double padding, String... text) {this(center, spriteName, font, color, lineSpacing, lineHeight, lineWidth, padding, padding, padding, padding, text);}

	public TextBox(Vector center, String spriteName, Font font, double lineSpacing, double lineHeight, double lineWidth, double paddingLeft, double paddingTop, double paddingRight, double paddingBottom, String... lines) {this(center, spriteName, font, Color.BLACK, lineSpacing, lineHeight, lineWidth, paddingLeft, paddingTop, paddingRight, paddingBottom, lines);}

	public TextBox(Vector center, String spriteName, Font font, Color color, double lineSpacing, double lineHeight, double lineWidth, double paddingLeft, double paddingTop, double paddingRight, double paddingBottom, String... lines) {
		super(new Box(center, lineWidth + paddingLeft + paddingRight, (lineHeight) *
				lines.length + lineSpacing * (lines.length - 1) + paddingBottom + paddingTop), spriteName);

		this.color = color;
		this.lines = lines;
		this.lineSpacing = lineSpacing;
		this.lineHeight = lineHeight;
		this.paddingTop = paddingTop;
		this.paddingLeft = paddingLeft;
		this.font = font;
	}

	@Override
	public void update(Input input) {
		super.update(input);
		this.elapsedTime += input.getDeltaTime();

		if (input.getPressedKeys().size() > 0)
			elapsedTime = Double.MAX_VALUE; // to display everything
	}

	/**
	 * Define the function that is called at each display attempt.
	 * This function takes as an argument all the lines to display and the time elapsed since
	 * the first update of the actor, and returns a new array of the lines to display
	 * @param linesAdapter a function called at each display attempt
	 * @return the current textbox instance, for chained calls
	 */
	public TextBox setLinesAdapter(BiFunction<String[], Double, String[]> linesAdapter) {
		this.linesAdapter = linesAdapter;
		return this;
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output); // first we draw the box

		// Draw the text !
		double x = getBox().getMin().getX() + paddingLeft;
		double y = getBox().getMax().getY() - paddingTop;

		String[] lines = linesAdapter.apply(this.lines, elapsedTime);
		for (String line : lines) {
			if (line == null) { continue; }

			y -= lineHeight;
			output.drawText(line, new Vector(x, y), font, color);
			y -= lineSpacing;
		}

		// TODO : finish this
		// TODO : replace Tooltip with this
		// TODO : implement as slow and not slow
	}

	@Override
	public int getPriority() {
		return 100000;
	}
}
