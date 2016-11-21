package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import platform.util.Box;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public abstract class GameDataObject<T> {
	public static final int DATA_END = 0;
	public static final int DATA_INT = 1;
	public static final int DATA_DOUBLE = 2;
	public static final int DATA_STRING = 3;
	public static final int DATA_VECTOR = 4;
	public static final int DATA_LIST = 5;
	public static final int DATA_MAP = 6;
	public static final int DATA_BOX = 7;

	public abstract int getObjectId();

	protected abstract void read(DataInputStream stream) throws IOException;

	protected abstract void write(DataOutputStream stream) throws IOException;

	public abstract T getValue();

	public abstract void setValue(T value);

	public static GameDataObject readObject(DataInputStream stream) throws IOException {
		int type = stream.readByte() & 0xFF;
		GameDataObject object = createObject(type);
		if (object == null)
			throw new IOException("Found unknown data type : " + type);
		object.read(stream);
		return object;
	}

	public static void writeObject(GameDataObject object, DataOutputStream stream) throws IOException {
		int id = object.getObjectId();
		if (id < 0 || id > 7)
			throw new IllegalArgumentException("invalid object : unknown type id " + id);
		stream.writeByte(id);
		object.write(stream);
	}

	protected static GameDataObject createObject(int type) {
		switch (type) {
			case DATA_END:
				return new GameDataEnd();
			case DATA_INT:
				return new GameDataInt();
			case DATA_DOUBLE:
				return new GameDataDouble();
			case DATA_STRING:
				return new GameDataString();
			case DATA_VECTOR:
				return new GameDataVector();
			case DATA_LIST:
				return new GameDataList();
			case DATA_MAP:
				return new GameDataMap();
			case DATA_BOX:
				return new GameDataBox();
			default:
				return null;
		}
	}

	public int getAsInt() {
		return (int) getValue();
	}

	public double getAsDouble() {
		return (double) getValue();
	}

	public String getAsString() {
		return (String) getValue();
	}

	public Vector getAsVector() {
		return (Vector) getValue();
	}

	public Box getAsBox() {
		return (Box) getValue();
	}

}
