package core.workManager;

import core.modifier.Modifier;

/**
 * Interface that grants access to put object modifier into the custom pool.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
@FunctionalInterface
public interface PutInPoolAccessor {
	void putInPool(Modifier modifier);
}
