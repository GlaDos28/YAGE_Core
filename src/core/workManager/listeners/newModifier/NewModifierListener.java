package core.workManager.listeners.newModifier;

import core.modifier.Modifier;
import core.workManager.listeners.general.AbstractListener;

/**
 * Listener of modifier creation. Triggers when an additional condition turns true.
 *
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
public final class NewModifierListener extends AbstractListener<Modifier, NewModifierCondition, NewModifierHandler> {
	public NewModifierListener(Modifier origin, NewModifierCondition condition, NewModifierHandler handler) {
		super(origin, condition, handler);
	}
}
