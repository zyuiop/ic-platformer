package platform.util;

import java.awt.Color;

/**
 * Combines Output and Input interfaces.
 * @see Output
 * @see Input
 */
public interface Display extends Output, Input {

    /**
     * Sets background color.
     * @param color any color, not null
     */
	void setBackground(Color color);
    
    /** Initiates new frame rendering */
	void begin();
    
    /** Finalizes the current frame and displays the result on the screen */
	void end();
    
    /** Closes window and releases resources */
	void close();
    
    /** @return whether the user requested to close the window */
	boolean isCloseRequested();
}