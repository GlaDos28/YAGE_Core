package core.modifier.modifierAccessors;

import core.modifier.Modifier;

/**
 * Interface that grants access to the modifier registering in the work manager. Used to put other modifiers before or after the one that is being registered once, if needed.
 *
 * @author Evgeny Savelyev
 * @since 02.09.17
 */
@FunctionalInterface
public interface RegisterModifierAccessor {
	void registerModifier(Modifier modifier);
}
