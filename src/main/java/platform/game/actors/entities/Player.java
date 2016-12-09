package platform.game.actors.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import platform.Program;
import platform.game.Actor;
import platform.game.Effect;
import platform.game.KeyBindings;
import platform.game.KeyBindings.Key;
import platform.game.Simulator;
import platform.game.World;
import platform.game.actors.AttachLink;
import platform.game.actors.Side;
import platform.game.actors.animations.BlowAnimation;
import platform.game.actors.animations.Crosshair;
import platform.game.actors.animations.Overlay;
import platform.game.actors.basic.LivingActor;
import platform.game.actors.interfaces.IAttachable;
import platform.game.actors.interfaces.IPositioned;
import platform.game.level.PlayableLevel;
import platform.game.menus.main.MainMenuLevel;
import platform.game.particles.ParticleEffect;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;
import platform.util.View;

/**
 * @author zyuiop
 */
public class Player extends LivingActor implements IAttachable {
	private boolean debug = false;
	private final KeyBindings bindings;
	private boolean isColliding = false;
	private final int maxAirJumps = 1;
	private int remainingAirJumps = 1;
	private boolean isOnFloor = false;
	private AttachLink attachLink;
	private Crosshair crosshair;

	public Player(Vector position, Vector velocity, KeyBindings bindings) {
		super(position, .5, "blocker.happy", velocity, 10);
		this.bindings = bindings;
	}

	public Player(Vector position, Vector velocity, double maxhealth, double health, KeyBindings bindings) {
		super(position, .5, "blocker.happy", velocity, maxhealth, health);
		this.bindings = bindings;
	}

	@Override
	public int getPriority() {
		return 42;
	}

	@Override
	public void draw(Input input, Output output) {
		super.draw(input, output);

		if (debug) {
			if (output instanceof View) { output = ((View) output).getOutput(); }
			Vector top = output.getBox().getMin().add(new Vector(0, output.getBox().getHeight()));
			Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
			output.drawText("Position : " + getPosition(), top.add(new Vector(10, -20)), font, Color.BLACK);
			output.drawText("Velocity : " + getVelocity(), top.add(new Vector(10, -40)), font, Color.BLACK);
			output.drawText("colliding : " + isColliding + ", on floor : " + isOnFloor, top.add(new Vector(10, -60)), font, Color.BLACK);
			output.drawText("attached : " + isAttached() + ", airjumps : " + remainingAirJumps, top.add(new Vector(10, -80)), font, Color.BLACK);
			output.drawText("health : " + getHealth() + " / " + getMaxHealth(), top.add(new Vector(10, -100)), font, Color.BLACK);

			int actors = 0;
			if (getWorld() instanceof Simulator) {
				actors = ((Simulator) getWorld()).countActors();
			}

			output.drawText("FPS : " + Math.round(Program.getFps()), output.getBox().getMin().add(new Vector(10, 5)), font, Color.BLACK);
			output.drawText("Actors : " + actors, output.getBox().getMin().add(new Vector(10, 25)), font, Color.BLACK);
		}
	}

	@Override
	public void update(Input input) {
		if (isColliding) {
			double scale = Math.pow(0.001, input.getDeltaTime()); // frottements
			setVelocity(getVelocity().mul(scale));
		}

		double maxSpeed = 4.0;
		if (bindings.isDown(input, Key.RIGHT)) {
			if (getVelocity().getX() < maxSpeed) {
				double increase = 60.0 * input.getDeltaTime();
				double speed = getVelocity().getX() + increase;
				if (speed > maxSpeed) {
					speed = maxSpeed;
				}

				// We call detach because it moves but also frees the player from a moving platform
				detach(new Vector(speed, getVelocity().getY()));
			}
		} else if (bindings.isDown(input, Key.LEFT)) {
			if (getVelocity().getX() > -maxSpeed) {
				double increase = 60.0 * input.getDeltaTime();
				double speed = getVelocity().getX() - increase;
				if (speed < -maxSpeed) {
					speed = -maxSpeed;
				}

				// We call detach because it moves but also frees the player from a moving platform
				detach(new Vector(speed, getVelocity().getY()));
			}
		}

		if (bindings.isPressed(input, Key.JUMP)) {
			if (isOnFloor || remainingAirJumps > 0) {
				// We call detach because it moves but also frees the player from a moving platform
				detach(new Vector(getVelocity().getX(), 5D));

				if (!isOnFloor) {
					remainingAirJumps--;
					ParticleEffect.BLOW.play(getWorld(), getPosition());
				}
			}
		}

		// Attack also use the mouse (more convenient)
		// TODO : add a super cool sound
		// TODO : make possible to change weapon
		if (bindings.isPressed(input, Key.ATTACK) || input.getMouseButton(MouseEvent.BUTTON1).isPressed()) {
			Vector vector = input.getMouseLocation().sub(getPosition());
			if (vector.getLength() > 7D) {
				double angle = vector.getAngle();
				vector = Vector.X.rotated(angle).mul(7D);
			}

			vector = vector.mul(2);

			getWorld().register(new Fireball(getPosition(), vector, this));
		}

		// Some useless debug screen
		if (input.getKeyboardButton(KeyEvent.VK_F3).isPressed()) {
			this.debug = !this.debug;
			System.out.println("Debug toggled to " + debug);
		}

		// TODO : add a super cool sound
		if (bindings.isPressed(input, Key.BLOW)) {
			getWorld().hurt(getBox(), this, Effect.AIR, 1.0, getPosition());
			getWorld().register(new BlowAnimation(getPosition()));
			ParticleEffect.BLOW.play(getWorld(), getPosition());
		}

		if (bindings.isPressed(input, Key.USE)) {
			getWorld().hurt(getBox(), this, Effect.ACTIVATION, 1.0, getPosition());
		}

		if (bindings.isPressed(input, Key.MENU)) {
			getWorld().setNextLevel(new MainMenuLevel());
			getWorld().nextLevel();
		}

		super.update(input);
	}

