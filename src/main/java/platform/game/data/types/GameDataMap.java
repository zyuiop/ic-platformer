package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class GameDataMap extends GameDataObject<Map<String, GameDataObject>> {
	private Map<String, GameDataObject> value = new HashMap<>();

	@Override
	public Map<String, GameDataObject> getValue() {
		return value;
	}

	@Override
	public void setValue(Map<String, GameDataObject> value) {
		this.value = value;
	}

	public GameDataObject get(String key) {
		return value.get(key);
	}

	public void put(String key, GameDataObject value) {
		this.value.put(key, value);
	}

	public void put(String key, String value) {
		put(key, new GameDataString(value));
	}

	public void put(String key, int value) {
		put(key, new GameDataInt(value));
	}

	public void put(String key, double value) {
		put(key, new GameDataDouble(value));
	}

	public void put(String key, Vector value) {
		put(key, new GameDataVector(value));
	}

	public void put(String key, Box box) {
		put(key, new GameDataBox(box));
	}

	public boolean containsKey(String key) {
		return value.containsKey(key);
	}

	private NamedDataTag readNamedObject(DataInputStream stream) throws IOException {
		int type = stream.readByte() & 0xFF;
		GameDataObject object = createObject(type);
		if (object == null)
			throw new IOException("Found unknown data type : " + type);

		if (object instanceof GameDataEnd)
			return new NamedDataTag(null, object); // we don't care about the name for the end tag

		String name = stream.readUTF();
		object.read(stream);
		return new NamedDataTag(name, object);
	}

	@Override
	protected void read(DataInputStream stream) throws IOException {
		while (true) {
			NamedDataTag object = readNamedObject(stream);

			if (object.getObject() instanceof GameDataEnd)
				break;

			value.put(object.getName(), object.getObject());
		}
	}

	private void writeNamedObject(NamedDataTag object, DataOutputStream stream) throws IOException {
		int id = object.getObject().getObjectId();
		if (id < 0 || id > 7)
			throw new IllegalArgumentException("invalid object : unknown type id " + id);
		stream.writeByte(id);

		if (!(object.getObject() instanceof GameDataEnd)) {
			stream.writeUTF(object.getName());
			object.getObject().write(stream);
		}
	}

	@Override
	protected void write(DataOutputStream stream) throws IOException {
		for (Map.Entry<String, GameDataObject> entry : value.entrySet()) {
			writeNamedObject(new NamedDataTag(entry.getKey(), entry.getValue()), stream);
		}

		writeObject(new GameDataEnd(), stream); // end of the map
	}

	@Override
	public int getObjectId() {
		return DATA_MAP;
	}

}
