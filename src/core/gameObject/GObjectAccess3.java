package core.gameObject;

/**
 * Facade that gives third-level access to all GObject getter methods, and also getSubObject() returns a GObject with a third-level access.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class GObjectAccess3 {
	private final GObject object;

	public GObjectAccess3(GObject object) {
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
}
