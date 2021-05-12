package io.github.sskorol.framework.core;

import static io.github.sskorol.framework.core.BaseConfig.BASE_CONFIG;
import static io.github.sskorol.framework.core.listeners.BaseListener.getDriver;

public interface Page {

    default void open() {
        getDriver().get(url());
    }

    default String url() {
        return BASE_CONFIG.landingPageUrl();
    }
}
