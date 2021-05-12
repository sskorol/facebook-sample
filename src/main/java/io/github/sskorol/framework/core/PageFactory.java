package io.github.sskorol.framework.core;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.sskorol.framework.core.listeners.PageCacheProvider;
import io.vavr.control.Option;

import java.util.Objects;

import static org.joor.Reflect.on;

public final class PageFactory {

    private PageFactory() {
        throw new UnsupportedOperationException("Illegal access to private constructor");
    }

    public static <T extends Page> T open(final Class<T> page) {
        final T pageObject = at(page);
        pageObject.open();
        return pageObject;
    }

    public static <T extends Page> T at(final Class<T> page) {
        return cachePage(page).getOrElseThrow(() -> new IllegalStateException("Unable to initialize page"));
    }

    @SuppressWarnings("unchecked")
    private static <T extends Page> Option<T> cachePage(final Class<T> pageClass) {
        final Option<Cache<Class<? extends Page>, Page>> pageCache = PageCacheProvider.getPageCache();
        final T page = pageCache
                .map(cache -> cache.getIfPresent(pageClass))
                .filter(Objects::nonNull)
                .map(p -> (T) p)
                .getOrElse(() -> on(pageClass).create().get());

        if (pageCache.isDefined()) {
            pageCache.get().put(pageClass, page);
        }

        return Option.of(page);
    }
}
