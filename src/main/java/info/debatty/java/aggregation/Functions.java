package info.debatty.java.aggregation;

/**
 * Represents an array of functions.
 *
 * @author Thibault Debatty
 */
class Functions {

    Function[] functions;

    Functions(final int size) {
        functions = new Function[size + 1];
        for (int i = 0; i <= size; i++) {
            functions[i] = new Function();
        }
    }
}
