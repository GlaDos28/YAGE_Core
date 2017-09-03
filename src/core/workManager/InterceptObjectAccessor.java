package core.workManager;

import core.modifier.Modifier;

/**
 * Interface that grants access to the object interceptions. Forces to create a single given modifier when a new object with the given marker is being registered.
 *
 * @author Evgeny Savelyev
 * @since 02.09.17
 */
@FunctionalInterface
public interface InterceptObjectAccessor {
	void setObjectInterception(String marker, Modifier modifier);
}
