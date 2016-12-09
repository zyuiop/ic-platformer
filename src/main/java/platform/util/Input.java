package platform.util;

import java.util.Collection;

/**
 * Provides informations about input, such as time, mouse and keyboard.
 */
public interface Input {

    /** @return time elapsed since the game started, in seconds */
	double getTime();
	
    /** @return time elapsed since last frame, in seconds */
	double getDeltaTime();
	
    /** @return mouse location, in pixels, with origin at lower-left corner */
	Vector getMouseLocation();
	
    /**
     * Gets state of specified mouse button.
     * @param index valid button identifier (1 - left, 2 - right, 3 - middle)
     * @return button state
     */
	Button getMouseButton(int index);

	Collection<Integer> getPressedKeys();

	/** @return mouse wheel rotation units since last frame (positive is upward) */
	int getMouseScroll();
	
    /**
     * Gets state of specified keyboard button.
     * @param code valid button identifier (see values in {@link java.awt.event.KeyEvent})
     * @return button state
     */
	Button getKeyboardButton(int code);
	
    /** @return window user focus (i.e. down means focused) */
	Button getFocus();
	
}
