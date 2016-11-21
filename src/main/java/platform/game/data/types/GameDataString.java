package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author zyuiop
 */
public class GameDataString extends GameDataObject<String> {
	private String value;

	public GameDataString(String value) {
		this.value = value;
	}

	GameDataString() {
	}

	@Override
	public int getObjectId() {
		return DATA_STRING;
	}

	@Override
	public void read(DataInputStream stream) throws IOException {
		value = stream.readUTF();
	}

	@Override
	public void write(DataOutputStream stream) throws IOException {
		stream.writeUTF(value);
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
