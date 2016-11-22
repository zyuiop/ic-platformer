package platform.util.sounds;

/**
 * Provides sound loading by name.
 * @see Sound
 */
public interface SoundLoader {
    SoundLoader DUMMY_LOADER = (name) -> ((d) -> {});
    
    /**
     * Gets the sound associated to specified name.
     * @param name identifier, not null
     * @return valid sound, not null
     */
    Sound getSound(String name);
    
}
