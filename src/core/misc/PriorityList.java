package core.misc;

import java.util.*;

/**
 * Structure for storing sorted key(int):value pairs. Two values might have the same priority (key).
 *
 * @param <V> value type
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public final class PriorityList<V> {
	private final Map<Integer, Set<V>> data;

	public PriorityList() {
		this.data = new TreeMap<>();
	}

	public void put(int priority, V value) {
		priority = -priority; /* higher number, higher priority, but in TreeMap it is vise-versa */

		if (this.data.containsKey(priority))
			this.data.get(priority).add(value);
		else {
			Set<V> newSet = new HashSet<>();
			newSet.add(value);
			this.data.put(priority, newSet);
		}
	}

	public void delete(int priority, V value) {
		this.data.get(priority).remove(value);
	}

	public ArrayList<V> getSorted() {
		ArrayList<V> res = new ArrayList<>();

		for (Map.Entry<Integer, Set<V>> entry : this.data.entrySet())
			res.addAll(entry.getValue());

		return res;
	}
}
