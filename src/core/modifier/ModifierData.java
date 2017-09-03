package core.modifier;

import core.gameObject.GObject;
import core.gameObject.GObjectAccess1;
import core.gameObject.GObjectAccess2;
import core.gameObject.GObjectAccess3;
import core.modifier.modifierAccessors.ModifierAccessors;
import core.misc.Executable;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.workManager.listeners.newModifier.NewModifierCondition;
import core.workManager.listeners.newModifier.NewModifierHandler;
import core.workManager.listeners.newObject.NewObjectCondition;
import core.workManager.listeners.newObject.NewObjectHandler;
import javafx.util.Pair;

/**
 * Modifier package with needed data to be executed. Transmits into the modifier body tp extract a Runnable.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class ModifierData implements Cloneable {
	private       Modifier                                           modifier;
	private       GObjectAccess1                                     objectAccessor;
	private final ModifierAccessors                                  accessors;
	private final String                                             pool;
	private final int                                                priority;
	private final Order                                              order;
	private       boolean                                            shouldDestruct;
	private       boolean                                            shouldDestructObject;
	private       DoubleLinkedListElement<Pair<Integer, Executable>> elementLink;

	public ModifierData(GObjectAccess2 object, String pool, int priority, Order order) {
		this(object.getRawObject(), pool, priority, order);
	}

	public ModifierData(GObject object, String pool, int priority, Order order) {
		this(null, new GObjectAccess1(object), object.getModifierAccessors(), pool, priority, order);
	}

	private ModifierData(GObjectAccess1 objectAccessor, ModifierAccessors modifierAccessors, String pool, int priority, Order order) {
		this(null, objectAccessor, modifierAccessors, pool, priority, order);
	}

	private ModifierData(Modifier modifier, GObjectAccess1 objectAccessor, ModifierAccessors modifierAccessors, String pool, int priority, Order order) {
		this.modifier             = modifier;
		this.objectAccessor       = objectAccessor;
		this.accessors            = modifierAccessors;
		this.pool                 = pool;
		this.priority             = priority;
		this.order                = order;
		this.shouldDestruct       = false;
		this.shouldDestructObject = false;
		this.elementLink          = null;
	}

	//** info used inside modifier code

	public GObjectAccess3[] getMarkedObjects(String marker) {
		return this.accessors.getMarkedObjects(marker);
	}

	//** create methods used inside modifier code

	public void createSubObject(String subObjectName, int priority) {
		GObject object = new GObject(this.accessors, subObjectName, priority);

		this.objectAccessor.putSubObject(subObjectName, object);

		this.accessors.registerObject(object);
	}

	public void createModifier(String pool, int priority, Order order, ModifierBody body) {
		this.objectAccessor.putAndRegisterModifier(new Modifier(body, new ModifierData(this.objectAccessor, this.accessors, pool, priority, order)));
	}

	public void createSubModifier(String subObjectName, String pool, int priority, Order order, ModifierBody body) {
		this.objectAccessor.getSubObject(subObjectName).putModifier(new Modifier(body, new ModifierData(this.objectAccessor, this.accessors, pool, priority, order)));
	}

	public void createModifierBefore(ModifierBody body) {
		Modifier modifier = new Modifier(body, new ModifierData(this.objectAccessor, this.accessors, this.pool, this.priority, this.order));
		modifier.getData().setElementLink(this.elementLink.addPrev(new Pair<>(this.priority, modifier)));

		this.accessors.registerModifier(modifier);
	}

	public void createModifierAfter(ModifierBody body) {
		Modifier modifier = new Modifier(body, new ModifierData(this.objectAccessor, this.accessors, this.pool, this.priority, this.order));
		modifier.getData().setElementLink(this.elementLink.addNext(new Pair<>(this.priority, modifier)));

		this.accessors.registerModifier(modifier);
	}

	public void createNewModifierListener(NewModifierCondition condition, NewModifierHandler handler) {
		this.accessors.createNewModifierListener(this.modifier, condition, handler);
	}

	public void createNewObjectListener(NewObjectCondition condition, NewObjectHandler handler) {
		this.accessors.createNewObjectListener(this.modifier, condition, handler);
	}

	//** destruct methods used inside modifier code

	public void doSelfDestruct() {
		this.shouldDestruct = true;
	}

	public void doDestructObject() {
		this.shouldDestructObject = true;
	}

	public void doDestructSubObject(String subObjectName) {
		this.objectAccessor.deleteSubObject(subObjectName);
	}

	//** methods not for using in modifier code

	void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

	public void setObjectAccessor(GObject objectAccessor) {
		this.objectAccessor = new GObjectAccess1(objectAccessor);
	}

	public GObjectAccess1 getOwner() {
		return this.objectAccessor;
	}

	public String getPool() {
		return this.pool;
	}

	public int getPriority() {
		return this.priority;
	}

	public Order getOrder() {
		return this.order;
	}

	public boolean shouldDestruct() {
		return this.shouldDestruct;
	}

	public boolean shouldDestructObject() {
		return this.shouldDestructObject;
	}

	public void setElementLink(DoubleLinkedListElement<Pair<Integer, Executable>> elementLink) {
		this.elementLink = elementLink;
	}

	@Override
	public ModifierData clone() {
		return new ModifierData(this.modifier, this.objectAccessor, this.accessors, this.pool, this.priority, this.order);
	}
}
