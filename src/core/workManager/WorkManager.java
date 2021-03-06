package core.workManager;

import core.gameObject.GObject;
import core.gameObject.GObjectAccess2;
import core.gameObject.GObjectAccess3;
import core.modifier.modifierAccessors.ModifierAccessors;
import core.misc.TwoIndexedList;
import core.misc.exceptionsFiltering.ExceptionFilter;
import core.misc.exceptionsFiltering.FilterExceptions;
import core.modifier.Modifier;
import core.modifier.ModifierBody;
import core.modifier.ModifierData;
import core.modifier.Order;
import core.pool.CustomPool;
import core.pool.DefaultPool;
import core.pool.Pool;

import java.util.*;

/**
 * Manager of game working loop. Contains a root of all game objects and map of pools to execute modifiers in concrete pool order.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class WorkManager extends FilterExceptions<Exception> {
	private static final int DEFAULT_POOL_PRIORITY = 0;

	public static final int    DEFAULT_DELAY_TIME = 500; /* in milliseconds */
	public static final String DEFAULT_POOL_NAME  = "default";

	private final long                            delayTime; /* in nanoseconds */
	private final TwoIndexedList<Pool>            pools;
	private final GObject                         rootObject;
	private final Map<String, ArrayList<GObject>> markedObjects;
	private final WorkManagerListeners            listeners;

	public WorkManager() {
		this(DEFAULT_DELAY_TIME, null);
	}

	public WorkManager(ExceptionFilter<Exception> exceptionFilter) {
		this(DEFAULT_DELAY_TIME, exceptionFilter);
	}

	public WorkManager(int delayTime) {
		this(delayTime, null);
	}

	public WorkManager(int delayTime, ExceptionFilter<Exception> exceptionFilter) {
		super(exceptionFilter);

		this.delayTime            = (long) delayTime * 1000000;
		this.pools                = new TwoIndexedList<>();
		this.listeners            = new WorkManagerListeners();
		this.markedObjects        = new HashMap<>();
		this.rootObject           = new GObject(
			new ModifierAccessors(
				marker -> {
					ArrayList<GObject> objects = WorkManager.this.markedObjects.get(marker);

					if (objects == null)
						return new GObjectAccess3[0];

					GObjectAccess3[] rawObjects = new GObjectAccess3[objects.size()];

					for (int i = 0; i < objects.size(); i++)
						rawObjects[i] = new GObjectAccess3(objects.get(i));

					return rawObjects;
				},
				modifier -> ((CustomPool) this.pools.getByName(modifier.getData().getPool())).putModifier(modifier),
				modifier -> this.listeners.getNewModifierListeners().removeIf(listener -> listener.tryTrigger(modifier)),
				object   -> this.listeners.getNewObjectListeners().removeIf(listener -> listener.tryTrigger(new GObjectAccess2(object))),
				WorkManager.this.listeners.getNewModifierListeners()::add,
				WorkManager.this.listeners.getNewObjectListeners()::add
			),
			"root",
			0
		);

		this.pools.put(DEFAULT_POOL_NAME, DEFAULT_POOL_PRIORITY, new DefaultPool(DEFAULT_POOL_NAME, DEFAULT_POOL_PRIORITY, this.rootObject));
	}

	//** setters

	public WorkManager attachGlobalModifier(ModifierBody globalModifierBody) {
		Modifier modifier = new Modifier(globalModifierBody, new ModifierData(this.rootObject, DEFAULT_POOL_NAME, 0, Order.PRE));
		modifier.getData().setElementLink(this.rootObject.putModifier(modifier));

		return this;
	}

	public WorkManager createPool(CustomPool pool) {
		this.pools.put(pool.getName(), pool.getPriority(), pool);
		return this;
	}

	//** other

	public void startLoop() throws Exception {
		long prevTime;
		long curTime = System.nanoTime();

		while (true) {
			prevTime = curTime;

			try {
				this.pools.forEach((elem, value) -> value.executeModifiers());
			} catch (Exception ex) {
				super.filterException(ex);
			}

			curTime = System.nanoTime();

			if (curTime - prevTime > this.delayTime) {
				System.out.println("Execution step time " + ((double) (curTime - prevTime) / 1000000) + " ms exceeded the maximum step time " + ((double) this.delayTime / 1000000) + " ms");
				prevTime = curTime - this.delayTime;
			}

			while (curTime - prevTime < this.delayTime)
				curTime = System.nanoTime();
		}
	}
}
