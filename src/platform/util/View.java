package platform.util;

/**
 * Transform viewport to render specific area of the world.
 */
public class View extends Transform {

    private double scale;
    private Vector translation;

    /**
     * Create a new view with identity transform.
     * @param input underlying input, not null
     * @param output underlying output, not null
     */
	public View(Input input, Output output) {
		super(input, output);
        scale = 1.0;
        translation = Vector.ZERO;
	}
    
    /**
     * Set viewport location and size.
     * @param center viewport center, not null
     * @param radius viewport radius, positive
     */
    public void setTarget(Vector center, double radius) {
        if (radius <= 0.0)
            throw new IllegalArgumentException();
        
        // Get window ratio
        Vector size = getOutput().getBox().getSize();
        double ratio = size.getY() / size.getX();
        
        // Use largest dimension as reference
        double length;
        Vector extent;
        if (ratio < 1.0) {
            length = size.getY();
            extent = new Vector(radius / ratio, radius);
        } else {
            length = size.getX();
            extent = new Vector(radius, radius * ratio);
        }
        
        // Compute scale and translation
        scale = 2.0 * radius / length;
        translation = center.sub(extent);
    }
    
    @Override
    public Vector convertToView(Vector x) {
        return x.mul(scale).add(translation);
    }
    
    @Override
    public Vector convertFromView(Vector x) {
        return x.sub(translation).div(scale);
    }
    
}
