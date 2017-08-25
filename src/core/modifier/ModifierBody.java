package core.modifier;

/**
 * Modifier code that generates a Runnable by the given arguments.
 *
 * @author Evgeny Savelyev
 * @since 23.08.17
 */
@FunctionalInterface
public interface ModifierBody {
	Runnable extractRunnable(ModifierData data);
}
