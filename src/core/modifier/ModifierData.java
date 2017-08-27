package core.modifier;

import core.gameObject.GObject;
import core.gameObject.GObjectAccess1;
import core.gameObject.GObjectAccess3;
import core.workManager.MarkedObjectAccessor;
import core.workManager.PutInPoolAccessor;
import core.workManager.WorkManager;

/**
 * Modifier package with needed data to be executed. Transmits into the modifier body tp extract a Runnable.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class ModifierData { /* TODO object destruct, sub-object destruct, exceptions */
	private final GObjectAccess1       objectAccess;
	private final MarkedObjectAccessor markedObjectAccessor;
	private final PutInPoolAccessor    putInPoolAccessor;
	private final String               pool;
	private final int                  priority;
	private final Order                order;
	private       boolean              shouldDestruct;

	public ModifierData(GObject object, String pool, int priority, Order order) {
		this(new GObjectAccess1(object), object.getMarkedObjectAccessor(), object.getPutInPoolAccessor(), pool, priority, order);
	}

	private ModifierData(GObjectAccess1 objectAccess, MarkedObjectAccessor markedObjectAccessor, PutInPoolAccessor putInPoolAccessor, String pool, int priority, Order order) {
		this.objectAccess         = objectAccess;
		this.markedObjectAccessor = markedObjectAccessor;
		this.putInPoolAccessor    = putInPoolAccessor;
		this.pool                 = pool;
		this.priority             = priority;
		this.order                = order;
		this.shouldDestruct       = false;
	}

	//** used by modifiers

	public GObjectAccess3[] getMarkedObjects(String marker) {
		return this.markedObjectAccessor.getMarkedObjects(marker);
	}

	public void createSubObject(String subObjectName, int priority) {
		this.objectAccess.putSubObject(subObjectName, new GObject(this.markedObjectAccessor, this.putInPoolAccessor, subObjectName, priority));
	}

	public void createModifier(String pool, int priority, Order order, ModifierBody body) {
		Modifier modifier = new Modifier(body, new ModifierData(this.objectAccess, this.markedObjectAccessor, this.putInPoolAccessor, pool, priority, order));

		this.objectAccess.putModifier(modifier);

		if (!WorkManager.DEFAULT_POOL_NAME.equals(pool))
			this.putInPoolAccessor.putInPool(modifier);
	}

	public void doSelfDestruct() {
		this.shouldDestruct = true;
	}

	//** other

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
}
