package core.workManager;

import core.misc.Executable;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.modifier.Modifier;
import javafx.util.Pair;

/**
 * Interface that grants access to put object modifier into the custom pool.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
@FunctionalInterface
public interface PutInPoolAccessor {
	DoubleLinkedListElement<Pair<Integer, Executable>> putInPool(Modifier modifier);
}
