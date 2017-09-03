package core.workManager;

import core.workManager.listeners.newModifier.NewModifierListener;
import core.workManager.listeners.newObject.NewObjectListener;

import java.util.LinkedList;
import java.util.List;

/**
 * All listeners contained by the work manager. Class used for packing all listeners into single structure, for more convenience.
 *
 * @author Evgeny Savelyev
 * @since 03.09.17
 */
final class WorkManagerListeners {
	private final List<NewModifierListener> newModifierListeners;
	private final List<NewObjectListener>   newObjectListeners;

	WorkManagerListeners() {
		this.newModifierListeners = new LinkedList<>();
		this.newObjectListeners   = new LinkedList<>();
	}

	List<NewModifierListener> getNewModifierListeners() {
		return this.newModifierListeners;
	}

	List<NewObjectListener> getNewObjectListeners() {
		return this.newObjectListeners;
	}
}
