package core.pool;

import core.gameObject.GObject;

/**
 * Default pool that goes through objects in hierarchy according to the pre-, post- and mid- modifier orders.
 *
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public final class DefaultPool extends Pool {
	public DefaultPool(String name, int priority, GObject rootObject) {
		super(name, priority, null, null);

		super.setExecutor(() -> {
			try {
				rootObject.execute();
			} catch (Exception ex) {
				super.filterException(ex);
			}
		});
	}
}
