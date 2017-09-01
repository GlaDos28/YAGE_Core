package core.misc;

import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.misc.priorityList.ListElementOperator;
import core.misc.priorityList.PriorityList;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of elements sorted by the name(String) and by the priority(int).
 *
 * @param <V> value type
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public final class TwoIndexedList<V> {
	private final Map<String, Pair<Integer, V>>  nameList;
	private final PriorityList<V> priorityList;

	public TwoIndexedList() {
		this.nameList     = new HashMap<>();
		this.priorityList = new PriorityList<>();
	}

	public DoubleLinkedListElement<Pair<Integer, V>> put(String name, int priority, V value) {
		if (name != null)
			this.nameList.put(name, new Pair<>(priority, value));

		return this.priorityList.put(priority, value);
	}

	public void delete(String name, DoubleLinkedListElement<Pair<Integer, V>> element) {
		if (name != null)
			this.nameList.remove(name);

		this.priorityList.delete(element);
	}

	public void delete(String name) {
		this.priorityList.delete(this.nameList.get(name).getKey(), this.nameList.get(name).getValue());

		if (name != null)
			this.nameList.remove(name);
	}

	public V getByName(String name) {
		return this.nameList.get(name).getValue();
	}

	public DoubleLinkedListElement<Pair<Integer, V>> getHeadElement() {
		return this.priorityList.getFirstElement();
	}

	public void forEach(ListElementOperator<V> operator) throws Exception {
		this.priorityList.forEach(operator);
	}
}
