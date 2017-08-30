package core.misc;

/**
 * Runnable with exception throwing
 *
 * @param <E> exception type
 * @author Evgeny Savelyev
 * @since 30.08.17
 */
@FunctionalInterface
public interface ExRunnable<E extends Exception> {
	void run() throws Exception;
}
