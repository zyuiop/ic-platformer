package platform.game.particles;

import platform.game.actors.animations.DisappearingParticle;
import platform.game.actors.animations.ParticleActor;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class DisappearingParticleEffect extends SimpleParticleEffect {
	private double startTransparency = 1D;
	private double endTransparency = 1D;

	public DisappearingParticleEffect(String particle) {
		super(particle);
	}

	@Override
	public SimpleParticleEffect transparency(double value) {
		this.startTransparency = value;
		this.endTransparency = value;
		return super.transparency(value);
	}

	public DisappearingParticleEffect startTransparency(double value) {
		this.startTransparency = value;
		return this;
	}

	public DisappearingParticleEffect endTransparency(double value) {
		this.endTransparency = value;
		return this;
	}

	@Override
	public ParticleActor build(Vector location) {
		return new DisappearingParticle(location, sizeX, sizeY, this.particle, fadeIn, fadeOut, stay, startTransparency, endTransparency);
	}
}
