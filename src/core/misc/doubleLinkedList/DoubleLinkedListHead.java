package core.misc.doubleLinkedList;

/**
 * Double linked list head definition.
 *
 * @param <V> element value type
 * @author Evgeny Savelyev
 * @since 28.08.17
 */
public final class DoubleLinkedListHead<V> extends DoubleLinkedList<V> {
	private DoubleLinkedListElement<V> head;

	public DoubleLinkedListHead() {
		this.head = null;
	}

	public DoubleLinkedListElement<V> getHead() {
		return this.head;
	}

	public boolean isEmpty() {
		return this.head == null;
	}

	public DoubleLinkedListElement<V> addHead(V value) {
		DoubleLinkedListElement<V> newHead = new DoubleLinkedListElement<>(value, this, this.head);

		if (this.head != null)
			this.head.setPrev(newHead);

		this.head = newHead;

		return this.head;
	}

	@Override
	public void setNext(DoubleLinkedListElement<V> next) {
		this.head = next;
	}

	public void clear() {
		this.head = null;
	}

	public void deleteValue(V value) {
		DoubleLinkedListElement<V> cur = this.head;

		while (cur != null) {
			if (cur.getValue() == value) {
				cur.unlink();
				return;
			}

			cur = cur.getNext();
		}
	}

	@Override
	public String toString() {
		if (this.head == null)
			return "list { empty }";

		return "list { " + this.head.toString() + " }";
	}
}
