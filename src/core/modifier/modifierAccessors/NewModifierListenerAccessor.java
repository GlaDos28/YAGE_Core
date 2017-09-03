package core.modifier.modifierAccessors;

import core.workManager.listeners.newModifier.NewModifierListener;

/**
 * Interface that grants access to the corresponding listener creation.
 *
 * @author Evgeny Savelyev
 * @since 02.09.17
 */
@FunctionalInterface
public interface NewModifierListenerAccessor {
	void createNewModifierListener(NewModifierListener listener);
}
