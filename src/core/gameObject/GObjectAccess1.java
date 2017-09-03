package core.gameObject;

import core.misc.Executable;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.modifier.Modifier;
import javafx.util.Pair;

import java.util.Set;

/**
 * Facade that gives first-level access to all GObject getter and setter methods, and also getSubObject() returns a GObject with a second-level access.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class GObjectAccess1 {
	private final GObject object;

	public GObjectAccess1(GObject object) {
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

	public GObjectAccess2 getSubObject(String subObjectName) {
		return new GObjectAccess2(this.object.getSubObject(subObjectName));
	}

	public boolean shouldDestruct() {
		return this.object.shouldDestruct();
	}

	//** setters

	public GObjectAccess1 putAttribute(String attributeName, Object attributeValue) {
		this.object.putAttribute(attributeName, attributeValue);
		return this;
	}

	public GObjectAccess1 mark(String marker) {
		this.object.mark(marker);
		return this;
	}

	public GObjectAccess1 putSubObject(String subObjectName, GObject subObjectValue) {
		this.object.putSubObject(subObjectName, subObjectValue);
		return this;
	}

	public GObjectAccess1 deleteSubObject(String subObjectName) {
		this.object.deleteSubObject(subObjectName);
		return this;
	}

	public DoubleLinkedListElement<Pair<Integer, Executable>> putModifier(Modifier modifier) {
		return this.object.putModifier(modifier);
	}

	public void putAndRegisterModifier(Modifier modifier) {
		this.object.putAndRegisterModifier(modifier);
	}

	public void doDestruct() {
		this.object.doDestruct();
	}
}
