package platform.util;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Dummy loader that always provides a 1x1 red sprite.
 */
public enum DefaultLoader implements Loader {
    
    /** The singleton instance */
    INSTANCE;
    
    private final Sprite sprite;
    
    private DefaultLoader() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, Color.RED.getRGB());
        sprite = new Sprite(image);
    }

    @Override
    public Sprite getSprite(String name) {
        return sprite;
    }
    
}
