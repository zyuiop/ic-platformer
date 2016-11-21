package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author zyuiop
 */
public class GameDataInt extends GameDataObject<Integer> {
	private int value;

	public GameDataInt(int value) {
		this.value = value;
	}

	GameDataInt() {
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public void read(DataInputStream stream) throws IOException {
		value = stream.readInt();
	}

	@Override
	public void write(DataOutputStream stream) throws IOException {
		stream.writeInt(value);
	}

	@Override
	public int getObjectId() {
		return DATA_INT;
	}
}
