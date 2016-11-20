package platform.util;

/**
 * Enumeration of all possible states of a button.
 */
public enum Button {

    /** The button is released for more than one frame */
	UP,
    
    /** The button was just released */
	RELEASED,
    
    /** The button was just pressed */
	PRESSED,
    
    /** The button was pressed for more than one frame */
	DOWN;
	
    /**
     * Compute the new state, after one frame.
     * @param down whether the button was down during last frame
     * @return updated state
     */
	public Button updated(boolean down) {
		if (this == UP || this == RELEASED)
			return down ? PRESSED : UP;
		return down ? DOWN : RELEASED;
	}
	
    /** @return whether the button is released */
	public boolean isUp() {
		return this == UP || this == RELEASED;
	}
    
    /** @return whether the button was just released */
    public boolean isReleased() {
        return this == RELEASED;
    }
    
    /** @return whether the button was just pressed */
    public boolean isPressed() {
        return this == PRESSED;
    }
	
    /** @return whether the button is pressed */
	public boolean isDown() {
		return this == PRESSED || this == DOWN;
	}
	
}
