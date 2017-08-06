package io.github.sskorol.framework.core;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Reloadable;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.HotReload(value = 1)
@Config.Sources("classpath:config.properties")
public interface BaseConfig extends Config, Reloadable {

    BaseConfig BASE_CONFIG = ConfigFactory.create(BaseConfig.class, System.getProperties(), System.getenv());

    @Key("use.page.cache")
    boolean usePageCache();

    @Key("landing.page.url")
    String landingPageUrl();

    @Key("wait.timeout")
    long waitTimeout();

    @Key("gmail.api.user.id")
    String gmailUserId();

    @Key("rest.services.url")
    String restUrl();
}
