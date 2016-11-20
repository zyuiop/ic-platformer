package platform.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides caching capabilities to avoid multiple sprite fetch.
 */
public class BufferedLoader implements Loader {

    private Map<String, Sprite> sprites;
    private Loader loader;
    
    /**
     * Creates a new buffered loader.
     * @param loader underlying loader, not null
     */
    public BufferedLoader(Loader loader) {
        if (loader == null)
            throw new NullPointerException();
        sprites = new HashMap<>();
        this.loader = loader;
    }
    
    @Override
    public Sprite getSprite(String name) {
        Sprite sprite = sprites.get(name);
        if (sprite == null) {
            sprite = loader.getSprite(name);
            sprites.put(name, sprite);
        }
        return sprite;
    }
    
}
