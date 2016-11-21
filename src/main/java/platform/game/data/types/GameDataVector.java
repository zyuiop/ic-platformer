package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class GameDataVector extends GameDataObject<Vector> {
	private Vector value;

	GameDataVector() {
	}

	public GameDataVector(Vector value) {
		this.value = value;
	}

	@Override
	public int getObjectId() {
		return DATA_VECTOR;
	}

	@Override
	public void read(DataInputStream stream) throws IOException {
		double d1 = stream.readDouble();
		double d2 = stream.readDouble();

		value = new Vector(d1, d2);
	}

	@Override
	public void write(DataOutputStream stream) throws IOException {
		stream.writeDouble(value.getX());
		stream.writeDouble(value.getY());
	}

	@Override
	public Vector getValue() {
		return value;
	}

	@Override
	public void setValue(Vector value) {
		this.value = value;
	}
}
