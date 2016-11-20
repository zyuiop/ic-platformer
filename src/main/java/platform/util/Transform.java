package platform.util;

/**
 * Applies a simple coordinate transform.
 */
public abstract class Transform implements Input, Output {
    
    private Input input;
	private Output output;

    /**
     * Create a new view with identity transform.
     * @param input underlying input, not null
     * @param output underlying output, not null
     */
	public Transform(Input input, Output output) {
		if (input == null || output == null)
			throw new NullPointerException();
        this.input = input;
		this.output = output;
	}

    /** @return underlying input, not null */
    public Input getInput() {
        return input;
    }

    /** @return underlying output, not null */
    public Output getOutput() {
        return output;
    }
    
    /**
     * Transform point to view coordinates.
     * @param x point in underlying system coordinates, not null
     * @return point in view coordinates, not null
     */
    public abstract Vector convertToView(Vector x);
    
    /**
     * Transform box to view coordinates.
     * @param x box in underlying system coordinates, not null
     * @return box in view coordinates, not null
     */
    public Box convertToView(Box x) {
        return new Box(convertToView(x.getMin()), convertToView(x.getMax()));
    }
    
    /**
     * Transform point to underlying system coordinates.
     * @param x point in view coordinates, not null
     * @return point in underlying system coordinates, not null
     */
    public abstract Vector convertFromView(Vector x);
    
    /**
     * Transform box to underlying system coordinates.
     * @param x box in view coordinates, not null
     * @return box in underlying system coordinates, not null
     */
    public Box convertFromView(Box x) {
        return new Box(convertFromView(x.getMin()), convertFromView(x.getMax()));
    }
    
    @Override
	public Box getBox() {
		return convertToView(output.getBox());
	}
	
    @Override
    public double getTime() {
        return input.getTime();
    }

    @Override
    public double getDeltaTime() {
        return input.getDeltaTime();
    }

    @Override
    public Vector getMouseLocation() {
        return convertToView(input.getMouseLocation());
    }

    @Override
    public Button getMouseButton(int index) {
        return input.getMouseButton(index);
    }

    @Override
    public int getMouseScroll() {
        return input.getMouseScroll();
    }
    
    @Override
    public Button getKeyboardButton(int code) {
        return input.getKeyboardButton(code);
    }

    @Override
    public Button getFocus() {
        return input.getFocus();
    }
    
	@Override
	public void drawSprite(Sprite sprite, Box location) {
		output.drawSprite(sprite, convertFromView(location));
	}

    @Override
    public void drawSprite(Sprite sprite, Box location, double angle) {
        output.drawSprite(sprite, convertFromView(location), angle);
    }
    
    @Override
    public void drawSprite(Sprite sprite, Box location, double angle, double transparency) {
        output.drawSprite(sprite, convertFromView(location), angle, transparency);
    }
    
}
