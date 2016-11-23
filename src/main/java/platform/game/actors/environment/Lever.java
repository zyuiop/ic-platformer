package platform.game.actors.environment;

import platform.game.Actor;
import platform.game.Effect;
import platform.game.Signal;
import platform.game.actors.basic.PositionedActor;
import platform.util.Input;
import platform.util.sounds.Sound;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class Lever extends PositionedActor implements Signal {
	private boolean active = false;
	private double duration = 30D;
	private double transition = 0;
	private double time;

	private String disabledSprite = "lever.left";
	private String enabledSprite = "lever.right";
	private String transitionSprite = "lever.mid";

	public Lever(Vector position, double size, double duration) {
		this(position, size, duration, false);
	}

	public Lever(Vector position, double size, double duration, boolean active) {
		super(position, size, active ? "lever.right" : "lever.left");
		this.duration = duration;
		this.active = active;
	}

	public Lever(Vector position, double size, double duration, boolean active, String disabledSprite, String enabledSprite, String transitionSprite) {
		super(position, size, active ? enabledSprite : disabledSprite);
		this.duration = duration;
		this.active = active;
		this.enabledSprite = enabledSprite;
		this.disabledSprite = disabledSprite;
		this.transitionSprite = transitionSprite;
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
			if (time == 0)
				setActive(false);
		}
	}

	private void setActive(boolean state) {
		this.active = state;

		if (transitionSprite != null) {
			setSpriteName(transitionSprite);
			this.transition = 0.3;
		} else {
			setSpriteName(active ? enabledSprite : disabledSprite);
		}

		Sound sound = getWorld().getSoundLoader().getSound("chop");
		sound.play();

	}
}
