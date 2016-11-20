package platform.util;

/**
 * Represents an immutable bounding-box, defined by a minimum and maximum.
 */
public final class Box {

    /** The empty box */
	public static final Box EMPTY = new Box(Vector.ZERO, Vector.ZERO);
	
	private final Vector min, max;
	
    /**
     * Create a new box.
     * @param min lower-left corner, not null
     * @param max upper-right corner, not null
     */
	public Box(Vector min, Vector max) {
        if (min.getX() > max.getX() || min.getY() > max.getY())
            throw new IllegalArgumentException();
		this.min = min;
		this.max = max;
	}
    
    /**
     * Create a new box.
     * @param center middle of the box, not null
     * @param width horizontal size
     * @param height vertical size
     */
    public Box(Vector center, double width, double height) {
        this.min = new Vector(center.getX() - width * 0.5, center.getY() - height * 0.5);
        this.max = new Vector(center.getX() + width * 0.5, center.getY() + height * 0.5);
    }
	
    /** @return lower-left corner */
	public Vector getMin() {
		return min;
	}

    /** @return upper-right corner */
	public Vector getMax() {
		return max;
	}
	
    /** @return middle point */
	public Vector getCenter() {
		return min.add(max).mul(0.5);
	}
    
    /** @return horizontal size */
    public double getWidth() {
        return getSize().getX();
    }
    
    /** @return vertical size */
    public double getHeight() {
        return getSize().getY();
    }
	
    /** @return width and height */
	public Vector getSize() {
		return max.sub(min);
	}
    
    /** @return radius of largest circle inside box */
    public double getInnerRadius() {
        Vector size = getSize();
        return Math.min(size.getX(), size.getY());
    }
    
    /** @return radius of smallest circle outside box */
    public double getOuterRadius() {
        return getSize().getLength() * 0.5;
    }
    
	@Override
	public int hashCode() {
		return min.hashCode() ^ max.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || !(object instanceof Box))
			return false;
		Box other = (Box)object;
		return min.equals(other.min) && max.equals(other.max);
	}
	
	@Override
	public String toString() {
		return min + "->" + max;
	}
    
    /**
     * @param point value, not null
     * @return whether specified point is inside the box
     */
	public boolean isColliding(Vector point) {
        if (point == null)
            return false;
		return point.getX() > min.getX() && point.getX() < max.getX() && point.getY() > min.getY() && point.getY() < max.getY(); 
	}
	
    /**
     * @param box value, not null
     * @return whether specified box intersects this rectangle
     */
	public boolean isColliding(Box box) {
        if (box == null)
            return false;
		Vector min = this.min.max(box.min);
		Vector max = this.max.min(box.max);
		return min.getX() < max.getX() && min.getY() < max.getY();
	}
	
    /**
     * @param point value, not null
     * @return smallest direction to move the point outside this box
     */
	public Vector getCollision(Vector point) {
		return getCollision(new Box(point, point));
	}
	
    /**
     * @param box value, not null
     * @return smallest direction to move the box outside this box
     */
	public Vector getCollision(Box box) {
		double left = box.getMax().getX() - min.getX();
		if (left <= 0.0)
			return null;
		double right = max.getX() - box.getMin().getX();
		if (right <= 0.0)
			return null;
		double bottom = box.getMax().getY() - min.getY();
		if (bottom <= 0.0)
			return null;
		double top = max.getY() - box.getMin().getY();
		if (top <= 0.0)
			return null;
		double horizontal = Math.min(left, right);
		double vertical = Math.min(bottom, top);
		if (horizontal < vertical)
			return new Vector(left < right ? -left : right, 0.0);
		return new Vector(0.0, bottom < top ? -bottom : top);
	}
	
    /**
     * Translates this box.
     * @param other delta to apply, not null
     * @return new box, not null
     */
	public Box add(Vector other) {
		return new Box(min.add(other), max.add(other));
	}
	
    /**
     * Translates this box.
     * @param other delta to apply, not null
     * @return new box, not null
     */
	public Box sub(Vector other) {
		return new Box(min.sub(other), max.sub(other));
	}
	
}
