package core.misc;

import java.util.ArrayList;
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
	private final Map<String, V>  nameList;
	private final PriorityList<V> priorityList;

	public TwoIndexedList() {
		this.nameList     = new HashMap<>();
		this.priorityList = new PriorityList<>();
	}

	public void put(String name, int priority, V value) {
		if (name != null)
			this.nameList.put(name, value);

		this.priorityList.put(priority, value);
	}

	public void delete(String name, int priority, V value) {
		if (name != null)
			this.nameList.remove(name);

		this.priorityList.delete(priority, value);
	}

	public V getByName(String name) {
		return this.nameList.get(name);
	}

	public ArrayList<V> getSortedByPriority() {
		return this.priorityList.getSorted();
	}
}
