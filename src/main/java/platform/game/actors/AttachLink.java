package platform.game.actors;

import platform.game.Actor;
import platform.game.actors.interfaces.IAttachable;
import platform.game.actors.interfaces.IPositioned;
import platform.util.Input;
import platform.util.Vector;

/**
 * @author zyuiop
 */
public class AttachLink extends Actor {
	private IPositioned attachedTo;
	private IAttachable attachedActor;
	private Vector difference;

	public AttachLink(IPositioned attachedTo, IAttachable attachedActor, Vector difference) {
		this.attachedTo = attachedTo;
		this.difference = difference;
		this.attachedActor = attachedActor;
	}

	@Override
	public int getPriority() {
		return -1;
	}

	@Override
	public void update(Input input) {
		super.update(input);

		if (attachedTo == null || !attachedTo.isRegistered() || attachedTo.getPosition() == null) {
			detach();
			return;
		}

		attachedActor.setPosition(attachedTo.getPosition().add(difference));
	}

	public void detach() {
		getWorld().unregister(this);
	}
}
