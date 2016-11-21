package platform.game.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import platform.game.data.types.GameDataObject;

/**
 * @author zyuiop
 */
public class GameDataUtils {
	public static GameDataObject readDataFromFile(File file) throws IOException {
		DataInputStream stream = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
		return GameDataObject.readObject(stream);
	}

	public static void writeDataToFile(GameDataObject data, File file) throws IOException {
		DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
		GameDataObject.writeObject(data, stream);
	}
}
