package platform.util;

/**
 * Provides informations about input, such as time, mouse and keyboard.
 */
public interface Input {

    /** @return time elapsed since the game started, in seconds */
	public double getTime();
	
    /** @return time elapsed since last frame, in seconds */
	public double getDeltaTime();
	
    /** @return mouse location, in pixels, with origin at lower-left corner */
	public Vector getMouseLocation();
	
    /**
     * Gets state of specified mouse button.
     * @param index valid button identifier (1 - left, 2 - right, 3 - middle)
     * @return button state
     */
	public Button getMouseButton(int index);
	
    /** @return mouse wheel rotation units since last frame (positive is upward) */
	public int getMouseScroll();
	
    /**
     * Gets state of specified keyboard button.
     * @param code valid button identifier (see values in {@link java.awt.KeyEvent})
     * @return button state
     */
	public Button getKeyboardButton(int code);
	
    /** @return window user focus (i.e. down means focused) */
	public Button getFocus();
	
}
