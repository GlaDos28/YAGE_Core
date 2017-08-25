package core.modifier;

import core.gameObject.GObject;
import core.gameObject.GObjectAccess1;
import core.gameObject.GObjectAccess3;
import core.objectManager.MarkedObjectAccessor;

/**
 * Modifier package with needed data to be executed. Transmits into the modifier body tp extract a Runnable.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class ModifierData { /* TODO destruction, exceptions */
	private final GObjectAccess1       objectAccess;
	private final MarkedObjectAccessor markedObjectAccessor;

	public ModifierData(GObject object, MarkedObjectAccessor markedObjectAccessor) {
		this.objectAccess         = new GObjectAccess1(object);
		this.markedObjectAccessor = markedObjectAccessor;
	}

	public GObjectAccess1 getObject() {
		return this.objectAccess;
	}

	public GObjectAccess3[] getMarkedObjects(String marker) {
		return this.markedObjectAccessor.getMarkedObjects(marker);
	}
}
