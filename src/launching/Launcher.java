package launching;

import core.modifier.*;
import core.pool.CustomPool;
import core.workManager.WorkManager;

/**
 * Program launcher.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class Launcher {
	private static final int MODIFIER_NUMBER = 2;

	private static final ModifierEditableBody[] MODIFIER_BODIES = new ModifierEditableBody[MODIFIER_NUMBER];

	static {
		for (int i = 0; i < MODIFIER_NUMBER; i++)
			MODIFIER_BODIES[i] = new ModifierEditableBody();

		MODIFIER_BODIES[0].setBody(data -> () -> {
			System.out.println("Hello, world!");

			data.createModifier("test pool", 0, Order.PRE, MODIFIER_BODIES[1]);

			data.doSelfDestruct();
		});

		MODIFIER_BODIES[1].setBody(data -> () -> {
			System.out.println("I want to say");
		});
	}

	public static void main(String[] args) {
		WorkManager manager = new WorkManager();
		manager
			.attachGlobalModifier(MODIFIER_BODIES[0])
			.createPool(new CustomPool("test pool", 100));

		manager.startLoop();
		manager.startLoop();
	}
}
