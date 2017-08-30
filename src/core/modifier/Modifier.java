package core.modifier;

import core.misc.exceptionsFiltering.ExceptionFilter;
import core.misc.exceptionsFiltering.FilterExceptions;
import core.misc.Executable;

/**
 * Object modifier.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
public final class Modifier extends FilterExceptions<Exception> implements Executable {
	private final ModifierBody body;
	private final ModifierData data;

	public Modifier(ModifierBody body, ModifierData data) {
		this(body, data, null);
	}

	public Modifier(ModifierBody body, ModifierData data, ExceptionFilter<Exception> exceptionFilter) {
		super(exceptionFilter);

		this.body = body;
		this.data = data;
	}

	public ModifierData getData() {
		return this.data;
	}

	@Override
	public void execute() throws Exception {
		try {
			this.body.extractRunnable(this.data).run();
		} catch (Exception ex) {
			super.filterException(ex);
		}
	}
}