	@Override
	public void interact(Actor other) {
		super.interact(other);
		if (other.isSolid()) {
			Vector delta = other.getBox().getCollision(getBox());
			if (delta != null) {
				setPosition(getPosition().add(delta));

				// We find the side we interacted with
				Side direction = Side.compute(delta);

				if (delta.getX() != 0D) {
					setVelocity(new Vector(0D, getVelocity().getY()));
				}

				if (delta.getY() != 0D) {
					setVelocity(new Vector(getVelocity().getX(), 0D));
					if (delta.getY() > 0) {
						// We are on the top of the block ==> we are on the floor
						this.isOnFloor = true;
						this.remainingAirJumps = this.maxAirJumps;
					}
				}

				// I did this to avoid having to send a ""damage"" to the block
				other.onCollide(this, direction);
				this.isColliding = true;
			}
		}
	}

	@Override
	public void preUpdate(Input input) {
		super.preUpdate(input);
		this.isColliding = false;
		this.isOnFloor = false;
	}

	@Override
	public void postUpdate(Input input) {
		getWorld().setView(getPosition());
		super.postUpdate(input);
	}

	@Override
	public boolean hurt(Actor damageFrom, Effect damageType, double amount, Vector location) {
		switch (damageType) {
			case AIR:
				// TODO : let the jumper do it by itself ?
				setVelocity(getPosition().sub(location).resized(amount));
				return true;
			case VOID:
				if (super.hurt(damageFrom, damageType, 2D, location)) {
					if (!this.isDead()) {
						getWorld().getLevelManager().setPlayerLife(getHealth());
						try {
							getWorld().setNextLevel(getWorld().getCurrentLevel().getClass().newInstance());
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();

							// Fallback to restart group
							// This should never happen whatever.
							getWorld().setNextLevel(getWorld().getLevelManager()
									.restartGroup((PlayableLevel) getWorld().getCurrentLevel()));
						}

						getWorld().nextLevel();
					}
					return true;
				}
				return false;
			default:
				return super.hurt(damageFrom, damageType, amount, location);
		}
	}

	@Override
	public void unregister() {
		// save life in level manager
		if (getHealth() > 0D) { getWorld().getLevelManager().setPlayerLife(getHealth()); }

		super.unregister();
	}

	@Override
	public void die() {
		super.die();
		// The current level must be playable as there is a player in it
		getWorld().setNextLevel(getWorld().getLevelManager().restartGroup((PlayableLevel) getWorld().getCurrentLevel()));
		getWorld().nextLevel();
	}

	@Override
	public void register(World world) {
		super.register(world);
		if (!(world.getCurrentLevel() instanceof PlayableLevel)) {
			throw new IllegalStateException("Cannot add a player in a non playable level !");
		}

		crosshair = new Crosshair(this);
		world.register(crosshair);
		world.register(new Overlay(this));
	}

	@Override
	public void attachTo(IPositioned attachedTo, Vector positionDifference) {
		detach(Vector.ZERO); // detach and set velocity
		this.attachLink = new AttachLink(attachedTo, this, positionDifference);
		getWorld().register(attachLink);
	}

	/**
	 * Detach the current actor from it's attach (if it exists), and give it a velocity.
	 *
	 * @param velocity the velocity to set to the actor when detached. In general,
	 * {@link Vector#ZERO} is used as the new velocity parameter.
	 * @implNote This implementation tries to remove the link then it sets the velocity of the
	 * player, even if it was not attached.
	 */
	@Override
	public void detach(Vector velocity) {
		if (attachLink != null) { attachLink.detach(); }
		this.attachLink = null;
		this.setVelocity(velocity);
	}

	@Override
	public boolean isAttached() {
		if (attachLink != null && !attachLink.isRegistered()) {
			detach(Vector.ZERO);
			return false;
		}

		return attachLink != null;
	}
}
