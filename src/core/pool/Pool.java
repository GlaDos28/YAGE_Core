package core.pool;

/**
 * Modifier-driving module. Manages its own collection of modifiers that are belonged to this pool.
 *
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public class Pool {
	private final String   name;
	private final int      priority;
	private       Runnable executor;

	public Pool(String name, int priority, Runnable executor) {
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

	public final void executeModifiers() {
		this.executor.run();
	}

	public final void setExecutor(Runnable executor) {
		this.executor = executor;
	}
}
