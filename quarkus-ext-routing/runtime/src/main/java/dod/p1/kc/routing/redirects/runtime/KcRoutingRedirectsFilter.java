package dod.p1.kc.routing.redirects.runtime;

import javax.enterprise.context.ApplicationScoped;

import javax.enterprise.event.Observes;
import io.quarkus.vertx.http.runtime.filters.Filters;


@ApplicationScoped
public class KcRoutingRedirectsFilter {

    /**
     * declare static final variable to ovoid checkstyle linting error.
     */
    private static final int FILTER_REGISTER_PRIORITY = 100;

    /**
     *
     * @param filters
     */
    public void registerKcRoutingRedirectsFilter(@Observes final Filters filters) {
        filters.register(rc -> {
            rc.response().putHeader("X-Header", "intercepting the request");
            rc.next();
        }, FILTER_REGISTER_PRIORITY);
    }
    // @RouteFilter
    // void myFilter(RoutingContext rc) {
    //    rc.response().putHeader("X-Header", "Vertex header");
    //    rc.next();
    // }
}
