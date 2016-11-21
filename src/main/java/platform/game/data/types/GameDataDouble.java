package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author zyuiop
 */
public class GameDataDouble extends GameDataObject<Double> {
	private double value;

	GameDataDouble() {
	}

	public GameDataDouble(double value) {
		this.value = value;
	}

	@Override
	public int getObjectId() {
		return DATA_DOUBLE;
	}

	@Override
	public void read(DataInputStream stream) throws IOException {
		value = stream.readDouble();
	}

	@Override
	public void write(DataOutputStream stream) throws IOException {
		stream.writeDouble(value);
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}
}
