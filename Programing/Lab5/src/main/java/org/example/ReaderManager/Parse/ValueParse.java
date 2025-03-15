package org.example.ReaderManager.Parse;

public interface ValueParse<T> {
    T parse(String value) throws Exception;
}
