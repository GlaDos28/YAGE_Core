package core.workManager.listeners.newObject;

import core.gameObject.GObjectAccess2;
import core.workManager.listeners.general.AbstractCondition;

/**
 * Interface for an additional condition for triggering the new object listener handler.
 *
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
@FunctionalInterface
public interface NewObjectCondition extends AbstractCondition<GObjectAccess2> {
	@Override
	boolean fulfillsCondition(GObjectAccess2 object);
}
