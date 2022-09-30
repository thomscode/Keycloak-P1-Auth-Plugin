package dod.p1.kc.routing.redirects.deployment;

import java.util.Map;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigRoot;

//https://quarkus.io/guides/config-mappings
@ConfigRoot
public class KcRoutingRedirectsConfig {
    /**
     * The path where KC Redirects is available.
     * <p>
     * The value `/` is not allowed as it blocks the application from serving anything else.
     * By default, this value will be resolved as a path relative to `${quarkus.http.non-application-root-path}`.
     */
    @ConfigItem
    Map<String, String> urls;


    /**
     * The path where KC Prefixes Redirects are available.
     * <p>
     * The value `/` is not allowed as it blocks the application from serving anything else.
     * By default, this value will be resolved as a path relative to `${quarkus.http.non-application-root-path}`.
     */
    @ConfigItem()
    Map<String, String> pathPrefixes;

    /**
     * The path where KC filters is available.
     * <p>
     * The value `/` is not allowed as it blocks the application from serving anything else.
     * By default, this value will be resolved as a path relative to `${quarkus.http.non-application-root-path}`.
     */
    @ConfigItem()
    Map<String, String> pathFilters;

    /**
     * If this should be included every time. By default, this is only included when the application is running
     * in dev mode.
     */
    @ConfigItem(defaultValue = "false")
    boolean alwaysInclude;

}
