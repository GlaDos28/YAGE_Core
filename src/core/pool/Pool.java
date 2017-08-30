package core.pool;

import core.misc.ExRunnable;
import core.misc.exceptionsFiltering.ExceptionFilter;
import core.misc.exceptionsFiltering.FilterExceptions;

/**
 * Modifier-driving module. Manages its own collection of modifiers that are belonged to this pool.
 *
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public class Pool extends FilterExceptions<Exception> {
	private final String     name;
	private final int        priority;
	private       ExRunnable executor;

	Pool(String name, int priority, ExRunnable executor, ExceptionFilter<Exception> exceptionFilter) {
		super(exceptionFilter);

		this.name     = name;
		this.priority = priority;
		this.executor = executor;
	}

	public String getName() {
		return this.name;
	}

	public int getPriority() {
		return this.priority;
	}

	public final void executeModifiers() throws Exception {
		this.executor.run();
	}

	final void setExecutor(ExRunnable executor) {
		this.executor = executor;
	}
}
