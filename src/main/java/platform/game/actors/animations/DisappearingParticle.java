package platform.game.actors.animations;

import platform.util.Vector;

/**
 * @author zyuiop
 */
public class DisappearingParticle extends SimpleParticle {
	private double startTransparency = 1;
	private double endTransparency = 0;

	public DisappearingParticle(Vector position, double size, String spriteName, double fadeIn, double fadeOut, double stay, double startTransparency, double endTransparency) {
		super(position, size, spriteName, fadeIn, fadeOut, stay, startTransparency);
		this.startTransparency = startTransparency;
		this.endTransparency = endTransparency;
	}

	public DisappearingParticle(Vector position, double sizeX, double sizeY, String spriteName, double fadeIn, double fadeOut, double stay, double startTransparency, double endTransparency) {
		super(position, sizeX, sizeY, spriteName, fadeIn, fadeOut, stay, startTransparency);
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
