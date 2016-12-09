package platform.game.particles;

import java.util.ArrayList;
import java.util.Collection;
import platform.game.actors.animations.ParticleActor;
import platform.game.actors.animations.ParticleAnimation;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class ParticleEffectAnimation implements ParticleEffect {
	private final Collection<ParticleEffect> effects = new ArrayList<>();

	public ParticleEffectAnimation add(ParticleEffect effect) {
		effects.add(effect);
		return this;
	}

	@Override
	public ParticleActor build(Vector location) {
		ParticleAnimation actor = new ParticleAnimation(location);
		effects.stream().map(e -> e.build(location)).forEach(actor::add);
		return actor;
	}
}
