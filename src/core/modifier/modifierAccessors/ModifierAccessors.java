package core.modifier.modifierAccessors;

import core.gameObject.GObject;
import core.gameObject.GObjectAccess3;
import core.misc.Executable;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.modifier.Modifier;
import core.workManager.listeners.newModifier.NewModifierCondition;
import core.workManager.listeners.newModifier.NewModifierHandler;
import core.workManager.listeners.newModifier.NewModifierListener;
import core.workManager.listeners.newObject.NewObjectCondition;
import core.workManager.listeners.newObject.NewObjectHandler;
import core.workManager.listeners.newObject.NewObjectListener;
import javafx.util.Pair;

/**
 * Package of game object's accessors that grants some additional functionality from the work manager without referring to it straightly.
 *
 * @author Evgeny Savelyev
 * @since 02.09.17
 */
public final class ModifierAccessors {
	private final MarkedObjectAccessor        markedObjectAccessor;
	private final PutInPoolAccessor           putInPoolAccessor;
	private final RegisterModifierAccessor    registerModifierAccessor;
	private final RegisterObjectAccessor      registerObjectAccessor;
	private final NewModifierListenerAccessor newModifierListenerAccessor;
	private final NewObjectListenerAccessor   newObjectListenerAccessor;

	public ModifierAccessors(MarkedObjectAccessor markedObjectAccessor, PutInPoolAccessor putInPoolAccessor, RegisterModifierAccessor registerModifierAccessor, RegisterObjectAccessor registerObjectAccessor, NewModifierListenerAccessor newModifierListenerAccessor, NewObjectListenerAccessor newObjectListenerAccessor) {
		this.markedObjectAccessor        = markedObjectAccessor;
		this.putInPoolAccessor           = putInPoolAccessor;
		this.registerModifierAccessor    = registerModifierAccessor;
		this.registerObjectAccessor      = registerObjectAccessor;
		this.newModifierListenerAccessor = newModifierListenerAccessor;
		this.newObjectListenerAccessor   = newObjectListenerAccessor;
	}

	//** accessors' methods in the appropriate order

	public GObjectAccess3[] getMarkedObjects(String marker) {
		return this.markedObjectAccessor.getMarkedObjects(marker);
	}

	public DoubleLinkedListElement<Pair<Integer, Executable>> putInPool(Modifier modifier) {
		return this.putInPoolAccessor.putInPool(modifier);
	}

	public void registerModifier(Modifier modifier) {
		this.registerModifierAccessor.registerModifier(modifier);
	}

	public void registerObject(GObject object) {
		this.registerObjectAccessor.registerObject(object);
	}

	public void createNewModifierListener(Modifier origin, NewModifierCondition condition, NewModifierHandler handler) {
		this.newModifierListenerAccessor.createNewModifierListener(new NewModifierListener(origin, condition, handler));
	}

	public void createNewObjectListener(Modifier origin, NewObjectCondition condition, NewObjectHandler handler) {
		this.newObjectListenerAccessor.createNewObjectListener(new NewObjectListener(origin, condition, handler));
	}
}
