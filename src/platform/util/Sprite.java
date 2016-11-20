package platform.util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * An immutable RGBA image.
 */
public final class Sprite {
    
	private final BufferedImage image;
	
    /**
     * Creates a sprite from specified image.
     * @param image valid image to be copied, not null
     */
	public Sprite(Image image) {
		// See
		// http://stackoverflow.com/questions/196890/java2d-performance-issues
		// http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
		// http://stackoverflow.com/questions/148478/java-2d-drawing-optimal-performance
		
		// Get system graphical configuration
		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		// Get image size
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		
		// Create optimized buffered image
		this.image = config.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		
		// Draw original image in buffer
		Graphics2D graphics = this.image.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
	}
	
    /**
     * Creates a sprite from specified image file.
     * @param file valid image file identifier, not null
     * @throws IOException if an error occurs during reading
     */
	public Sprite(File file) throws IOException {
		this(ImageIO.read(file));
	}
	
    /**
     * Creates a sprite from specified image file.
     * @param path valid image file path, not null
     * @throws IOException if an error occurs during reading
     */
	public Sprite(String path) throws IOException {
		this(new File(path));
	}
	
    /** @return underlying buffered image, not null */
	public BufferedImage getImage() {
		return image;
	}
	
    /** @return image width, in pixels */
	public int getWidth() {
		return image.getWidth();
	}
	
    /** @return image height, in pixels */
	public int getHeight() {
		return image.getHeight();
	}
	
}
