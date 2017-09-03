package core.workManager.listeners.newObject;

import core.gameObject.GObjectAccess2;
import core.modifier.Modifier;
import core.workManager.listeners.general.AbstractHandler;

/**
 * Handler for the new object listener. Is able to create new modifiers when the listener condition triggers.
 *
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
@FunctionalInterface
public interface NewObjectHandler extends AbstractHandler<GObjectAccess2> {
	@Override
	void handle(Modifier origin, GObjectAccess2 target);
}
