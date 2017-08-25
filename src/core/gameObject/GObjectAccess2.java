package core.gameObject;

import core.modifier.Modifier;

/**
 * Facade that gives second-level access to all GObject getter methods, modifier adding method, and also getSubObject() returns a GObject with a third-level access.
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

	public boolean isMarked(String marker) {
		return this.object.isMarked(marker);
	}

	public GObjectAccess3 getSubObject(String subObjectName) {
		return new GObjectAccess3(this.object.getSubObject(subObjectName));
	}

	//** setters

	public GObjectAccess2 putModifier(Modifier modifier) {
		this.object.putModifier(modifier);
		return this;
	}
}