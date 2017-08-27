package core.gameObject;

import core.modifier.Modifier;

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

	public boolean isMarked(String marker) {
		return this.object.isMarked(marker);
	}

	public GObjectAccess2 getSubObject(String subObjectName) {
		return new GObjectAccess2(this.object.getSubObject(subObjectName));
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

	public GObjectAccess1 putModifier(Modifier modifier) {
		this.object.putModifier(modifier);
		return this;
	}
}
