package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class GameDataBox extends GameDataObject<Box> {
	private Box value;

	GameDataBox() {
	}

	public GameDataBox(Box value) {
		this.value = value;
	}

	@Override
	public int getObjectId() {
		return DATA_BOX;
	}

	@Override
	public void read(DataInputStream stream) throws IOException {
		double d1 = stream.readDouble();
		double d2 = stream.readDouble();
		double width = stream.readDouble();
		double height = stream.readDouble();

		value = new Box(new Vector(d1, d2), width, height);
	}

	@Override
	public void write(DataOutputStream stream) throws IOException {
		stream.writeDouble(value.getCenter().getX());
		stream.writeDouble(value.getCenter().getY());
		stream.writeDouble(value.getWidth());
		stream.writeDouble(value.getHeight());
	}

	@Override
	public Box getValue() {
		return value;
	}

	@Override
	public void setValue(Box value) {
		this.value = value;
	}
}
