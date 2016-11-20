package platform.util;

import java.io.IOException;

/**
 * Loads sprites from specified folder, guessing necessary file extensions.
 */
public class FileLoader implements Loader {
    
    private static final String[] EXTENSIONS = {
        "", ".png", ".jpg", ".jpeg", ".bmp"
    };
    
    private String prefix;
    private Loader fallback;
    
    /**
     * Creates a new file loader.
     * @param prefix path prefix used to form absolute or relative path when concatenated with an identifier, not null
     * @param fallback loader used in case of error, not null
     */
    public FileLoader(String prefix, Loader fallback) {
        if (prefix == null || fallback == null)
            throw new NullPointerException();
        this.prefix = prefix;
        this.fallback = fallback;
    }
    
    @Override
    public Sprite getSprite(String name) {
        Sprite sprite = null;
        
        // Try each extension, until we are able to successfully read the file
        for (String extension : EXTENSIONS) {
            try {
                sprite = new Sprite(prefix + name + extension);
                break;
            } catch (IOException e) {}
        }
        
        // On failure, use fallback loader
        if (sprite == null)
            sprite = fallback.getSprite(name);
        return sprite;
    }
    
}
