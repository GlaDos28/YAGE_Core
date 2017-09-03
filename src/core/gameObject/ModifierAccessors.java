package core.gameObject;

import core.misc.Executable;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.modifier.Modifier;
import core.modifier.ModifierBody;
import core.modifier.ModifierData;
import core.modifier.Order;
import core.workManager.*;
import javafx.util.Pair;

/**
 * Package of game object's accessors that grants some additional functionality from the work manager without referring to it straightly.
 *
 * @author Evgeny Savelyev
 * @since 02.09.17
 */
public final class ModifierAccessors {
	private final MarkedObjectAccessor      markedObjectAccessor;
	private final PutInPoolAccessor         putInPoolAccessor;
	private final RegisterModifierAccessor  registerModifierAccessor;
	private final RegisterObjectAccessor    registerObjectAccessor;
	private final InterceptModifierAccessor interceptModifierAccessor;
	private final InterceptObjectAccessor   interceptObjectAccessor;

	public ModifierAccessors(MarkedObjectAccessor markedObjectAccessor, PutInPoolAccessor putInPoolAccessor, RegisterModifierAccessor registerModifierAccessor, RegisterObjectAccessor registerObjectAccessor, InterceptModifierAccessor interceptModifierAccessor, InterceptObjectAccessor interceptObjectAccessor) {
		this.markedObjectAccessor      = markedObjectAccessor;
		this.putInPoolAccessor         = putInPoolAccessor;
		this.registerModifierAccessor  = registerModifierAccessor;
		this.registerObjectAccessor    = registerObjectAccessor;
		this.interceptModifierAccessor = interceptModifierAccessor;
		this.interceptObjectAccessor   = interceptObjectAccessor;
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

	public void setRegisterObject(GObject object) {
		this.registerObjectAccessor.registerObject(object);
	}

	public void interceptModifier(String marker, ModifierBody modifierBody, String modifierPool, int modifierPriority, Order modifierOrder) {
		this.interceptModifierAccessor.setModifierInterception(marker, new Modifier(modifierBody, new ModifierData(null, modifierPool, modifierPriority, modifierOrder)));
	}

	public void interceptObject(String marker, ModifierBody modifierBody, String modifierPool, int modifierPriority, Order modifierOrder) {
		this.interceptObjectAccessor.setObjectInterception(marker, new Modifier(modifierBody, new ModifierData(null, modifierPool, modifierPriority, modifierOrder)));
	}
}
