package platform.util;

import java.util.HashMap;
import java.util.Map;
import platform.util.sounds.Sound;
import platform.util.sounds.SoundLoader;

/**
 * Provides caching capabilities to avoid multiple sprite fetch.
 */
public class BufferedLoader implements Loader, SoundLoader {

    private Map<String, Sprite> sprites;
    private Map<String, Sound> sounds;
    private Loader loader;
    private SoundLoader soundLoader;
    
    /**
     * Creates a new buffered loader.
     * @param loader underlying loader, not null
     */
    public BufferedLoader(Loader loader, SoundLoader soundLoader) {
        if (loader == null)
            throw new NullPointerException();
        sprites = new HashMap<>();
        sounds = new HashMap<>();
        this.loader = loader;
        this.soundLoader = soundLoader;
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

    @Override
    public Sound getSound(String name) {
        Sound sound = sounds.get(name);
        if (sound == null) {
            sound = soundLoader.getSound(name);
            if (sound != null) {
                sounds.put(name, sound);
            }
        }
        return sound;
    }
}
