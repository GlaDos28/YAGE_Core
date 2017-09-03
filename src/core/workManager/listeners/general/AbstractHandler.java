package core.workManager.listeners.general;

import core.modifier.Modifier;

/**
 * Abstract listener's handler.
 *
 * @param <O> target object type
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
public interface AbstractHandler<O> {
	void handle(Modifier origin, O target);
}
