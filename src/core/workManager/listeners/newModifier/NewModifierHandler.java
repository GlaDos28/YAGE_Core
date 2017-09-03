package core.workManager.listeners.newModifier;

import core.modifier.Modifier;
import core.workManager.listeners.general.AbstractHandler;

/**
 * Handler for the new modifier listener. Is able to create new modifiers when the listener condition triggers.
 *
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
@FunctionalInterface
public interface NewModifierHandler extends AbstractHandler<Modifier> {
	@Override
	void handle(Modifier origin, Modifier target);
}
