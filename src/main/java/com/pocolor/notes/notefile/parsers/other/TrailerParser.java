package com.pocolor.notes.notefile.parsers.other;

import com.pocolor.notes.notefile.trailer.Dependencies;
import com.pocolor.notes.notefile.trailer.Trailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public final class TrailerParser {
    private static final Logger log = LoggerFactory.getLogger(TrailerParser.class);

    @FunctionalInterface
    private interface TrailerComponentParser<T> {
        T parse(String[] values);
    }

    private TrailerParser() throws Exception { throw new Exception("no instances of this class"); }

    private static final HashMap<String, Class<?>> DECLARATIONS = new HashMap<>();
    private static final HashMap<Class<?>, TrailerComponentParser<?>> PARSERS = new HashMap<>();

    static {
        DECLARATIONS.put(Dependencies.DECLARATION, Dependencies.class);

        PARSERS.put(Dependencies.class, Dependencies::new);
    }

    public static Trailer parse(byte[] bytes) {
        HashMap<Class<?>, String[]> objects = new HashMap<>();
        String[] tokens = new String(bytes, StandardCharsets.UTF_8).split("\\s");

        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].startsWith("/") && !tokens[i].isEmpty()) {
                log.error("Unknown token '{}' in trailer: {}", tokens[i], tokens);
                throw new RuntimeException();
            }

            String declaration = tokens[i];

            if (!DECLARATIONS.containsKey(declaration)) {
                log.error("Unknown declaration '{}'. Defined declarations are: {}", declaration, DECLARATIONS.keySet());
                throw new RuntimeException();
            }

            int declarationIndex = i;
            do { i++; } while (i < tokens.length && !tokens[i].startsWith("/"));

            objects.put(DECLARATIONS.get(declaration), Arrays.copyOfRange(tokens, declarationIndex + 1, i));
        }

        return constructTrailer(objects);
    }

    private static Trailer constructTrailer(HashMap<Class<?>, String[]> objects) {
        return new Trailer(
                (Dependencies) PARSERS.get(Dependencies.class).parse(objects.get(Dependencies.class))
        );
    }
}
