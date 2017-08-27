package core.modifier;

/**
 * Editable version of modifier body. Contains a modifier body which can be changed by set method.
 * Made to allow modifier bodies cycle dependencies.
 *
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
public final class ModifierEditableBody implements ModifierBody {
	private ModifierBody body;

	public ModifierEditableBody() {
		this(null);
	}

	public ModifierEditableBody(ModifierBody body) {
		this.body = body;
	}

	public void setBody(ModifierBody body) {
		this.body = body;
	}

	@Override
	public Runnable extractRunnable(ModifierData data) {
		return this.body.extractRunnable(data);
	}
}
