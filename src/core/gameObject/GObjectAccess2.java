package core.gameObject;

import core.modifier.Modifier;
import core.modifier.ModifierBody;
import core.modifier.ModifierData;
import core.modifier.Order;

import java.util.Set;

/**
 * Facade that gives second-level access to all GObject getter methods, modifier adding method (note that putModifier here is putAndRegistry method of the GObject object), and also getSubObject() returns a GObject with a third-level access.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class GObjectAccess2 {
	private final GObject object;

	public GObjectAccess2(GObject object) {
		this.object = object;
	}

	//** getters

	public Object getAttribute(String attributeName) {
		return this.object.getAttribute(attributeName);
	}

	public Set<String> getMarkers() {
		return this.object.getMarkers();
	}

	public boolean isMarked(String marker) {
		return this.object.isMarked(marker);
	}

	public GObjectAccess3 getSubObject(String subObjectName) {
		return new GObjectAccess3(this.object.getSubObject(subObjectName));
	}

	public boolean shouldDestruct() {
		return this.object.shouldDestruct();
	}

	//** setters

	public void putModifier(Modifier modifier) {
		this.object.putAndRegisterModifier(modifier);
	}

	public void putModifier(ModifierBody body, String pool, int priority, Order order) {
		this.putModifier(new Modifier(body, new ModifierData(this, pool, priority, order)));
	}

	//** for ModifierData only

	public GObject getRawObject() {
		return this.object;
	}
}
