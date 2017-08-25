package core.gameObject;

import core.modifier.Modifier;
import core.modifier.ModifierData;
import core.objectManager.MarkedObjectAccessor;
import javafx.util.Pair;

import java.util.*;

/**
 * Game object.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class GObject {
	private final Map<String, Object>  attributes;
	private final Set<String>          markers;
	private final Map<String, GObject> subObjects;
	private final ArrayList<Modifier>  modifiers;
	private final MarkedObjectAccessor markedObjectAccessor;

	public GObject(MarkedObjectAccessor markedObjectAccessor) {
		this(markedObjectAccessor, new Pair[0], new String[0], new Pair[0], new Modifier[0]);
	}

	public GObject(MarkedObjectAccessor markedObjectAccessor, Modifier... modifiers) {
		this(markedObjectAccessor, new Pair[0], new String[0], new Pair[0], modifiers);
	}

	public GObject(MarkedObjectAccessor markedObjectAccessor, Pair<String, Object>[] attributes, String[] markers, Pair<String, GObject>[] subObjects, Modifier[] modifiers) {
		this.attributes           = new HashMap<>();
		this.markers              = new HashSet<>();
		this.subObjects           = new HashMap<>();
		this.modifiers            = new ArrayList<>();
		this.markedObjectAccessor = markedObjectAccessor;

		//** assigning values of attributes, markers, subObjects and modifiers

		for (Pair<String, Object> attribute : attributes)
			this.attributes.put(attribute.getKey(), attribute.getValue());

		Collections.addAll(this.markers, markers);

		for (Pair<String, GObject> subObject : subObjects)
			this.subObjects.put(subObject.getKey(), subObject.getValue());

		Collections.addAll(this.modifiers, modifiers);
	}

	//** getters

	public Object getAttribute(String attributeName) {
		return this.attributes.get(attributeName);
	}

	public boolean isMarked(String marker) {
		return this.markers.contains(marker);
	}

	public GObject getSubObject(String subObjectName) {
		return this.subObjects.get(subObjectName);
	}

	//** setters

	public GObject putAttribute(String attributeName, Object attributeValue) {
		this.attributes.put(attributeName, attributeValue);
		return this;
	}

	public GObject mark(String marker) {
		this.markers.add(marker);
		return this;
	}

	public GObject putSubObject(String subObjectName, GObject subObjectValue) {
		this.subObjects.put(subObjectName, subObjectValue);
		return this;
	}

	public GObject putModifier(Modifier modifier) {
		this.modifiers.add(modifier);
		return this;
	}

	//** other

	public void runModifiers() { /* TODO modifier run priorities */
		for (Modifier modifier : this.modifiers)
			modifier.run(new ModifierData(this, markedObjectAccessor));

		for (GObject subObject : this.subObjects.values())
			subObject.runModifiers();
	}
}
