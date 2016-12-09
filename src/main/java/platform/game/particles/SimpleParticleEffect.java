package platform.game.particles;

import platform.game.actors.animations.ParticleActor;
import platform.game.actors.animations.SimpleParticle;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class SimpleParticleEffect implements ParticleEffect {
	protected double fadeIn = 0D;
	protected double fadeOut = 0D;
	protected double stay = 1D;
	protected final String particle;
	protected double transparency = 1;
	protected double sizeX = .8;
	protected double sizeY = .8;

	public SimpleParticleEffect(String particle) {
		this.particle = particle;
	}

	public SimpleParticleEffect fadeIn(double value) {
		this.fadeIn = value;
		return this;
	}

	public SimpleParticleEffect fadeOut(double value) {
		this.fadeOut = value;
		return this;
	}

	public SimpleParticleEffect stay(double value) {
		this.stay = value;
		return this;
	}

	public SimpleParticleEffect transparency(double value) {
		this.transparency = value;
		return this;
	}

	public SimpleParticleEffect sizeX(double value) {
		this.sizeX = value;
		return this;
	}

	public SimpleParticleEffect sizeY(double value) {
		this.sizeY = value;
		return this;
	}

	public SimpleParticleEffect size(double value) {
		this.sizeX = value;
		this.sizeY = value;
		return this;
	}

	@Override
	public ParticleActor build(Vector location) {
		return new SimpleParticle(location, sizeX, sizeY, this.particle, fadeIn, fadeOut, stay, transparency);
	}
}
