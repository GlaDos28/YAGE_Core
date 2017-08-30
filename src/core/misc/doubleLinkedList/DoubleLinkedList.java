package core.misc.doubleLinkedList;

/**
 * Double linked list structure. Contains the collection of elements.
 *
 * @param <V> value type
 * @author Evgeny Savelyev
 * @since 29.08.17
 */
abstract class DoubleLinkedList<V> {
	abstract void setNext(DoubleLinkedListElement<V> next);
}
