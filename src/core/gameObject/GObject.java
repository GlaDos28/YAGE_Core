package core.gameObject;

import core.misc.Executable;
import core.misc.PriorityList;
import core.misc.TwoIndexedList;
import core.modifier.Modifier;
import core.workManager.MarkedObjectAccessor;
import core.workManager.PutInPoolAccessor;
import core.workManager.WorkManager;
import javafx.util.Pair;

import java.util.*;

/**
 * Game object.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class GObject implements Executable {
	private final String                     name;
	private final int                        priority;
	private final Map<String, Object>        attributes;
	private final Set<String>                markers;
	private final TwoIndexedList<Executable> subObjectsAndMidModifiers; /* modifiers have no name, only priority */
	private final PriorityList<Modifier>     preModifiers;
	private final PriorityList<Modifier>     postModifiers;
	private final MarkedObjectAccessor       markedObjectAccessor;
	private final PutInPoolAccessor          putInPool;

	public GObject(MarkedObjectAccessor markedObjectAccessor, PutInPoolAccessor putInPool, String name, int priority) {
		this(markedObjectAccessor, putInPool, name, priority, new Pair[0], new String[0], new Pair[0], new Modifier[0]);
	}

	public GObject(MarkedObjectAccessor markedObjectAccessor, PutInPoolAccessor putInPool, String name, int priority, Modifier... modifiers) {
		this(markedObjectAccessor, putInPool, name, priority, new Pair[0], new String[0], new Pair[0], modifiers);
	}

	public GObject(MarkedObjectAccessor markedObjectAccessor, PutInPoolAccessor putInPool, String name, int priority, Pair<String, Object>[] attributes, String[] markers, Pair<String, GObject>[] subObjects, Modifier[] modifiers) {
		this.name                      = name;
		this.priority                  = priority;
		this.attributes                = new HashMap<>();
		this.markers                   = new HashSet<>();
		this.subObjectsAndMidModifiers = new TwoIndexedList<>();
		this.preModifiers              = new PriorityList<>();
		this.postModifiers             = new PriorityList<>();
		this.markedObjectAccessor      = markedObjectAccessor;
		this.putInPool                 = putInPool;

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

	public boolean isMarked(String marker) {
		return this.markers.contains(marker);
	}

	public GObject getSubObject(String subObjectName) {
		return (GObject) this.subObjectsAndMidModifiers.getByName(subObjectName);
	}

	public MarkedObjectAccessor getMarkedObjectAccessor() {
		return this.markedObjectAccessor;
	}

	public PutInPoolAccessor getPutInPoolAccessor() {
		return this.putInPool;
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

	public GObject putModifier(Modifier modifier) {
		if (!WorkManager.DEFAULT_POOL_NAME.equals(modifier.getData().getPool()))
			return this;

		switch (modifier.getData().getOrder()) {
			case PRE:
				this.preModifiers.put(modifier.getData().getPriority(), modifier);
				break;
			case POST:
				this.postModifiers.put(modifier.getData().getPriority(), modifier);
				break;
			case MID:
				this.subObjectsAndMidModifiers.put(null, modifier.getData().getPriority(), modifier);
				break;
			default:
				throw new RuntimeException("unknown order " + modifier.getData().getOrder());
		}

		return this;
	}

	//** other

	@Override
	public boolean execute() {
		for (Modifier modifier : this.preModifiers.getSorted())
			if (modifier.execute())
				this.preModifiers.delete(modifier.getData().getPriority(), modifier);

		for (Executable subObjectOrModifier : this.subObjectsAndMidModifiers.getSortedByPriority())
			if (subObjectOrModifier.execute())
				if (subObjectOrModifier instanceof GObject)
					this.subObjectsAndMidModifiers.delete(((GObject) subObjectOrModifier).getName(), ((GObject) subObjectOrModifier).getPriority(), subObjectOrModifier);
				else
					this.subObjectsAndMidModifiers.delete(null, ((Modifier) subObjectOrModifier).getData().getPriority(), subObjectOrModifier);


		for (Modifier modifier : this.postModifiers.getSorted())
			if (modifier.execute())
				this.postModifiers.delete(modifier.getData().getPriority(), modifier);

		return false; /* TODO object destruction */
	}
}
