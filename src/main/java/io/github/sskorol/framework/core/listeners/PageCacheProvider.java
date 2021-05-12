package io.github.sskorol.framework.core.listeners;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.sskorol.framework.core.Page;
import io.vavr.control.Option;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import static io.github.sskorol.framework.core.BaseConfig.BASE_CONFIG;

public class PageCacheProvider implements IInvokedMethodListener {

    private static final ThreadLocal<Cache<Class<? extends Page>, Page>> PAGE_CACHE = new ThreadLocal<>();

    public static Option<Cache<Class<? extends Page>, Page>> getPageCache() {
        return BASE_CONFIG.usePageCache() ? Option.of(PAGE_CACHE.get()) : Option.none();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            createPageCache();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            cleanUpPageCache();
        }
    }

    private void createPageCache() {
        PAGE_CACHE.set(Caffeine.newBuilder().build());
    }

    private void cleanUpPageCache() {
        final Option<Cache<Class<? extends Page>, Page>> pageCache = getPageCache();
        if (pageCache.isDefined()) {
            pageCache.get().invalidateAll();
        }
        PAGE_CACHE.remove();
    }
}
