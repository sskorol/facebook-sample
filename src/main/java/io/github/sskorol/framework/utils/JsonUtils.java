package io.github.sskorol.framework.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Optional.ofNullable;

@Slf4j
public final class JsonUtils {

    private JsonUtils() {
        throw new UnsupportedOperationException("Illegal access to private constructor");
    }

    public static <T> Optional<T> toEntity(final Class<T> entity) {
        try (final InputStreamReader reader = new InputStreamReader(
                getSystemResourceAsStream("data/" + entity.getSimpleName().toLowerCase() + ".json"))) {
            return ofNullable(new Gson().fromJson(reader, entity));
        } catch (IOException e) {
            log.error("Unable to convert json to POJO:\n{}", e);
            return Optional.empty();
        }
    }
}
