package platform.game.actors.animations;

import platform.util.Input;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.Vector;

/**
 * @author zyuiop
 *
 * A simple particle effect that is displayed a short defined amount of time, with the ability to
 * add a fade-in and fade-out animation
 */
public class SimpleParticle extends ParticleActor {
	private double fadeIn;
	private double fadeOut;
	private double stay;
	private double transparency;
	private double time = 0;

	public SimpleParticle(Vector position, double size, String spriteName, double fadeIn, double fadeOut, double stay, double transparency) {
		super(position, size, spriteName);
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		this.stay = stay;
		this.transparency = transparency;
	}

	public SimpleParticle(Vector position, double sizeX, double sizeY, String spriteName, double fadeIn, double fadeOut, double stay, double transparency) {
		super(position, sizeX, sizeY, spriteName);
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		this.stay = stay;
		this.transparency = transparency;
	}

	@Override
	public int getPriority() {
		return 5000; // au dessus de pas mal de choses
	}

	@Override
	public void update(Input input) {
		super.update(input);
		time += input.getDeltaTime();
		if (time > fadeIn + fadeOut + stay) {
			getWorld().unregister(this);
			unregister();
		}
	}

	@Override
	public double getOpacity() {
		double transparency = this.transparency;

		if (time < fadeIn) {
			transparency *= (time / fadeIn);
		} else if (time >= fadeIn + stay) {
			transparency = transparency - ((time - fadeIn - fadeOut) / fadeOut) * transparency;
		}
		return transparency;
	}

	protected double getFadeIn() {
		return fadeIn;
	}

	protected double getFadeOut() {
		return fadeOut;
	}

	protected double getStay() {
		return stay;
	}

	protected double getTransparency() {
		return transparency;
	}

	protected double getTime() {
		return time;
	}
}
