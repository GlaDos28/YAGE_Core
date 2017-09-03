package core.workManager.listeners.general;

import core.modifier.Modifier;

/**
 * Abstract listener. Extends to concrete listeners.
 *
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
public abstract class AbstractListener<O, C extends AbstractCondition<O>, H extends AbstractHandler<O>> {
	private final Modifier origin;
	private final C        condition;
	private final H        handler;

	protected AbstractListener(Modifier origin, C condition, H handler) {
		this.origin    = origin;
		this.condition = condition;
		this.handler   = handler;
	}

	public boolean tryTrigger(O object) {
		if (this.condition.fulfillsCondition(object))
			this.handler.handle(this.origin, object);

		return false; /* TODO destruction */
	}
}
