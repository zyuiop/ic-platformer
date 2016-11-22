package platform.util;

import java.awt.*;
import platform.util.sounds.Sound;

/**
 * Provides a rendering context, with its own coordinate system (i.e. unit is not guaranteed to be the pixel, the implementation is free to scale objects).
 */
public interface Output {
	
    /** @return visible bounds, i.e. elements outside this area are not visible */
	public Box getBox();

    /**
     * Draws a sprite in specified area.
     * @param sprite image to render, not null
     * @param location rectangular area, not null
     */
	public void drawSprite(Sprite sprite, Box location);
    
    /**
     * Draw a sprite in specified area, rotated around the center.
     * @param sprite image to render, not null
     * @param location rectangular area, not null
     * @param angle counter-clockwise angle, in radians
     */
    public void drawSprite(Sprite sprite, Box location, double angle);
    
    /**
     * Draw an alpha-blended sprite in specified area, rotated around the center.
     * @param sprite image to render, not null
     * @param location rectangular area, not null
     * @param angle counter-clockwise angle, in radians
     * @param transparency transparency multiplier, between 0.0 and 1.0
     */
    public void drawSprite(Sprite sprite, Box location, double angle, double transparency);

	/**
	 * Display a text on the screen
	 * @param text the text to display, not null
	 * @param position the position where to display the text, not null
	 * @param font the font used to render the text, not null
	 * @param color the color of the text, not null
	 */
	void drawText(String text, Vector position, Font font, Color color);

	void drawBackground(Sprite sprite);

	void drawBackground(Sprite sprite, boolean repeatX, boolean repeatY);
}
