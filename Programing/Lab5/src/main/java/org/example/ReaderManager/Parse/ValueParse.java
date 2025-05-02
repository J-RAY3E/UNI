package ReaderManager.Parse;

/**
 * Functional interface for parsing string values into other types.
 * @param <T> the target type.
 */
public interface ValueParse<T> {
    /**
     * Parses a string into the specified type.
     * @param value the string value.
     * @return the parsed value.
     * @throws Exception if parsing fails.
     */
    T parse(String value) throws Exception;
}
