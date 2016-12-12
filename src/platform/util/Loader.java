package platform.util;

/**
 * Provides sprite loading by name.
 * @see Sprite
 */
public interface Loader {
    
    /**
     * Gets the sprite associated to specified name.
     * @param name identifier, not null
     * @return valid sprite, not null
     */
	Sprite getSprite(String name);
    
}
