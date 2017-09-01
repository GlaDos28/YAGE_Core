package core.gameObject;

import core.misc.Executable;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.modifier.Modifier;
import javafx.util.Pair;

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

	public boolean shouldDestruct() {
		return this.object.shouldDestruct();
	}

	//** setters

	public DoubleLinkedListElement<Pair<Integer,Executable>> putModifier(Modifier modifier) {
		return this.object.putModifier(modifier);
	}
}
