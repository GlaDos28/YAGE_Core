package core.misc;

/**
 * Interface for generalizing modifier and object execute methods.
 *
 * @author Evgeny Savelyev
 * @since 27.08.17
 */
@FunctionalInterface
public interface Executable {
	boolean execute(); /* whether to destroy object after execution */
}
