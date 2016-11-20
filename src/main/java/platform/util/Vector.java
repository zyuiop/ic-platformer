package platform.util;

/**
 * Represents an immutable 2D floating-point vector.
 */
public final class Vector {
	
    /** The zero vector (0, 0) */
	public static final Vector ZERO = new Vector(0.0, 0.0);
    
    /** The unit X vector (1, 0) */
    public static final Vector X = new Vector(1.0, 0.0);
    
    /** The unit Y vector (0, 1) */
    public static final Vector Y = new Vector(0.0, 1.0);
	
	private final double x, y;

    /**
     * Create a new vector.
     * @param x abscissa
     * @param y ordinate
     */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

    /** @return abscissa */
	public double getX() {
		return x;
	}

    /** @return ordinate */
	public double getY() {
		return y;
	}
    
    /** @return euclidian length */
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }
    
    /** @return angle in standard trigonometrical system, in radians */
    public double getAngle() {
        return Math.atan2(y, x);
    }

	@Override
	public int hashCode() {
		return Double.hashCode(x) ^ Double.hashCode(y);
	}

	@Override
	public boolean equals(Object object) {
		if (object == null || !(object instanceof Vector))
			return false;
		Vector other = (Vector)object;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
    /** @return negated vector */
	public Vector opposite() {
		return new Vector(-x, -y);
	}
	
    /**
     * @param other right-hand operand, not null
     * @return sum, not null
     */
	public Vector add(Vector other) {
		return new Vector(x + other.x, y + other.y);
	}
	
    /**
     * @param other right-hand operand, not null
     * @return difference, not null
     */
	public Vector sub(Vector other) {
		return new Vector(x - other.x, y - other.y);
	}
	
    /**
     * @param other right-hand operand
     * @return scaled vector, not null
     */
	public Vector mul(double other) {
		return new Vector(x * other, y * other);
	}
	
    /**
     * @param other right-hand operand, not null
     * @return component-wise multiplication, not null
     */
	public Vector mul(Vector other) {
		return new Vector(x * other.x, y * other.y);
	}
	
    /**
     * @param other right-hand operand
     * @return scaled vector, not null
     */
	public Vector div(double other) {
		return new Vector(x / other, y / other);
	}
	
    /**
     * @param other right-hand operand, not null
     * @return component-wise division, not null
     */
	public Vector div(Vector other) {
		return new Vector(x / other.x, y / other.y);
	}
    
    /**
     * @param other right-hand operand, not null
     * @return dot product
     */
    public double dot(Vector other) {
        return x * other.x + y * other.y;
    }
    
    /**
     * @param other right-hand operand, not null
     * @return component-wise minimum, not null
     */
	public Vector min(Vector other) {
		return new Vector(Math.min(x, other.x), Math.min(y, other.y));
	}
	
    /**
     * @param other right-hand operand, not null
     * @return component-wise maximum, not null
     */
	public Vector max(Vector other) {
		return new Vector(Math.max(x, other.x), Math.max(y, other.y));
	}
    
    /** @return smallest component */
    public double min() {
        return Math.min(x, y);
    }
    
    /** @return largest component */
    public double max() {
        return Math.max(x, y);
    }
    
    /**
     * Computes unit vector of same direction, or (1, 0) if zero.
     * @return rescaled vector, not null
     */
    public Vector normalized() {
        double length = getLength();
        if (length > 1e-6)
            return div(length);
        return Vector.X;
    }
    
    /**
     * Resizes vector to specified length, or (<code>length</code>, 0) if zero.
     * @param length new length
     * @return rescaled vector, not null
     */
    public Vector resized(double length) {
        return normalized().mul(length);
    }
    
    /**
     * Computes mirrored vector, with respect to specified normal.
     * @param normal vector perpendicular to the symmetry plane, not null
     * @return rotated vector, not null
     */
    public Vector mirrored(Vector normal) {
        normal = normal.normalized();
        return sub(normal.mul(2.0 * dot(normal)));
    }
	
    /**
     * Computes rotated vector, in a counter-clockwise manner.
     * @param angle rotation, in radians
     * @return rotated vector, not null
     */
	public Vector rotated(double angle) {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        return new Vector(x * c - y * s, x * s + y * c);
    }
    
    /**
     * Computes linear interpolation between two vectors.
     * @param other second vector, not null
     * @param factor weight of the second vector
     * @return interpolated vector, not null
     */
    public Vector mixed(Vector other, double factor) {
        return new Vector(x * (1.0 - factor) + other.x * factor, y * (1.0 - factor) + other.y * factor);
    }
	
}
