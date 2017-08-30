package core.misc.priorityList;

import core.misc.doubleLinkedList.DoubleLinkedListElement;
import javafx.util.Pair;

/**
 * Operator
 *
 * @param <V> list element value type
 * @author Evgeny Savelyev
 * @since 30.08.17
 */
@FunctionalInterface
public interface ListElementOperator<V> {
	void run(DoubleLinkedListElement<Pair<Integer, V>> elem, V value) throws Exception;
}
