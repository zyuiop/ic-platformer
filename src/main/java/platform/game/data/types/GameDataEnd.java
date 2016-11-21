package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author zyuiop
 */
public class GameDataEnd extends GameDataObject<Void> {
	@Override
	public int getObjectId() {
		return DATA_END;
	}

	@Override
	public void read(DataInputStream stream) {
		// do nothing
	}

	@Override
	public void write(DataOutputStream stream) throws IOException {
		// do nothing
	}

	@Override
	public Void getValue() {
		return null;
	}

	@Override
	public void setValue(Void value) {

	}
}
