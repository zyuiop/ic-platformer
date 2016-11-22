package platform.game.actors.animations;

import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class DisappearingParticle extends SimpleParticle {
	private double startTransparency = 1;
	private double endTransparency = 0;

	public DisappearingParticle(String spriteName, double size, Vector position, double fadeIn, double fadeOut, double stay, double startTransparency, double endTransparency) {
		super(spriteName, size, position, fadeIn, fadeOut, stay, startTransparency);
		this.startTransparency = startTransparency;
		this.endTransparency = endTransparency;
	}

	public DisappearingParticle(Sprite sprite, double size, Vector position, double fadeIn, double fadeOut, double stay, double startTransparency, double endTransparency) {
		super(sprite, size, position, fadeIn, fadeOut, stay, startTransparency);
		this.startTransparency = startTransparency;
		this.endTransparency = endTransparency;
	}

	public DisappearingParticle(String spriteName, double sizeX, double sizeY, Vector position, double fadeIn, double fadeOut, double stay, double startTransparency, double endTransparency) {
		super(spriteName, sizeX, sizeY, position, fadeIn, fadeOut, stay, startTransparency);
		this.startTransparency = startTransparency;
		this.endTransparency = endTransparency;
	}

	public DisappearingParticle(Sprite sprite, double sizeX, double sizeY, Vector position, double fadeIn, double fadeOut, double stay, double startTransparency, double endTransparency) {
		super(sprite, sizeX, sizeY, position, fadeIn, fadeOut, stay, startTransparency);
		this.startTransparency = startTransparency;
		this.endTransparency = endTransparency;
	}

	@Override
	public int getPriority() {
		return 5000; // au dessus de pas mal de choses
	}

	protected double calcTransparency() {

		if (getTime() < getFadeIn()) {
			return startTransparency * (getTime() / getFadeIn());
		} else if (getTime() >= getFadeIn() + getStay()) {
			return endTransparency - ((getTime() - getFadeIn() - getStay()) / getFadeOut()) * endTransparency;
		} else {
			double gradient = (startTransparency - endTransparency) / getStay();
			double transparency = startTransparency + gradient * (getTime() - getFadeIn());

			if (transparency < 0)
				transparency = Math.min(startTransparency, endTransparency);
			else if (transparency > 1)
				transparency = Math.max(startTransparency, endTransparency);

			return transparency;
		}
	}

}
