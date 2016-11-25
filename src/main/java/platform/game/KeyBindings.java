package platform.game;

import platform.util.Button;
import platform.util.Input;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author zyuiop
 * This class manages used-configured key bindings
 */
public class KeyBindings {
	private File file;
	private Properties properties = new Properties();
	private Map<Key, Collection<Integer>> keyBinds = new HashMap<>();
	private static KeyBindings instance;

	/**
	 * Create an instance of the KeyBindings manager, giving it the file in which the keys are saved
	 * @param file the file identifier of the properties file
	 */
	public KeyBindings(File file) {
		this.file = file;
		instance = this;
	}

	/**
	 * Get the current KeyBindings instance. May return null if no instance of this class have been
	 * created.
	 * @return the current KeyBindings instance
	 */
	public static KeyBindings getInstance() {
		return instance;
	}

	/**
	 * Load default key bindings and user-configured ones, if the file exists.
	 * @throws IOException if an error happens while reading the file
	 */
	public void load() throws IOException {
		load(file);
		populateKeybinds();
		loadKeybindsFromFile();
	}

	/**
	 * Save the user keybinds to the file
	 * @throws IOException if an error happens while writing the file
	 */
	public void save() throws IOException {
		verifyKeys();
		saveKeybindsToFile();
		save(file);
	}

	/**
	 * Get all the bindings for a key
	 * @param key the key to check
	 * @return the bindings for this key
	 */
	public Collection<Integer> getKeys(Key key) {
		return keyBinds.get(key);
	}

	/**
	 * Add a binding for a key
	 * @param key the modified key
	 * @param button the new binding to add
	 */
	public void addKey(Key key, Integer button) {
		if (!keyBinds.get(key).contains(button))
			keyBinds.get(key).add(button);
	}

	public String getFirstConfiguredKey(Key key) {
		Collection<Integer> collection = getKeys(key);
		if (collection.size() > 0) {
			return KeyEvent.getKeyText(collection.iterator().next());
		}
		return key.getDescription();
	}

	/**
	 * Remove a binding for a key
	 * @param key the modified key
	 * @param button the binding to remove
	 */
	public void removeKey(Key key, Integer button) {
		keyBinds.get(key).remove(button);
	}

	/**
	 * Call this method after editing keys and before saving. It will populate missing keys.
	 */
	public void verifyKeys() {
		for (Key k : Key.values())
			keyCheck(k);
	}

	/**
	 * Check if a given key has bindings
	 * @param key the key to check
	 */
	private void keyCheck(Key key) {
		if (keyBinds.get(key) == null || keyBinds.get(key).isEmpty()) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int def : key.getDefaultKeys())
				list.add(def);

			keyBinds.put(key, list);
		}
	}

	/**
	 * Check if a given key is pressed
	 * @param input the user input
	 * @param key the key to check
	 * @return true if the key is pressed
	 * @see Button#isPressed()
	 */
	public boolean isPressed(Input input, Key key) {
		for (Integer k : keyBinds.get(key)) {
			if (input.getKeyboardButton(k).isPressed()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Check if a given key is down
	 * @param input the user input
	 * @param key the key to check
	 * @return true if the key is down
	 * @see Button#isDown()
	 */
	public boolean isDown(Input input, Key key) {
		for (Integer k : keyBinds.get(key)) {
			if (input.getKeyboardButton(k).isDown())
				return true;
		}
		return false;
	}

	private void save(File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		properties.store(writer, "Saved by platformGame");
		writer.flush();
		writer.close();
	}

	private void load(File file) throws IOException {
		if (!file.exists())
			return;
		FileReader reader = new FileReader(file);
		properties.load(reader);
		reader.close();
	}

	private void populateKeybinds() {
		for (Key key : Key.values()) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int def : key.getDefaultKeys())
				list.add(def);

			keyBinds.put(key, list);
		}
	}

	private void loadKeybindsFromFile() {
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();

			key = key.toUpperCase();
			try {
				Key k = Key.valueOf(key);
				Collection<Integer> collection = fromKeyString(value);

				if (collection.size() > 0) {
					keyBinds.put(k, collection); // if empty collection we leave the default config
				}
			} catch (IllegalArgumentException ignored) {
				// no such key, modified config ?
			}
		}
	}

	private void saveKeybindsToFile() {
		for (Entry<Key, Collection<Integer>> entry : keyBinds.entrySet()) {
			String key = entry.getKey().name().toLowerCase();
			String value = toKeyString(entry.getValue());

			properties.setProperty(key, value);
		}
	}

	private String toKeyString(Collection<Integer> keys) {
		StringBuilder builder = new StringBuilder();
		for (int i : keys)
			builder.append(i).append(",");
		builder.deleteCharAt(builder.length() - 1); // dernière virgule
		return builder.toString();
	}

	private Collection<Integer> fromKeyString(String keys) {
		String[] data = keys.split(",");
		List<Integer> target = new ArrayList<>();

		for (String key : data) {
			if (key.length() > 0) {
				try {
					target.add(Integer.parseInt(key));
				} catch (NumberFormatException ignored) {
					// someone edited the configuration manually
				}
			}
		}

		return target;
	}

	/**
	 * An enumeration representing all available action keys
	 */
	public enum Key {
		/**
		 * A key used to jump
		 */
		JUMP("Sauter", KeyEvent.VK_UP, KeyEvent.VK_Z),
		/**
		 * A key used to go left
		 */
		LEFT("Gauche", KeyEvent.VK_LEFT, KeyEvent.VK_Q),
		/**
		 * A key used to go right
		 */
		RIGHT("Droite", KeyEvent.VK_RIGHT, KeyEvent.VK_D),
		/**
		 * A key used to use an item or block
		 */
		USE("Utiliser", KeyEvent.VK_E),
		/**
		 * A key used to attack
		 */
		ATTACK("Attaquer", KeyEvent.VK_SPACE),
		/**
		 * A key used to blow
		 */
		BLOW("Souffler", KeyEvent.VK_B),
		/**
		 * A key used to open a menu
		 */
		MENU("Menu", KeyEvent.VK_ESCAPE);

		private int[] defaultKeys;
		private String description;

		Key(int... defaultKeys) {
			this.defaultKeys = defaultKeys;
		}

		Key(String description, int... defaultKeys) {
			this.defaultKeys = defaultKeys;
			this.description = description;
		}

		/**
		 * Get the default bindings for this Key
		 * @return an array of bindings
		 */
		public int[] getDefaultKeys() {
			return defaultKeys;
		}

		/**
		 * Get the human readable description of this key
		 * @return a string explaining shortly what this key does
		 */
		public String getDescription() {
			return description;
		}
	}
}
