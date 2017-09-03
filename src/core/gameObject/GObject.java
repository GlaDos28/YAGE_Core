package core.gameObject;

import core.misc.Executable;
import core.misc.exceptionsFiltering.ExceptionFilter;
import core.misc.exceptionsFiltering.FilterExceptions;
import core.misc.priorityList.PriorityList;
import core.misc.TwoIndexedList;
import core.misc.doubleLinkedList.DoubleLinkedListElement;
import core.modifier.Modifier;
import core.workManager.WorkManager;
import javafx.util.Pair;

import java.util.*;

/**
 * Game object.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class GObject extends FilterExceptions<Exception> implements Executable {
	private final String                     name;
	private final int                        priority;
	private final Map<String, Object>        attributes;
	private final Set<String>                markers;
	private final TwoIndexedList<Executable> subObjectsAndMidModifiers; /* modifiers have no name, only priority */
	private final PriorityList<Executable>   preModifiers;
	private final PriorityList<Executable>   postModifiers;
	private final ModifierAccessors          modifierAccessors;
	private       boolean                    shouldDestruct;

	public GObject(ModifierAccessors accessors, String name, int priority) {
		this(accessors, name, priority, new Pair[0], new String[0], new Pair[0], new Modifier[0], null);
	}

	public GObject(ModifierAccessors accessors, String name, int priority, ExceptionFilter<Exception> exceptionFilter) {
		this(accessors, name, priority, new Pair[0], new String[0], new Pair[0], new Modifier[0], exceptionFilter);
	}

	public GObject(ModifierAccessors accessors, String name, int priority, Modifier... modifiers) {
		this(accessors, name, priority, new Pair[0], new String[0], new Pair[0], modifiers, null);
	}

	public GObject(ModifierAccessors accessors, String name, int priority, ExceptionFilter<Exception> exceptionFilter, Modifier... modifiers) {
		this(accessors, name, priority, new Pair[0], new String[0], new Pair[0], modifiers, exceptionFilter);
	}

	public GObject(ModifierAccessors accessors, String name, int priority, Pair<String, Object>[] attributes, String[] markers, Pair<String, GObject>[] subObjects, Modifier[] modifiers, ExceptionFilter<Exception> exceptionFilter) {
		super(exceptionFilter);

		this.name                      = name;
		this.priority                  = priority;
		this.attributes                = new HashMap<>();
		this.markers                   = new HashSet<>();
		this.subObjectsAndMidModifiers = new TwoIndexedList<>();
		this.preModifiers              = new PriorityList<>();
		this.postModifiers             = new PriorityList<>();
		this.modifierAccessors = accessors;
		this.shouldDestruct            = false;

		//** assigning values of attributes, markers, subObjectsAndMidModifiers and modifiers

		for (Pair<String, Object> attribute : attributes)
			this.attributes.put(attribute.getKey(), attribute.getValue());

		Collections.addAll(this.markers, markers);

		for (Modifier modifier : modifiers)
			this.putModifier(modifier);

		for (Pair<String, GObject> subObject : subObjects)
			this.subObjectsAndMidModifiers.put(subObject.getKey(), subObject.getValue().getPriority(), subObject.getValue());
	}

	//** getters

	public String getName() {
		return this.name;
	}

	public int getPriority() {
		return this.priority;
	}

	public Object getAttribute(String attributeName) {
		return this.attributes.get(attributeName);
	}

	public Set<String> getMarkers() {
		return this.markers;
	}

	public boolean isMarked(String marker) {
		return this.markers.contains(marker);
	}

	public GObject getSubObject(String subObjectName) {
		return (GObject) this.subObjectsAndMidModifiers.getByName(subObjectName);
	}

	public ModifierAccessors getModifierAccessors() {
		return this.modifierAccessors;
	}

	public boolean shouldDestruct() {
		return this.shouldDestruct;
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
		this.subObjectsAndMidModifiers.put(subObjectName, subObjectValue.getPriority(), subObjectValue);
		return this;
	}

	public GObject deleteSubObject(String subObjectName) {
		this.subObjectsAndMidModifiers.delete(subObjectName);
		return this;
	}

	public DoubleLinkedListElement<Pair<Integer, Executable>> putModifier(Modifier modifier) {
		if (!WorkManager.DEFAULT_POOL_NAME.equals(modifier.getData().getPool()))
			return null;

		switch (modifier.getData().getOrder()) {
			case PRE:
				return this.preModifiers.put(modifier.getData().getPriority(), modifier);
			case POST:
				return this.postModifiers.put(modifier.getData().getPriority(), modifier);
			case MID:
				return this.subObjectsAndMidModifiers.put(null, modifier.getData().getPriority(), modifier);
			default:
				throw new RuntimeException("unknown order " + modifier.getData().getOrder());
		}
	}

	public void doDestruct() {
		this.shouldDestruct = true;
	}

	//** other

	private boolean handleModifier(DoubleLinkedListElement<Pair<Integer, Executable>> element) throws Exception {
		Modifier modifier = (Modifier) element.getValue().getValue();

		try {
			modifier.execute();
		} catch (Exception ex) {
			super.filterException(ex);
		}

		if (modifier.getData().shouldDestruct())
			element.unlink();

		if (modifier.getData().shouldDestructObject()) {
			this.shouldDestruct = true;
			return true;
		}

		return false;
	}

	@Override
	public void execute() throws Exception {
		//** pre-modifiers

		DoubleLinkedListElement<Pair<Integer, Executable>> cur = this.preModifiers.getFirstElement();

		while (cur != null) {
			DoubleLinkedListElement<Pair<Integer, Executable>> next = cur.getNext();

			if (this.handleModifier(cur))
				return;

			cur = next;
		}

		//** mid-modifiers and sub-objects

		cur = this.subObjectsAndMidModifiers.getHeadElement();

		while (cur != null) {
			DoubleLinkedListElement<Pair<Integer, Executable>> next = cur.getNext();

			if (cur.getValue().getValue() instanceof Modifier) {
				if (this.handleModifier(cur))
					return;
			}
			else {
				GObject object = (GObject) cur.getValue().getValue();

				object.execute();

				if (object.shouldDestruct())
					cur.unlink();
			}

			cur = next;
		}

		//** post-modifiers

		cur = this.postModifiers.getFirstElement();

		while (cur != null) {
			DoubleLinkedListElement<Pair<Integer, Executable>> next = cur.getNext();

			if (handleModifier(cur))
				return;

			cur = next;
		}
	}
}
