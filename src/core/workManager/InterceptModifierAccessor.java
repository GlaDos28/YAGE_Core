package core.workManager;

import core.modifier.Modifier;

/**
 * Interface that grants access to the modifier interceptions. Forces to create a single given modifier when a new modifier with the given marker is being registered.
 *
 * @author Evgeny Savelyev
 * @since 02.09.17
 */
@FunctionalInterface
public interface InterceptModifierAccessor {
	void setModifierInterception(String marker, Modifier modifier);
}
