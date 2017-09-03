package core.modifier;

import core.gameObject.GObject;
import core.gameObject.GObjectAccess1;
import core.gameObject.GObjectAccess3;
import core.gameObject.ModifierAccessors;
import core.misc.Executable;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.workManager.WorkManager;
import javafx.util.Pair;

/**
 * Modifier package with needed data to be executed. Transmits into the modifier body tp extract a Runnable.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class ModifierData implements Cloneable {
	private       GObjectAccess1                                     objectAccessor;
	private final ModifierAccessors                                  accessors;
	private final String                                             pool;
	private final int                                                priority;
	private final Order                                              order;
	private       boolean                                            shouldDestruct;
	private       boolean                                            shouldDestructObject;
	private       DoubleLinkedListElement<Pair<Integer, Executable>> elementLink;

	public ModifierData(GObject object, String pool, int priority, Order order) {
		this(new GObjectAccess1(object), object.getModifierAccessors(), pool, priority, order);
	}

	private ModifierData(GObjectAccess1 objectAccessor, ModifierAccessors modifierAccessors, String pool, int priority, Order order) {
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
		this.objectAccessor.putSubObject(subObjectName, new GObject(this.accessors, subObjectName, priority));
	}

	public void createModifier(String pool, int priority, Order order, ModifierBody body) {
		Modifier modifier = new Modifier(body, new ModifierData(this.objectAccessor, this.accessors, pool, priority, order));

		modifier.getData().setElementLink(this.objectAccessor.putModifier(modifier));

		if (!WorkManager.DEFAULT_POOL_NAME.equals(pool))
			modifier.getData().setElementLink(this.accessors.putInPool(modifier));
	}

	public void createSubModifier(String subObjectName, String pool, int priority, Order order, ModifierBody body) {
		Modifier modifier = new Modifier(body, new ModifierData(this.objectAccessor, this.accessors, pool, priority, order));

		modifier.getData().setElementLink(this.objectAccessor.getSubObject(subObjectName).putModifier(modifier));

		if (!WorkManager.DEFAULT_POOL_NAME.equals(pool))
			modifier.getData().setElementLink(this.accessors.putInPool(modifier));
	}

	public void createModifierBefore(ModifierBody body) {
		Modifier modifier = new Modifier(body, new ModifierData(this.objectAccessor, this.accessors, this.pool, this.priority, this.order));
		modifier.getData().setElementLink(this.elementLink.addPrev(new Pair<>(this.priority, modifier)));
	}

	public void createModifierAfter(ModifierBody body) {
		Modifier modifier = new Modifier(body, new ModifierData(this.objectAccessor, this.accessors, this.pool, this.priority, this.order));
		modifier.getData().setElementLink(this.elementLink.addNext(new Pair<>(this.priority, modifier)));
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
		return new ModifierData(this.objectAccessor, this.accessors, this.pool, this.priority, this.order);
	}
}
