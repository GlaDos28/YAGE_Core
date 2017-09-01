package core.misc.priorityList;

import core.misc.doubleLinkedList.DoubleLinkedListHead;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import javafx.util.Pair;

/**
 * Structure for storing sorted key(int):value pairs. Two values may have the same priority (key).
 *
 * @param <V> value type
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public final class PriorityList<V> {
	private final DoubleLinkedListHead<Pair<Integer, V>> data;

	public PriorityList() {
		this.data = new DoubleLinkedListHead<>();
	}

	public DoubleLinkedListElement<Pair<Integer, V>> put(int priority, V value) {
		if (this.data.isEmpty())
			return this.data.addHead(new Pair<>(priority, value));

		DoubleLinkedListElement<Pair<Integer, V>> cur = this.data.getHead();

		while (cur.getValue().getKey() < priority && cur.getNext() != null)
			cur = cur.getNext();

		if (cur.getValue().getKey() >= priority)
			return cur.addPrev(new Pair<>(priority, value));
		else
			return cur.addNext(new Pair<>(priority, value));
	}

	public void delete(DoubleLinkedListElement<Pair<Integer, V>> element) {
		element.unlink();
	}

	public void delete(int priority, V value) {
		this.data.deleteValue(new Pair<>(priority, value));
	}

	public DoubleLinkedListElement<Pair<Integer, V>> getFirstElement() {
		return this.data.getHead();
	}

	public void forEach(ListElementOperator<V> operator) throws Exception {
		DoubleLinkedListElement<Pair<Integer, V>> cur = this.data.getHead();

		while (cur != null) {
			DoubleLinkedListElement<Pair<Integer, V>> next = cur.getNext();
			operator.run(cur, cur.getValue().getValue());
			cur = next;
		}
	}

	@Override
	public String toString() {
		return this.data.toString();
	}
}
