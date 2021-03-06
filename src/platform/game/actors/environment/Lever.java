package platform.game.actors.environment;

import platform.game.actors.Actor;
import platform.game.Effect;
import platform.game.Signal;
import platform.game.actors.DisplayableActor;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 *         <p>
 *         A basic lever that can be either enabled or disabled, and transmits the corresponding signal
 */
public class Lever extends DisplayableActor implements Signal {
	private boolean active = false;
	private double duration = 30D;
	private double transition = 0;
	private double time;
	private double rotation = 0;

	private String disabledSprite = null;
	private String enabledSprite = null;
	private String transitionSprite = null;

	public Lever(Vector position, double size, double duration) {
		this(position, size, duration, false);
	}

	public Lever(Vector position, double size, double duration, boolean active) {
		this(position, size, duration, active, "lever.left", "lever.right", "lever.mid");
	}

	public Lever(Vector position, double size, double duration, boolean active, String disabledSprite, String enabledSprite, String transitionSprite) {
		this(position, size, duration, active, disabledSprite, enabledSprite, transitionSprite, 0);
	}

	public Lever(Vector position, double size, double duration, boolean active, String disabledSprite, String enabledSprite, String transitionSprite, double rotation) {
		super(position, size, active ? enabledSprite : disabledSprite);
		this.duration = duration;
		this.active = active;
		this.enabledSprite = enabledSprite;
		this.disabledSprite = disabledSprite;
		this.transitionSprite = transitionSprite;
		this.rotation = rotation;
	}

	@Override
	public int getPriority() {
		return 30;
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		if (damageType == Effect.ACTIVATION) {
			setActive(!active);
			return true;
		}
		return super.hurt(damageFrom, damageType, amount, location);
	}

	@Override
	public boolean isActive() {
		return active;
	}

	private void setActive(boolean state) {
		this.active = state;

		if (transitionSprite != null) {
			setSpriteName(transitionSprite);
			this.transition = 0.3;
		} else {
			setSpriteName(active ? enabledSprite : disabledSprite);
		}
	}

	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (transition > 0) {
			transition -= input.getDeltaTime();
			if (transition <= 0) {
				if (this.active) {
					setSpriteName(enabledSprite);
					time = duration;
				} else {
					setSpriteName(disabledSprite);
				}

				transition = 0;
			}
		}

		if (time > 0) {
			time = Math.max(time - input.getDeltaTime(), 0);
			if (time == 0) { setActive(false); }
		}
	}
}
