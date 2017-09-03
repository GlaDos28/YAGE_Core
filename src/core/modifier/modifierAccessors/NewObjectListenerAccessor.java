package core.modifier.modifierAccessors;

import core.workManager.listeners.newObject.NewObjectListener;

/**
 * Interface that grants access to the corresponding listener creation.
 *
 * @author Evgeny Savelyev
 * @since 02.09.17
 */
@FunctionalInterface
public interface NewObjectListenerAccessor {
	void createNewObjectListener(NewObjectListener listener);
}
