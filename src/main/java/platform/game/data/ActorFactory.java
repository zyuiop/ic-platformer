package platform.game.data;

import java.util.HashMap;
import java.util.Map;
import platform.game.Signal;
import platform.game.data.types.GameDataMap;
import platform.game.level.Level;

/**
 * @author zyuiop
 */
public class ActorFactory {
	private GameDataMap dataMap = new GameDataMap();
	private Map<String, Signal> signalMap = new HashMap<>();
	private Map<String, Level> levelsMap = new HashMap<>();

	public ActorFactory(GameDataMap dataMap) {
		this.dataMap = dataMap;
	}

	public ActorFactory() {
	}

	public Signal getSignal(String fieldName) {
		if (!dataMap.containsKey(fieldName)) { return null; }

		String signalName = dataMap.get(fieldName).getAsString();
		return signalMap.get(signalName); // can be null
	}

	public Level getLevel(String fieldName) {
		if (!dataMap.containsKey(fieldName)) { return null; }

		String levelName = dataMap.get(fieldName).getAsString();
		return levelsMap.get(levelName); // can be null
	}

	public void registerSignal(String name, Signal signal) {
		this.signalMap.put(name, signal);
	}

	public GameDataMap getDataMap() {
		return dataMap;
	}
}
