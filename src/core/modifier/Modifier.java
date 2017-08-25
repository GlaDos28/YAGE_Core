package core.modifier;

/**
 * Object modifier.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class Modifier {
	private final ModifierBody body;

	public Modifier(ModifierBody body) {
		this.body = body;
	}

	public void run(ModifierData data) {
		this.body.extractRunnable(data).run();
	}
}
