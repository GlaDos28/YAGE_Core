package core.modifier.modifierAccessors;

import core.gameObject.GObjectAccess3;

/**
 * Interface that grants access to the objects marked with the given marker.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
@FunctionalInterface
public interface MarkedObjectAccessor {
	GObjectAccess3[] getMarkedObjects(String marker);
}
