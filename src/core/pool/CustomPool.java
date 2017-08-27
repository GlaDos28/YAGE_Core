package core.pool;

import core.misc.PriorityList;
import core.modifier.Modifier;

/**
 * Custom pool that executes pool modifiers according to their priority.
 *
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public final class CustomPool extends Pool {
	private final PriorityList<Modifier> modifiers;

	public CustomPool(String name, int priority) {
		super(name, priority, null);

		this.modifiers = new PriorityList<>();

		super.setExecutor(() -> {
			for (Modifier modifier : this.modifiers.getSorted())
				modifier.execute();
		});
	}

	public void putModifier(Modifier modifier) {
		this.modifiers.put(modifier.getData().getPriority(), modifier);
	}
}
