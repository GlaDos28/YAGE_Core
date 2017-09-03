package core.modifier.modifierAccessors;

import core.gameObject.GObject;

/**
 * Interface that grants access to the object registering in the work manager. Used to put modifiers into the object that is being registered once, if needed.
 *
 * @author Evgeny Savelyev
 * @since 02.09.17
 */
@FunctionalInterface
public interface RegisterObjectAccessor {
	void registerObject(GObject object);
}
