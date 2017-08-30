package core.pool;

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
	private final PriorityList<Modifier> modifiers;

	public CustomPool(String name, int priority) {
		this(name, priority, null);
	}

	public CustomPool(String name, int priority, ExceptionFilter<Exception> exceptionFilter) {
		super(name, priority, null, exceptionFilter);

		this.modifiers = new PriorityList<>();

		super.setExecutor(() -> {
			DoubleLinkedListElement<Pair<Integer, Modifier>> cur = this.modifiers.getFirstElement();

			while (cur != null) {
				DoubleLinkedListElement<Pair<Integer, Modifier>> next = cur.getNext();

				try {
					cur.getValue().getValue().execute();
				} catch (Exception ex) {
					super.filterException(ex);
				}

				cur = next;
			}
		});
	}

	public void putModifier(Modifier modifier) {
		this.modifiers.put(modifier.getData().getPriority(), modifier);
	}
}
