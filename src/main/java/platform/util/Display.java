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
    public void setBackground(Color color);
    
    /** Initiates new frame rendering */
    public void begin();
    
    /** Finalizes the current frame and displays the result on the screen */
    public void end();
    
    /** Closes window and releases resources */
    public void close();
    
    /** @return whether the user requested to close the window */
    public boolean isCloseRequested();
    
}