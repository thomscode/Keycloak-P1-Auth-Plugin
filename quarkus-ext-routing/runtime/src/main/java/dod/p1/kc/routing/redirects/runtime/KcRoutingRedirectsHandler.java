package dod.p1.kc.routing.redirects.runtime;

import org.jboss.logging.Logger;
import java.util.HashMap;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.event.Observes;
//import io.quarkus.vertx.http.runtime.filters.Filters;

@ApplicationScoped
public class KcRoutingRedirectsHandler implements Handler<RoutingContext> {

    /**
     * declare logger.
     */
    private static final Logger LOGGER = Logger.getLogger(KcRoutingRedirectsHandler.class.getName());

    /**
     * declare static final variable to ovoid checkstyle linting error.
     */
    private static final int FILTER_REGISTER_PRIORITY = 100;

    /**
     * the urlsMap.
     */
    private static HashMap<String, String> urlsMap = null;

    /**
     * the pathPrefixesMap.
     */
    private static HashMap<String, String> pathPrefixesMap = null;

    /**
     * the pathFilterMap.
     */
    private static HashMap<String, String> pathFiltersMap = null;

    /**
     *
     * @param rc  the event to handle
     */
    @Override
    public void handle(final RoutingContext rc) {

       if (urlsMap.containsKey(rc.normalizedPath())) {
        LOGGER.debugf("Redirect Match: %s to %s", rc.normalizedPath(), urlsMap.get(rc.normalizedPath()));
        rc.redirect(urlsMap.get(rc.normalizedPath()));
       }

//      pathPrefixesMap.forEach((k, v) -> {
//        if (rc.normalizedPath().startsWith(k)) {
//            LOGGER.debugf("PathPrefixing Match: %s to %s", k, v);
//            //LOGGER.infof("uri before: %s", rc.request().uri());
//            rc.redirect(rc.request().uri().replace(k, v));
//            //LOGGER.infof("uri after: %s",rc.request().uri().replace(k, v));
//        }
//      });
//
//      if (pathFiltersMap.containsKey(rc.normalizedPath())) {
//        LOGGER.infof("uri before: %s", rc.request().uri());
//        LOGGER.infof("NormalizedPath before: %s", rc.normalizedPath());
//        LOGGER.infof("Path before: %s", rc.request().path());
//        LOGGER.infof("query before: %s", rc.request().query());
//        //LOGGER.infof("Headers before: %s", rc.request().headers());
//
//        LOGGER.infof("Filters Match: %s to %s", rc.normalizedPath(), pathFiltersMap.get(rc.normalizedPath()));
//        //rc.put("queryParams", rc.queryParams());
//        rc.reroute(pathFiltersMap.get(rc.normalizedPath()));
//      }
    }

    /**
     *
     * @param argUrlsMap
     */
    public void setRedirectPaths(final HashMap<String, String> argUrlsMap) {
    LOGGER.debugf("KcRoutingRedirectsHandler: setRedirectPaths(%s) ", urlsMap);
    this.urlsMap = argUrlsMap;
    }

//    /**
//     *
//     * @param argPathPrefixesMap
//     */
//    public void setPathPrefixes(final HashMap<String, String> argPathPrefixesMap) {
//    LOGGER.debugf("KcRoutingRedirectsHandler: setPathPrefixes(%s) ", pathPrefixesMap);
//    this.pathPrefixesMap = argPathPrefixesMap;
//    }
//
//    /**
//     *
//     * @param argPathFiltersMap
//     */
//    public void setPathFilters(final HashMap<String, String> argPathFiltersMap) {
//    LOGGER.debugf("KcRoutingRedirectsHandler: setPathFilters(%s) ", pathFiltersMap);
//    this.pathFiltersMap = pathFiltersMap;
//    }
//
//    /**
//     *
//     * @param filters
//     */
//    public void registerKcRoutingRedirectsFilter(@Observes final Filters filters) {
//      filters.register(rc -> {
//          rc.response().putHeader("X-Header", "intercepting the request");
//          rc.next();
//      }, FILTER_REGISTER_PRIORITY);
//    }

}
