package platform.util;

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
	
    
    
}
