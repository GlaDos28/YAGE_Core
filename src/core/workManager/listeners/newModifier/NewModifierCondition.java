package core.workManager.listeners.newModifier;

import core.modifier.Modifier;
import core.workManager.listeners.general.AbstractCondition;

/**
 * Interface for an additional condition for triggering the new modifier listener handler.
 *
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
@FunctionalInterface
public interface NewModifierCondition extends AbstractCondition<Modifier> {
	@Override
	boolean fulfillsCondition(Modifier modifier);
}
