package dod.p1.kc.routing.redirects.runtime;

import javax.enterprise.context.ApplicationScoped;

import javax.enterprise.event.Observes;
import io.quarkus.vertx.http.runtime.filters.Filters;

//import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

@ApplicationScoped
public class KcRoutingRedirectsFilter {

    public void registerKcRoutingRedirectsFilter(@Observes Filters filters) {
        filters.register(rc -> {
            rc.response().putHeader("X-Header", "intercepting the request");
            rc.next();
        }, 100);
    }
    // @RouteFilter
    // void myFilter(RoutingContext rc) {
    //    rc.response().putHeader("X-Header", "Vertex header");
    //    rc.next();
    // }
}
