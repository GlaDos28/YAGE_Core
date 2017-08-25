package core.objectManager;

import core.gameObject.GObject;
import core.gameObject.GObjectAccess3;
import core.modifier.Modifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager of game objects. Contains a root of all game objects and run a global (root) modifier.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class ObjectManager {
	private final GObject rootObject;
	private final Map<String, ArrayList<GObject>> markedObjects;

	public ObjectManager(Modifier globalModifier) {
		this.rootObject = new GObject(
			marker -> {
				ArrayList<GObject> objects = ObjectManager.this.markedObjects.get(marker);

				if (objects == null)
					return new GObjectAccess3[0];

				GObjectAccess3[] rawObjects = new GObjectAccess3[objects.size()];

				for (int i = 0; i < objects.size(); i++)
					rawObjects[i] = new GObjectAccess3(objects.get(i));

				return rawObjects;
			},
			globalModifier);
		this.markedObjects = new HashMap<>();
	}

	public void startLoop() {
		/* TODO start game loop */
	}
}
