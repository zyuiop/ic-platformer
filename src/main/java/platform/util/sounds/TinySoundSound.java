package platform.util.sounds;

/**
 * @author zyuiop
 */
public class TinySoundSound implements Sound {
	private final kuusisto.tinysound.Sound sound;

	TinySoundSound(kuusisto.tinysound.Sound sound) {
		this.sound = sound;
	}

	@Override
	public void play(double v) {
		sound.play(v);
	}
}
