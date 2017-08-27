package core.modifier;

import core.misc.Executable;

/**
 * Object modifier.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class Modifier implements Executable {
	private final ModifierBody body;
	private final ModifierData data;

	public Modifier(ModifierBody body, ModifierData data) {
		this.body = body;
		this.data = data;
	}

	public ModifierData getData() {
		return this.data;
	}

	@Override
	public void execute() {
		this.body.extractRunnable(this.data).run();
	}
}
