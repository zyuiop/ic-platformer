package platform.game.data.types;

/**
 * @author zyuiop
 */
class NamedDataTag {
	private String name;
	private GameDataObject object;

	public NamedDataTag(String name, GameDataObject object) {
		this.name = name;
		this.object = object;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameDataObject getObject() {
		return object;
	}

	public void setObject(GameDataObject object) {
		this.object = object;
	}
}
