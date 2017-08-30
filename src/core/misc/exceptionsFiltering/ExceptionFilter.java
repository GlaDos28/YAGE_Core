package core.misc.exceptionsFiltering;

/**
 * Object that handles exceptions. Throws exception further if can not handle them.
 * Method should throw exception of generalized type.
 *
 * @param <E> exception type
 * @author Evgeny Savelyev
 * @since 30.08.17
 */
@FunctionalInterface
public interface ExceptionFilter<E extends Exception> {
	void filterException(E ex) throws E;
}
