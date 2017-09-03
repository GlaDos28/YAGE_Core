package core.misc.exceptionsFiltering;

/**
 * Interface for classes which can handle exceptions in inner class objects. Some exceptions will be thrown further, other will be caught and processed.
 * Exception filter method should be called inside corresponding class methods via exceptions try-catch blocks.
 * Method should throw exception of generalized type.
 *
 * @param <E> exception filtering type
 * @author Evgeny Savelyev
 * @since 30.08.17
 */
public abstract class FilterExceptions<E extends Exception> {
	private final ExceptionFilter<E> filter;

	protected FilterExceptions(ExceptionFilter<E> filter) {
		this.filter = filter;
	}

	protected boolean filtersExceptions() {
		return this.filter != null;
	}

	protected ExceptionFilter<E> getExceptionFilter() {
		return this.filter;
	}

	protected void filterException(E ex) throws E {
		if (this.filter != null)
			this.filter.filterException(ex);
		else
			throw ex;
	}
}
