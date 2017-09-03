package core.workManager.listeners.general;

/**
 * Abstract listener's condition.
 *
 * @param <O> checking object type
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
@FunctionalInterface
public interface AbstractCondition<O> {
	boolean fulfillsCondition(O object);
}
