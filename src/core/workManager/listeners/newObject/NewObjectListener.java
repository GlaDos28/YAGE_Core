package core.workManager.listeners.newObject;

import core.gameObject.GObjectAccess2;
import core.modifier.Modifier;
import core.workManager.listeners.general.AbstractListener;

/**
 * Listener of object creation. Triggers when an additional condition turns true.
 *
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
public final class NewObjectListener extends AbstractListener<GObjectAccess2, NewObjectCondition, NewObjectHandler> {
	public NewObjectListener(Modifier origin, NewObjectCondition condition, NewObjectHandler handler) {
		super(origin, condition, handler);
	}
}
