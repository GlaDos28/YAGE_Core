package core.pool;

import core.misc.Executable;
import core.misc.exceptionsFiltering.ExceptionFilter;
import core.misc.priorityList.PriorityList;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.modifier.Modifier;
import javafx.util.Pair;

/**
 * Custom pool that executes pool modifiers according to their priority.
 *
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public final class CustomPool extends Pool {
	private final PriorityList<Executable> modifiers;

	public CustomPool(String name, int priority) {
		this(name, priority, null);
	}

	public CustomPool(String name, int priority, ExceptionFilter<Exception> exceptionFilter) {
		super(name, priority, null, exceptionFilter);

		this.modifiers = new PriorityList<>();

		super.setExecutor(() -> {
			DoubleLinkedListElement<Pair<Integer, Executable>> cur = this.modifiers.getFirstElement();

			while (cur != null) {
				DoubleLinkedListElement<Pair<Integer, Executable>> next = cur.getNext();
				Modifier modifier = (Modifier) cur.getValue().getValue();

				if (modifier.getData().getOwner().shouldDestruct()) /* possible for custom pools only */
					cur.unlink();
				else {
					try {
						modifier.execute();
					} catch (Exception ex) {
						super.filterException(ex);
					}

					if (modifier.getData().shouldDestruct() || modifier.getData().shouldDestructObject())
						cur.unlink();

					if (modifier.getData().shouldDestructObject())
						modifier.getData().getOwner().doDestruct();
				}

				cur = next;
			}
		});
	}

	public DoubleLinkedListElement<Pair<Integer, Executable>> putModifier(Modifier modifier) {
		return this.modifiers.put(modifier.getData().getPriority(), modifier);
	}
}
