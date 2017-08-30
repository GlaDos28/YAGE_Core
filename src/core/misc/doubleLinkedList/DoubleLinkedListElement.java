package core.misc.doubleLinkedList;

/**
 * Double linked list element definition.
 *
 * @param <V> element value type
 * @author Evgeny Savelyev
 * @since 28.08.17
 */
public final class DoubleLinkedListElement<V> extends DoubleLinkedList<V> {
	private final V value;
	private DoubleLinkedList<V> prev;
	private DoubleLinkedListElement<V> next;

	DoubleLinkedListElement(V value, DoubleLinkedList<V> prev, DoubleLinkedListElement<V> next) {
		this.value  = value;
		this.prev   = prev;
		this.next   = next;
	}

	//** getters

	public V getValue() {
		return this.value;
	}

	private DoubleLinkedListElement<V> getPrev() {
		return this.prev instanceof DoubleLinkedListElement ?
			(DoubleLinkedListElement<V>) this.prev :
			null;
	}

	public DoubleLinkedListElement<V> getNext() {
		return this.next;
	}

	//** setters

	void setPrev(DoubleLinkedList<V> prev) {
		this.prev = prev;
	}

	public void addPrev(V value) {
		boolean prevIsNull = this.prev == null;

		this.prev = new DoubleLinkedListElement<>(value, this.prev, this);

		if (!prevIsNull)
			((DoubleLinkedListElement<V>) this.prev).getPrev().setNext((DoubleLinkedListElement<V>) this.prev);
	}

	@Override
	public void setNext(DoubleLinkedListElement<V> next) {
		this.next = next;
	}

	public void addNext(V value) {
		boolean nextIsNull = this.next == null;

		this.next = new DoubleLinkedListElement<>(value, this, this.next);

		if (!nextIsNull)
			this.next.getNext().setPrev(this.next);
	}

	public void unlink() {
		DoubleLinkedListElement<V> newNext = this.next;

		if (this.next != null)
			this.next.setPrev(this.prev);

		if (this.prev != null)
			this.prev.setNext(newNext);
	}

	@Override
	public String toString() {
		return this.value.toString() + (this.next == null ? "" : ", " + this.next.toString());
	}
}
