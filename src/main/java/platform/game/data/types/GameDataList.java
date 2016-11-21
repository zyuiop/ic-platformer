package platform.game.data.types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyuiop
 */
public class GameDataList<T extends GameDataObject> extends GameDataObject<List<T>> {
	private List<T> objectList = new ArrayList<>();
	private int dataType = 0;

	GameDataList() {
	}

	public GameDataList(int dataType) {
		this.dataType = dataType;
	}

	public List<T> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<T> objectList) {
		this.objectList = objectList;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	@Override
	public int getObjectId() {
		return DATA_LIST;
	}

	@Override
	public void read(DataInputStream stream) throws IOException {
		dataType = stream.readByte() & 0xFF; // the subtype of the list
		int length = stream.readInt(); // the size of the list

		objectList.clear();

		for (int i = 0; i < length; ++i) {
			GameDataObject object = createObject(dataType);
			if (object == null)
				throw new IOException("Unknown list subtype " + dataType);
			object.read(stream);
			objectList.add((T) object);
		}
	}

	@Override
	public void write(DataOutputStream stream) throws IOException {
		if (dataType == 0)
			throw new IOException("the list datatype cannot be 0");

		stream.writeByte(dataType);
		stream.writeInt(objectList.size());

		for (GameDataObject object : getObjectList()) {
			object.write(stream);
		}
	}

	@Override
	public List<T> getValue() {
		return getObjectList();
	}

	@Override
	public void setValue(List<T> value) {
		setObjectList(value);
	}
}
