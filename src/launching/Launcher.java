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
	private static final int MODIFIER_NUMBER = 4;

	private static final ModifierEditableBody[] MODIFIER_BODIES = new ModifierEditableBody[MODIFIER_NUMBER];

	static {
		for (int i = 0; i < MODIFIER_NUMBER; i++)
			MODIFIER_BODIES[i] = new ModifierEditableBody();

		MODIFIER_BODIES[0].setBody(data -> () -> {
			System.out.println("Hello, world!");


			data.createModifier("test pool", 100, Order.PRE, MODIFIER_BODIES[1]);
			data.createModifierBefore(MODIFIER_BODIES[2]);
			data.createModifierAfter(MODIFIER_BODIES[3]);

			data.doSelfDestruct();
		});

		MODIFIER_BODIES[1].setBody(data -> () -> {
			System.out.println("Hello inside world!");
		});

		MODIFIER_BODIES[2].setBody(data -> () -> {
			System.out.println("Hello before world!");
		});

		MODIFIER_BODIES[3].setBody(data -> () -> {
			System.out.println("Hello after world!");
		});
	}

	public static void main(String[] args) throws Exception {
		WorkManager manager = new WorkManager();
		manager
			.attachGlobalModifier(MODIFIER_BODIES[0])
			.createPool(new CustomPool("test pool", 100));

		try {
			manager.startLoop();
		} catch (Exception ex) {
			System.out.println("ERROR\n" + ex.getMessage());
			throw ex;
		}
	}
}
